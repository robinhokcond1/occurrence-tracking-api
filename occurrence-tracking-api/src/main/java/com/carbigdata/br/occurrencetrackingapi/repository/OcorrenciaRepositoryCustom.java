package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OcorrenciaRepositoryCustom {
    Page<OcorrenciaEntity> findByFilters(String cpf, String nomeCliente, LocalDate dataOcorrencia, String cidade, Pageable pageable);
}

