package com.carbigdata.br.occurrencetrackingapi.entities;

import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ocorrencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_ocorrencia")
    private Long codOcorrenciaId;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "cod_endereco", nullable = false)
    private Endereco endereco;

    @Column(name = "dta_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "sta_ocorrencia", nullable = false)
    private StatusOcorrencia statusOcorrencia;

}
