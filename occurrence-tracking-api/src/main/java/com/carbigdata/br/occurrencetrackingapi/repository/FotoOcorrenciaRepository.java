package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.FotoOcorrenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoOcorrenciaRepository extends JpaRepository<FotoOcorrenciaEntity, Long> {
    Page<FotoOcorrenciaEntity> findByOcorrenciaId(Long ocorrenciaId, Pageable pageable);
}
