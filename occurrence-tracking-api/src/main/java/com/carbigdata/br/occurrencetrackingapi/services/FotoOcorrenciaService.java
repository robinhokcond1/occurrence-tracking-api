package com.carbigdata.br.occurrencetrackingapi.services;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entities.FotoOcorrencia;
import com.carbigdata.br.occurrencetrackingapi.repositories.FotoOcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FotoOcorrenciaService {

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    public FotoOcorrenciaDTO salvarFoto(FotoOcorrencia fotoOcorrencia) {
        FotoOcorrencia novaFoto = fotoOcorrenciaRepository.save(fotoOcorrencia);
        return DtoConverter.toFotoOcorrenciaDTO(novaFoto);
    }

    public List<FotoOcorrenciaDTO> listarFotosPorOcorrencia(Long ocorrenciaId) {
        return fotoOcorrenciaRepository.findByOcorrenciaId(ocorrenciaId)
                .stream()
                .map(DtoConverter::toFotoOcorrenciaDTO)
                .collect(Collectors.toList());
    }

    public Optional<FotoOcorrenciaDTO> buscarFotoPorId(Long id) {
        return fotoOcorrenciaRepository.findById(id)
                .map(DtoConverter::toFotoOcorrenciaDTO);
    }

    public void deletarFoto(Long id) {
        fotoOcorrenciaRepository.deleteById(id);
    }
}
