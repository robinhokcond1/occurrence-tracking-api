package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.FotoOcorrenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoOcorrenciaRepository extends JpaRepository<FotoOcorrenciaEntity, Long> {
    List<FotoOcorrenciaEntity> findByOcorrenciaId(Long id);
}
