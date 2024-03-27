package com.study.java.lambda.service;

import com.study.java.lambda.dto.DadosSerie;
import com.study.java.lambda.utils.ClientHttp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoveSeriesService {

    private final ClientHttp clientHttp;

    private final String URL_BASE = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public ResponseEntity findAllSerie(String name){

        return clientHttp.realizarChamadaHttp(null, null, HttpMethod.GET, URL_BASE + name.replace(" ", "+") + API_KEY, DadosSerie.class );

    }


}
