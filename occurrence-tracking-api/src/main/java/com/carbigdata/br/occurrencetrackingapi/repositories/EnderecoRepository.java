package com.carbigdata.br.occurrencetrackingapi.repositories;

import com.carbigdata.br.occurrencetrackingapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
