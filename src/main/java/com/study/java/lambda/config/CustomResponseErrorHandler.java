package com.study.java.lambda.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Log4j2
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        // your error handling here
        log.error("Error handler foi acionado");
        log.error(response.getStatusCode());
        log.error(response.getBody());
    }
}
