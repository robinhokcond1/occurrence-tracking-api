package com.carbigdata.br.occurrencetrackingapi.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErroPadraoDTO {
    private Instant timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String caminho;
}
