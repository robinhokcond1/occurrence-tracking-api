package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<OcorrenciaEntity, Long> {

    Page<OcorrenciaEntity> findByStatusOcorrencia(StatusOcorrenciaEnum status, Pageable pageable);
}