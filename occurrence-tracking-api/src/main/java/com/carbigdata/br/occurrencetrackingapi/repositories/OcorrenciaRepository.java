package com.carbigdata.br.occurrencetrackingapi.repositories;

import com.carbigdata.br.occurrencetrackingapi.entities.Ocorrencia;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByStatusOcorrencia(StatusOcorrencia status);
}
