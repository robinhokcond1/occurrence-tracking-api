package com.carbigdata.br.occurrencetrackingapi.services;

import com.carbigdata.br.occurrencetrackingapi.entities.FotoOcorrencia;
import com.carbigdata.br.occurrencetrackingapi.repositories.FotoOcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoOcorrenciaService {

    private final FotoOcorrenciaRepository fotoOcorrenciaRepository;

    public FotoOcorrenciaService(FotoOcorrenciaRepository fotoOcorrenciaRepository) {
        this.fotoOcorrenciaRepository = fotoOcorrenciaRepository;
    }
    public FotoOcorrencia salvarFoto(FotoOcorrencia fotoOcorrencia) {
        return fotoOcorrenciaRepository.save(fotoOcorrencia);
    }

    public List<FotoOcorrencia> listarFotosPorOcorrencia(Long ocorrenciaId) {
        return fotoOcorrenciaRepository.findByOcorrenciaId(ocorrenciaId);
    }

    public Optional<FotoOcorrencia> buscarFotoPorId(Long id) {
        return fotoOcorrenciaRepository.findById(id);
    }

    public void deletarFoto(Long id) {
        fotoOcorrenciaRepository.deleteById(id);
    }
}
