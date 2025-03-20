package com.carbigdata.br.occurrencetrackingapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "O logradouro não pode estar vazio.")
    private String logradouro;

    @NotBlank(message = "O bairro não pode estar vazio.")
    private String bairro;

    @NotBlank(message = "O CEP não pode estar vazio.")
    private String cep;

    @NotBlank(message = "A cidade não pode estar vazia.")
    private String cidade;

    @NotBlank(message = "O estado não pode estar vazio.")
    private String estado;
}

