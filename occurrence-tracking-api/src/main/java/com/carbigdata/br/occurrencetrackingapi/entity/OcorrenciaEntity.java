package com.carbigdata.br.occurrencetrackingapi.entity;

import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OcorrenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_ocorrencia")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "cod_endereco", nullable = false)
    private EnderecoEntity endereco;

    @Column(name = "dta_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "sta_ocorrencia", nullable = false)
    private StatusOcorrenciaEnum statusOcorrencia;

    @Column(name = "evidencia_path")
    private String evidenciaPath;

}
