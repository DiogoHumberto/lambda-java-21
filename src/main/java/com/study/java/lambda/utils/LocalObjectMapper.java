package com.study.java.lambda.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class LocalObjectMapper {
    private LocalObjectMapper() {
    }

    public static String writeValueAsString(Object parcelasValidasList) throws JsonProcessingException {
        //define um object mapper configurando tratamento dos bigdecimal para evitar notação cientifica
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        // Configure ObjectMapper to ignore null values during serialization
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper = objectMapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(parcelasValidasList);
    }

    public static <T> T readAsType(String data, Class<T> type) throws JsonProcessingException {
        //define um object mapper configurando tratamento dos bigdecimal para evitar notação cientifica
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper = objectMapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(data, type);
    }
}
