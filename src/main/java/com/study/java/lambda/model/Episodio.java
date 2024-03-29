package com.study.java.lambda.model;

import com.study.java.lambda.dto.DadosEpisodio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@NoArgsConstructor
public class Episodio {

    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();

        try {
            this.avaliacao = dadosEpisodio.avaliacao() != null ? Double.valueOf(dadosEpisodio.avaliacao()) : 0.0;
        } catch (NumberFormatException ex) {
            this.avaliacao =  0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dtLancamento());
        }catch (DateTimeParseException ex){
            this.dataLancamento = null;

        }
    }

}
