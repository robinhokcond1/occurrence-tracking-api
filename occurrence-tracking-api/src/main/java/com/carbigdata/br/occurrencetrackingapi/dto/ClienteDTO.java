package com.carbigdata.br.occurrencetrackingapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDateTime dataNascimento;
}
