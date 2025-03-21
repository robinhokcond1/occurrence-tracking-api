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
        System.out.println("🔹 MÉTODO salvarFoto INICIADO!"); // <--- Adicionado
        try {
            OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(ocorrenciaId)
                    .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

            String pathBucket = minioService.uploadFile(file, ocorrenciaId);
            System.out.println("Imagem enviada para MinIO: " + pathBucket);

            FotoOcorrenciaEntity foto = new FotoOcorrenciaEntity();
            foto.setOcorrencia(ocorrencia);
            foto.setDscPathBucket(pathBucket);
            foto.setDscHash(UUID.randomUUID().toString());

            FotoOcorrenciaEntity savedFoto = fotoOcorrenciaRepository.save(foto);
            System.out.println("Foto persistida no banco: ID -> " + savedFoto.getId());

            return DtoConverter.toFotoOcorrenciaDTO(savedFoto);
        } catch (Exception e) {
            System.err.println("Erro ao salvar a foto: " + e.getMessage());
            throw e;
        }
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
