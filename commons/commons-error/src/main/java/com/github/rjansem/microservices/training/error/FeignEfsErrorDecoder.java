package com.github.rjansem.microservices.training.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rjansem.microservices.training.exception.EfsExceptionBean;
import com.github.rjansem.microservices.training.exception.EfsFeignException;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Décode les erreurs Feign notamment celles renvoyées par EFS
 *
 * @author rjansem
 */
@Component
public class FeignEfsErrorDecoder implements ErrorDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignEfsErrorDecoder.class);

    private static final String EFS_SERVICE_KEY = "efs-service";

    private final ObjectMapper objectMapper;

    @Autowired
    public FeignEfsErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        if (!isEfsServiceRequest(response.request()) || response.body() == null) {
            return new Default().decode(methodKey, response);
        }
        try {
            String message = String.format("status %s reading %s", response.status(), methodKey);
            return new EfsFeignException(response.status(), message, mapEfsException(response));
        } catch (IOException e) {
            LOGGER.error("Erreur lors du parsing de l'exception EFS", e);
            throw new RuntimeException(e);
        }
    }

    private EfsExceptionBean mapEfsException(Response response) throws IOException {
        String body = Util.toString(response.body().asReader());
        return objectMapper.readValue(body, EfsExceptionBean.class);
    }

    private boolean isEfsServiceRequest(Request request) {
        return request != null && request.url() != null && request.url().contains(EFS_SERVICE_KEY);
    }
}
