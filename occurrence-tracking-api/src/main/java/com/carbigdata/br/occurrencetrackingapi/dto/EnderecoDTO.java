package com.carbigdata.br.occurrencetrackingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private Long id;
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
}
