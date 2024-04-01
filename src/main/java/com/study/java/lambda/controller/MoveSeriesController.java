package com.study.java.lambda.controller;

import com.study.java.lambda.dto.DadosSerie;
import com.study.java.lambda.service.MoveSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class MoveSeriesController {

    private final MoveSeriesService service;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllAboutOneSerie (@RequestParam String nameSerie,
                                                   @RequestParam(required = false) String episodio,
                                                   @RequestParam(required = false) Double avaliacao){

        return ResponseEntity.ok(service.findFilterSerie(nameSerie, episodio, avaliacao));
    }

}
