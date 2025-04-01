package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.FotoOcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.exception.RecursoNaoEncontradoException;
import com.carbigdata.br.occurrencetrackingapi.repository.FotoOcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FotoOcorrenciaService {

    private final FotoOcorrenciaRepository fotoOcorrenciaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final MinioService minioService;

    public FotoOcorrenciaDTO salvarFoto(Long ocorrenciaId, MultipartFile file) {
        String pathBucket;
        try {
            pathBucket = minioService.uploadFile(file, ocorrenciaId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar arquivo para MinIO", e);
        }

        return salvarFotoNaBase(ocorrenciaId, pathBucket);
    }

    @Transactional
    protected FotoOcorrenciaDTO salvarFotoNaBase(Long ocorrenciaId, String pathBucket) {
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(ocorrenciaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência não encontrada com ID: " + ocorrenciaId));

        FotoOcorrenciaEntity foto = new FotoOcorrenciaEntity();
        foto.setOcorrencia(ocorrencia);
        foto.setDscPathBucket(pathBucket);
        foto.setDscHash(UUID.randomUUID().toString());

        FotoOcorrenciaEntity savedFoto = fotoOcorrenciaRepository.save(foto);
        return DtoConverter.toFotoOcorrenciaDTO(savedFoto);
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
        if (!fotoOcorrenciaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Foto não encontrada com ID: " + id);
        }
        fotoOcorrenciaRepository.deleteById(id);
    }
}
