package com.study.java.lambda.dto;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericRespDto {

    String requestName;
    HttpStatusCode status;
    Object responseBody;
    Class<?> responseType;
}
