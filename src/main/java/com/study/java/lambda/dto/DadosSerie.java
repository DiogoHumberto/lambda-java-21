package com.study.java.lambda.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosSerie {
    @JsonAlias("Title")
    String titulo;
    @JsonAlias("totalSeasons")
    Integer totalTemp;
    @JsonAlias("imdbRating")
    String avaliacao;
}
