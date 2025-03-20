package com.carbigdata.br.occurrencetrackingapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OcorrenciaCreateDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    @NotNull(message = "O ID do endereço é obrigatório.")
    private Long enderecoId;

    @NotNull(message = "A data da ocorrência é obrigatória.")
    private LocalDateTime dataOcorrencia;
}

