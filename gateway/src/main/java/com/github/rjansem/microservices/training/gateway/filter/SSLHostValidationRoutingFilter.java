package com.github.rjansem.microservices.training.gateway.filter;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.Host;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Validate HTTPS certificate and redirect request to final destination.
 * 
 * @author cbarillet
 *
 */
public class SSLHostValidationRoutingFilter extends ZuulFilter {

	protected static final String JAVA_ENV_TRUSTSTORE = "javax.net.ssl.trustStore";
	protected static final String JAVA_ENV_TRUSTSTORE_PASSWORD = "javax.net.ssl.trustStorePassword";
	protected static final String ZUUL_FILTER_TYPE_ROUTE = "route";
	protected static final int ZUUL_FILTER_ORDER = 100;
	protected static final String HTTPS_PROTOCOL = "TLS";
	protected static final String HTTP_VERB_POST = "POST";
	protected static final String HTTP_VERB_PUT = "PUT";
	protected static final String HTTP_VERB_DELETE = "DELETE";
	protected static final String HTTP_VERB_PATCH = "PATCH";
	private static final Logger LOGGER = LoggerFactory.getLogger(SSLHostValidationRoutingFilter.class);
	private static final DynamicIntProperty SOCKET_TIMEOUT = DynamicPropertyFactory.getInstance()
			.getIntProperty(ZuulConstants.ZUUL_HOST_SOCKET_TIMEOUT_MILLIS, 10000);
	private static final DynamicIntProperty CONNECTION_TIMEOUT = DynamicPropertyFactory.getInstance()
			.getIntProperty(ZuulConstants.ZUUL_HOST_CONNECT_TIMEOUT_MILLIS, 2000);
	private static final String ERROR_STATUS_CODE = "error.status_code";
	private final Timer connectionManagerTimer = new Timer("SSLHostValidationRoutingFilter.connectionManagerTimer", true);
	@Value("${zuul.ssl.checkRemoteCertificate}")
	private Boolean checkRemoteCertificate;
	private boolean sslHostnameValidationEnabled;

	private ProxyRequestHelper helper;
	private Host hostProperties;
	private PoolingHttpClientConnectionManager connectionManager;
	private CloseableHttpClient httpClient;

	private String trustStorePath;
	private String trustStorePassword;

	private final Runnable clientloader = new Runnable() {
		@Override
		public void run() {
			try {
				SSLHostValidationRoutingFilter.this.httpClient.close();
			} catch (IOException ex) {
				LOGGER.error("error closing client", ex);
			}
			SSLHostValidationRoutingFilter.this.httpClient = newClient();
		}
	};

	public SSLHostValidationRoutingFilter(ProxyRequestHelper helper, ZuulProperties properties) {
		this.helper = helper;
		this.hostProperties = properties.getHost();
		this.sslHostnameValidationEnabled = properties.isSslHostnameValidationEnabled();
		// Récupération des informations de chargement du truststore
		this.trustStorePath = System.getProperty(JAVA_ENV_TRUSTSTORE);
		this.trustStorePassword = System.getProperty(JAVA_ENV_TRUSTSTORE_PASSWORD);
		if (LOGGER.isDebugEnabled()) {
			// Nous ne pouvons pas afficher le password sinon problème de sécurité.
			LOGGER.debug("trustStorePath : {} - trustStorePassword.length() : {}", this.trustStorePath, this.trustStorePassword != null ? this.trustStorePassword.length() : 0);
		}
	}

