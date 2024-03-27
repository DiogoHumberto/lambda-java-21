package com.study.java.lambda.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientHttp {

    private final RestTemplate restTemplate;

    /**
     * realizarChamadaHttp
     *
     * @param <T>          tipo genérico de retorno.
     * @param headers      cabeçalhos necesários para chamada.
     * @param method       método rest utilizado.
     * @param url          endpoint que será acionado.
     * @param responseType Classe de retorno da chamada.
     * @return ResponseEntity<T> generic reponse entity.
     */
    public <T> ResponseEntity<T> realizarChamadaHttp(HttpHeaders headers, Object body, HttpMethod method, String url, Class<T> responseType) {
        //MELHORAR LOG
        String objectToString = null;
        if (ObjectUtils.isNotEmpty(body)) {
            try {
                objectToString = LocalObjectMapper.writeValueAsString(body);
            }
            catch (JsonProcessingException e)
            {
                objectToString = "";
            }
        }
        log.info("---- Iniciando chamada header: {} , http: {} , url: {} , body: {} ", headers, method.name(), url, objectToString);
        return  restTemplate.exchange(url, method, new HttpEntity<>(objectToString, headers), responseType);
    }
}
