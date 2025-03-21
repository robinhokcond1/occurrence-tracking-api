package com.carbigdata.br.occurrencetrackingapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OcorrenciaResponseDTO {
    private Long id;
    private String clienteNome;
    private String cpf;
    private String endereco;
    private String cidade;
    private LocalDateTime dataOcorrencia;
    private String evidenciasUrl;
}

