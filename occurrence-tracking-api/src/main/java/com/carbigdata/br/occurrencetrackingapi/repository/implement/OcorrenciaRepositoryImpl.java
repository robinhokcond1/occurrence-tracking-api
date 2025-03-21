package com.carbigdata.br.occurrencetrackingapi.repository.implement;

import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OcorrenciaRepositoryImpl implements OcorrenciaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<OcorrenciaEntity> findByFilters(String cpf, String nomeCliente, LocalDate dataOcorrencia, String cidade, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OcorrenciaEntity> cq = cb.createQuery(OcorrenciaEntity.class);
        Root<OcorrenciaEntity> ocorrencia = cq.from(OcorrenciaEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (cpf != null && !cpf.isBlank()) {
            predicates.add(cb.equal(ocorrencia.get("cliente").get("cpf"), cpf));
        }

        if (nomeCliente != null && !nomeCliente.isBlank()) {
            predicates.add(cb.like(cb.lower(ocorrencia.get("cliente").get("nome")), "%" + nomeCliente.toLowerCase() + "%"));
        }

        if (dataOcorrencia != null) {
            predicates.add(cb.equal(ocorrencia.get("dataOcorrencia"), dataOcorrencia));
        }

        if (cidade != null && !cidade.isBlank()) {
            predicates.add(cb.like(cb.lower(ocorrencia.get("endereco").get("cidade")), "%" + cidade.toLowerCase() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<OcorrenciaEntity> query = entityManager.createQuery(cq);

        // Paginação
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<OcorrenciaEntity> resultList = query.getResultList();

        return new PageImpl<>(resultList, pageable, resultList.size());
    }
}

