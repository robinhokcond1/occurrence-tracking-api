package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface OcorrenciaRepository extends JpaRepository<OcorrenciaEntity, Long> {

    Page<OcorrenciaEntity> findByStatusOcorrencia(StatusOcorrenciaEnum status, Pageable pageable);


    @Query("SELECT o FROM OcorrenciaEntity o " +
            "WHERE (:cpf IS NULL OR o.cliente.cpf = :cpf) " +
            "AND (:nomeCliente IS NULL OR LOWER(o.cliente.nome) LIKE LOWER(CONCAT('%', :nomeCliente, '%'))) " +
            "AND (:dataOcorrencia IS NULL OR DATE(o.dataOcorrencia) = :dataOcorrencia) " +
            "AND (:cidade IS NULL OR LOWER(o.endereco.cidade) LIKE LOWER(CONCAT('%', :cidade, '%')))")
    Page<OcorrenciaEntity> findByFilters(
            String cpf,
            String nomeCliente,
            LocalDate dataOcorrencia,
            String cidade,
            Pageable pageable);

}