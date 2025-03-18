package com.carbigdata.br.occurrencetrackingapi.repositories;

import com.carbigdata.br.occurrencetrackingapi.entities.FotoOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoOcorrenciaRepository extends JpaRepository<FotoOcorrencia, Long> {
    List<FotoOcorrencia> findByOcorrenciaId(Long ocorrenciaId);
}
