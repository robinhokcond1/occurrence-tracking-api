package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

}
