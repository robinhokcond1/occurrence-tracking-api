package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.FotoOcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.FotoOcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FotoOcorrenciaService {

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private MinioService minioService;

    @Transactional
    public FotoOcorrenciaDTO salvarFoto(Long ocorrenciaId, MultipartFile file) {
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(ocorrenciaId)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        // 🔹 Upload da imagem para MinIO
        String pathBucket = minioService.uploadFile(file, ocorrenciaId);

        // 🔹 Salvar a foto no banco de dados
        FotoOcorrenciaEntity foto = new FotoOcorrenciaEntity();
        foto.setOcorrencia(ocorrencia);
        foto.setDscPathBucket(pathBucket);
        foto.setDscHash(UUID.randomUUID().toString());

        return DtoConverter.toFotoOcorrenciaDTO(fotoOcorrenciaRepository.save(foto));
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
