package com.github.rjansem.microservices.training.apisecurity.config;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import rx.Single;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration de Swagger
 *
 * @author rjansem
 */
@Configuration
@EnableSwagger2
@Profile(ProfileConstants.NOT_TEST_PROFILE)
public class SwaggerConfig {

    private static final String DEFAULT_INCLUDE_PATTERN = ServicesUris.API + "/.*";

    private final ApiProperties apiProperties;

    @Autowired
    public SwaggerConfig(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    @Bean
    public Docket swaggerDocket(@Value("${auth.gateway.url}") String authUrl) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .forCodeGeneration(true)
                .genericModelSubstitutes(Single.class)
                .ignoredParameterTypes(AuthenticatedUser.class)
                .select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(apiProperties.getTitle(),
                apiProperties.getDescription(),
                apiProperties.getVersion(),
                apiProperties.getTermsOfServiceUrl(),
                getContact(),
                apiProperties.getLicense(),
                apiProperties.getLicenseUrl());
    }

    private Contact getContact() {
        ApiProperties.Contact contactProperties = apiProperties.getContact();
        return new Contact(contactProperties.getName(), contactProperties.getUrl(), contactProperties.getMail());
    }

}