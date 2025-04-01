package com.carbigdata.br.occurrencetrackingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos.")
    private String cep;

    @NotBlank(message = "A cidade não pode estar vazia.")
    private String cidade;

    @NotBlank(message = "O estado não pode estar vazio.")
    @Pattern(regexp = "[A-Z]{2}", message = "O estado deve conter exatamente 2 letras maiúsculas (UF).")
    private String estado;
}
