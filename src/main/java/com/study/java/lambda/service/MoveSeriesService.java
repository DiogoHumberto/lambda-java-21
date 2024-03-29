package com.study.java.lambda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.java.lambda.dto.DadosEpisodio;
import com.study.java.lambda.dto.DadosSerie;
import com.study.java.lambda.dto.DadosTemporada;
import com.study.java.lambda.dto.GenericRespDto;
import com.study.java.lambda.model.Episodio;
import com.study.java.lambda.utils.ClientHttp;
import com.study.java.lambda.utils.LocalObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.study.java.lambda.utils.AsyncUtils.callAndReturn;

@Log4j2
@Service
@RequiredArgsConstructor
public class MoveSeriesService {

    private final ClientHttp clientHttp;

    private final String URL_BASE = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public List<?> findAllSerie(String name){

        ResponseEntity<DadosSerie> respEntitySerie = clientHttp.realizarChamadaHttp(null, null, HttpMethod.GET, URL_BASE + name.replace(" ", "+") + API_KEY, DadosSerie.class );

        DadosSerie respSerie = respEntitySerie.getBody();

        List<DadosTemporada> temporadas = new ArrayList<>();


        List<Callable<Object>> callableList = new ArrayList<>();

        IntStream.rangeClosed(1, respSerie.totalTemporadas()).forEach(i ->
                callableList.add(() -> featureCall(URL_BASE + name.replace(" ", "+") + "&season=" + i + API_KEY))
        );


        var respTemps = callAndReturn(callableList);

        respTemps.entrySet().stream().forEach(genericResp ->{

            if( genericResp.getValue().getStatus().is2xxSuccessful()){

                try {
                    temporadas.add(LocalObjectMapper.readAsType(genericResp.getValue().getResponseBody().toString(), DadosTemporada.class));
                } catch (JsonProcessingException e) {
                   log.error("parse  {} msg {}" , DadosTemporada.class, e.getMessage());
                }
            }

        });

//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

//        for (int i = 1; i<= respSerie.totalTemporadas(); i++){
//            ResponseEntity<DadosTemporada> respEntityTemporada = clientHttp.realizarChamadaHttp(null, null, HttpMethod.GET, URL_BASE + name.replace(" ", "+") +"&season=" + i + API_KEY, DadosTemporada.class );
//            temporadas.add(respEntityTemporada.getBody());
//        }

        return episodios;
    }

    @Async
    private Future<GenericRespDto> featureCall(String url) throws JsonProcessingException {

        ResponseEntity<?> resp = clientHttp.realizarChamadaHttp( new HttpHeaders(), null, HttpMethod.GET, url, String.class);

        return CompletableFuture.supplyAsync(() ->
                GenericRespDto.builder()
                        .requestName(url)
                        .status(resp.getStatusCode())
                        .responseBody(resp.getBody())
                        .responseType(String.class)
                        .build());
    }


}
