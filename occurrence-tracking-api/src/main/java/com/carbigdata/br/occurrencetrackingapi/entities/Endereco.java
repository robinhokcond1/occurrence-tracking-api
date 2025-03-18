package com.carbigdata.br.occurrencetrackingapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_endereco")
    private Long codEndercoId;

    @Column(name = "nme_logradouro")
    private String logradouro;

    @Column(name = "nme_bairro")
    private String bairro;

    @Column(name = "nro_cep")
    private String cep;

    @Column(name = "nme_cidade")
    private String cidade;

    @Column(name = "nme_estado")
    private String estado;


}
