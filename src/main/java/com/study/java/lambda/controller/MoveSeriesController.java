package com.study.java.lambda.controller;

import com.study.java.lambda.service.MoveSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class MoveSeriesController {

    private final MoveSeriesService service;

    @GetMapping("/findAll")
    public ResponseEntity findAllAboutOneSerie (@RequestParam String nameSerie){

        return service.findAllSerie(nameSerie);
    }

}