	@PostConstruct
	private void initialize() {
		this.httpClient = newClient();
		SOCKET_TIMEOUT.addCallback(this.clientloader);
		CONNECTION_TIMEOUT.addCallback(this.clientloader);
		this.connectionManagerTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (SSLHostValidationRoutingFilter.this.connectionManager == null) {
					return;
				}
				SSLHostValidationRoutingFilter.this.connectionManager.closeExpiredConnections();
			}
		}, 30000, 5000);
	}

	@PreDestroy
	public void stop() {
		this.connectionManagerTimer.cancel();
	}

	@Override
	public String filterType() {
		return ZUUL_FILTER_TYPE_ROUTE;
	}

	@Override
	public int filterOrder() {
		return ZUUL_FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().getRouteHost() != null
				&& RequestContext.getCurrentContext().sendZuulResponse();
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
		MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
		String verb = getVerb(request);
		InputStream requestEntity = getRequestBody(request);
		if (request.getContentLength() < 0) {
			context.setChunkedRequestBody();
		}

		String uri = this.helper.buildZuulRequestURI(request);
		this.helper.addIgnoredHeaders();

		try {
			HttpResponse response = forward(this.httpClient, verb, uri, request, headers, params, requestEntity);
			setResponse(response);
		} catch (Exception ex) {
			context.set(ERROR_STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			context.set("error.exception", ex);
		}
		return null;
	}

	protected PoolingHttpClientConnectionManager newConnectionManager() {
		try {

			X509TrustManager trustManager = null;
			if (Boolean.TRUE.equals(checkRemoteCertificate)) {
				checkValidationInformationAvailable();
				trustManager = this.getX509TrustManager();
			} else {
				trustManager = this.buildByPassTrustManager();
			}
			final SSLContext sslContext = SSLContext.getInstance(HTTPS_PROTOCOL);
			sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());

			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE);
			if (this.sslHostnameValidationEnabled) {
				registryBuilder.register("https", new SSLConnectionSocketFactory(sslContext));
			} else {
				registryBuilder.register("https",
						new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE));
			}
			final Registry<ConnectionSocketFactory> registry = registryBuilder.build();

			this.connectionManager = new PoolingHttpClientConnectionManager(registry);
			this.connectionManager.setMaxTotal(this.hostProperties.getMaxTotalConnections());
			this.connectionManager.setDefaultMaxPerRoute(this.hostProperties.getMaxPerRouteConnections());
			return this.connectionManager;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	protected CloseableHttpClient newClient() {
		final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT.get())
				.setConnectTimeout(CONNECTION_TIMEOUT.get()).setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		if (!this.sslHostnameValidationEnabled) {
			httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
		}
		return httpClientBuilder.setConnectionManager(newConnectionManager()).useSystemProperties()
				.setDefaultRequestConfig(requestConfig).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
				.setRedirectStrategy(new RedirectStrategy() {
					@Override
					public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)
							throws ProtocolException {
						return false;
					}

					@Override
					public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context)
							throws ProtocolException {
						return null;
					}
				}).build();
	}

	private HttpResponse forward(HttpClient httpclient, String verb, String uri, HttpServletRequest request,
			MultiValueMap<String, String> headers, MultiValueMap<String, String> params, InputStream requestEntity)
			throws Exception {
		Map<String, Object> info = this.helper.debug(verb, uri, headers, params, requestEntity);
		URL host = RequestContext.getCurrentContext().getRouteHost();
		HttpHost httpHost = getHttpHost(host);
		uri = StringUtils.cleanPath((host.getPath() + uri).replaceAll("/{2,}", "/"));
		int contentLength = request.getContentLength();
		InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
				request.getContentType() != null ? ContentType.create(request.getContentType()) : null);

		HttpRequest httpRequest = buildHttpRequest(verb, uri, entity, headers, params);
		try {
			LOGGER.debug(httpHost.getHostName() + " " + httpHost.getPort() + " " + httpHost.getSchemeName());
			HttpResponse zuulResponse = forwardRequest(httpclient, httpHost, httpRequest);
			this.helper.appendDebug(info, zuulResponse.getStatusLine().getStatusCode(),
					revertHeaders(zuulResponse.getAllHeaders()));
			return zuulResponse;
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			// httpclient.getConnectionManager().shutdown();
		}
	}

	protected HttpRequest buildHttpRequest(String verb, String uri, InputStreamEntity entity,
			MultiValueMap<String, String> headers, MultiValueMap<String, String> params) {
		HttpRequest httpRequest;

		switch (verb.toUpperCase()) {
		case HTTP_VERB_POST:
			HttpPost httpPost = new HttpPost(uri + this.helper.getQueryString(params));
			httpRequest = httpPost;
			httpPost.setEntity(entity);
			break;
		case HTTP_VERB_PUT:
			HttpPut httpPut = new HttpPut(uri + this.helper.getQueryString(params));
			httpRequest = httpPut;
			httpPut.setEntity(entity);
			break;
		case HTTP_VERB_PATCH:
			HttpPatch httpPatch = new HttpPatch(uri + this.helper.getQueryString(params));
			httpRequest = httpPatch;
			httpPatch.setEntity(entity);
			break;
		case HTTP_VERB_DELETE:
			BasicHttpEntityEnclosingRequest entityRequest = new BasicHttpEntityEnclosingRequest(verb,
					uri + this.helper.getQueryString(params));
			httpRequest = entityRequest;
			entityRequest.setEntity(entity);
			break;
		default:
			httpRequest = new BasicHttpRequest(verb, uri + this.helper.getQueryString(params));
			LOGGER.debug(uri + this.helper.getQueryString(params));
		}

		httpRequest.setHeaders(convertHeaders(headers));
		return httpRequest;
	}

	private MultiValueMap<String, String> revertHeaders(Header[] headers) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Header header : headers) {
			String name = header.getName();
			if (!map.containsKey(name)) {
				map.put(name, new ArrayList<String>());
			}
			map.get(name).add(header.getValue());
		}
		return map;
	}

	private Header[] convertHeaders(MultiValueMap<String, String> headers) {
		List<Header> list = new ArrayList<>();
		for (String name : headers.keySet()) {
			for (String value : headers.get(name)) {
				list.add(new BasicHeader(name, value));
			}
		}
		return list.toArray(new BasicHeader[0]);
	}

	private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost, HttpRequest httpRequest)
			throws IOException {
		return httpclient.execute(httpHost, httpRequest);
	}

	private HttpHost getHttpHost(URL host) {
		HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());
		return httpHost;
	}

	private InputStream getRequestBody(HttpServletRequest request) {
		InputStream requestEntity = null;
		try {
			requestEntity = request.getInputStream();
		} catch (IOException ex) {
			// no requestBody is ok.
		}
		return requestEntity;
	}

	private String getVerb(HttpServletRequest request) {
		String sMethod = request.getMethod();
		return sMethod.toUpperCase();
	}

	private void setResponse(HttpResponse response) throws IOException {
		this.helper.setResponse(response.getStatusLine().getStatusCode(),
				response.getEntity() == null ? null : response.getEntity().getContent(),
				revertHeaders(response.getAllHeaders()));
	}

	/**
	 * Add header names to exclude from proxied response in the current request.
	 * 
	 * @param names
	 */
	protected void addIgnoredHeaders(String... names) {
		this.helper.addIgnoredHeaders(names);
	}

	/**
	 * Determines whether the filter enables the validation for ssl hostnames.
	 * 
	 * @return true if enabled
	 */
	protected boolean isSslHostnameValidationEnabled() {
		return this.sslHostnameValidationEnabled;
	}

	/**
	 * Check if we have all necessary information in order to validate the ssl
	 * connection.
	 * 
	 * @throws SSLException
	 */
	protected void checkValidationInformationAvailable() throws SSLException {
		boolean available = !StringUtils.isEmpty(this.trustStorePath) && !StringUtils.isEmpty(this.trustStorePassword);
		if (!available) {
			throw new SSLException("TrustStorePath and TrustStorePassword are mandatory.");
		}
	}

	/**
	 * Load trust manager allows to validate ssl certificate.
	 * 
	 * @return Trust manager.
	 * @throws SSLException
	 */
	protected X509TrustManager getX509TrustManager() throws SSLException {
		try {
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			InputStream is = new FileInputStream(trustStorePath);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(is, trustStorePassword.toCharArray());
			is.close();
			tmf.init(ks);
			Optional<TrustManager> trm = Arrays.asList(tmf.getTrustManagers()).stream()
					.filter(t -> t instanceof X509TrustManager).findFirst();
			return (X509TrustManager) trm.orElseThrow(NoSuchElementException::new);
		} catch (Exception ex) {
			LOGGER.error("An error occured during retrieving of X509 trust manager", ex);
			throw new SSLException("An error occured during retrieving of X509 trust manager", ex);
		}
	}

	/**
	 * Build a bypass trust manager.
	 * 
	 * @return Trust manager which always validate certificate.
	 */
	protected X509TrustManager buildByPassTrustManager() {
		return new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
	}
}
