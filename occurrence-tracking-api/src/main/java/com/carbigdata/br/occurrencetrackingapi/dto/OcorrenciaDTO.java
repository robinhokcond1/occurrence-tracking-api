package com.carbigdata.br.occurrencetrackingapi.dto;

import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OcorrenciaDTO {
    private Long id;
    private ClienteDTO cliente;
    private EnderecoDTO endereco;
    private LocalDateTime dataOcorrencia;
    private StatusOcorrencia statusOcorrencia;
}
