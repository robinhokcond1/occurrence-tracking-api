package com.carbigdata.br.occurrencetrackingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoOcorrenciaDTO {
    private Long id;
    private String pathBucket;
    private String hash;
}
