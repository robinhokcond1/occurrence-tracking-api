package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.FotoOcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.FotoOcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FotoOcorrenciaService {

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    public FotoOcorrenciaDTO salvarFoto(FotoOcorrenciaEntity fotoOcorrencia) {
        FotoOcorrenciaEntity novaFoto = fotoOcorrenciaRepository.save(fotoOcorrencia);
        return DtoConverter.toFotoOcorrenciaDTO(novaFoto);
    }

    public Page<FotoOcorrenciaDTO> listarFotosPorOcorrencia(Long ocorrenciaId, Pageable pageable) {
        return fotoOcorrenciaRepository.findByOcorrenciaId(ocorrenciaId, pageable)
                .map(DtoConverter::toFotoOcorrenciaDTO);
    }

    public Optional<FotoOcorrenciaDTO> buscarFotoPorId(Long id) {
        return fotoOcorrenciaRepository.findById(id)
                .map(DtoConverter::toFotoOcorrenciaDTO);
    }

    public void deletarFoto(Long id) {
        fotoOcorrenciaRepository.deleteById(id);
    }
}
