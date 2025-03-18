package com.carbigdata.br.occurrencetrackingapi.services;

import com.carbigdata.br.occurrencetrackingapi.entities.Ocorrencia;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrencia;
import com.carbigdata.br.occurrencetrackingapi.repositories.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }
    public Ocorrencia registrarOcorrencia(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.save(ocorrencia);
    }

    public List<Ocorrencia> listarOcorrencias() {
        return ocorrenciaRepository.findAll();
    }

    public Optional<Ocorrencia> buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id);
    }

    public Ocorrencia finalizarOcorrencia(Long id) {
        return ocorrenciaRepository.findById(id).map(ocorrencia -> {
            ocorrencia.setStatusOcorrencia(StatusOcorrencia.FINALIZADA);
            return ocorrenciaRepository.save(ocorrencia);
        }).orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
    }
}
