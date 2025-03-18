package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByCpf(String cpf);
}
