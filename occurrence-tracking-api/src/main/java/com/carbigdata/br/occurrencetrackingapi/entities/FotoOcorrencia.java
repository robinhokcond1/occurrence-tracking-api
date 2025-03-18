package com.carbigdata.br.occurrencetrackingapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "foto_ocorrencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FotoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_foto_ocorrencia")
    private String codFotOcorrencia;

    @ManyToOne
    @JoinColumn(name = "cod_ocorrencia", nullable = false)
    private Ocorrencia ocorrencia;

    @Column(name = "dta_criacao",nullable = false)
    private String dataCriacao;

    @Column(name = "dsc_path_bucket", nullable = false)
    private String dscPathBucket;

    @Column(name = "dsc_hash", nullable = false)
    private String dscHash;
}
