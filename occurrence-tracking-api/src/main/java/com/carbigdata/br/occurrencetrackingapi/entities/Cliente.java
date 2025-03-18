package com.carbigdata.br.occurrencetrackingapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_cliente")
    private Long codClienteId;

    @Column(name = "nme_cliente", length = 50, nullable = false)
    private String nome;

    @Column(name = "dta_nascimento")
    private LocalDateTime dataNascimento;

    @Column(name = "nro_cpf",nullable = false)
    private String cpf;

    @Column(name = "dta_criacao",nullable = false)
    private LocalDateTime dataCriacao;

}
