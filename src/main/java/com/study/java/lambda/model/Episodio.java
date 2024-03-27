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
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dtLancamento());

        } catch (NumberFormatException | DateTimeParseException ex) {

            this.avaliacao =  ex.getCause() instanceof NumberFormatException ? 0.0 :  this.avaliacao;
            this.dataLancamento =  ex.getCause() instanceof DateTimeParseException ? null : this.dataLancamento;
        }
    }

}
