package com.carbigdata.br.occurrencetrackingapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OcorrenciaCreateDTO {
    private Long clienteId;
    private Long enderecoId;
    private LocalDateTime dataOcorrencia;
}
