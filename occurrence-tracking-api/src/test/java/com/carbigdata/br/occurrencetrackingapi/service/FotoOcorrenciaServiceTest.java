package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.FotoOcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.FotoOcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FotoOcorrenciaServiceTest {

    @Mock
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private FotoOcorrenciaService fotoOcorrenciaService;

    private FotoOcorrenciaEntity fotoEntity;
    private OcorrenciaEntity ocorrencia;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        ocorrencia = new OcorrenciaEntity();
        ocorrencia.setId(1L);

        fotoEntity = new FotoOcorrenciaEntity();
        fotoEntity.setId(1L);
        fotoEntity.setOcorrencia(ocorrencia);
        fotoEntity.setDscPathBucket("minio/path/file.jpg");
        fotoEntity.setDscHash(UUID.randomUUID().toString());

        file = mock(MultipartFile.class);
    }

    @Test
    void deveSalvarFotoComSucesso() {
        // Arrange
        when(ocorrenciaRepository.findById(1L)).thenReturn(Optional.of(ocorrencia));
        when(minioService.uploadFile(file, 1L)).thenReturn("minio/path/file.jpg");
        when(fotoOcorrenciaRepository.save(any(FotoOcorrenciaEntity.class))).thenReturn(fotoEntity);

        // Act
        FotoOcorrenciaDTO resultado = fotoOcorrenciaService.salvarFoto(1L, file);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getPathBucket()).isEqualTo("minio/path/file.jpg");

        verify(ocorrenciaRepository).findById(1L);
        verify(minioService).uploadFile(file, 1L);
        verify(fotoOcorrenciaRepository).save(any(FotoOcorrenciaEntity.class));
    }

    @Test
    void deveListarFotosPorOcorrencia() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<FotoOcorrenciaEntity> page = new PageImpl<>(List.of(fotoEntity));
        when(fotoOcorrenciaRepository.findByOcorrenciaId(1L, pageable)).thenReturn(page);

        // Act
        Page<FotoOcorrenciaDTO> resultado = fotoOcorrenciaService.listarFotosPorOcorrencia(1L, pageable);

        // Assert
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.getContent().get(0).getPathBucket()).isEqualTo("minio/path/file.jpg");
        verify(fotoOcorrenciaRepository).findByOcorrenciaId(1L, pageable);
    }

    @Test
    void deveBuscarFotoPorIdComSucesso() {
        when(fotoOcorrenciaRepository.findById(1L)).thenReturn(Optional.of(fotoEntity));

        Optional<FotoOcorrenciaDTO> resultado = fotoOcorrenciaService.buscarFotoPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getPathBucket()).isEqualTo("minio/path/file.jpg");
        verify(fotoOcorrenciaRepository).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoFotoNaoExiste() {
        when(fotoOcorrenciaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<FotoOcorrenciaDTO> resultado = fotoOcorrenciaService.buscarFotoPorId(1L);

        assertThat(resultado).isEmpty();
        verify(fotoOcorrenciaRepository).findById(1L);
    }

    @Test
    void deveDeletarFotoComSucesso() {
        when(fotoOcorrenciaRepository.existsById(1L)).thenReturn(true); // simula que o endereço existe

        fotoOcorrenciaService.deletarFoto(1L);

        verify(fotoOcorrenciaRepository).deleteById(1L);
    }
}
