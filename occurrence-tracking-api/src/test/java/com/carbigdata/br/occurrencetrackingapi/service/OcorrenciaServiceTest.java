package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaResponseDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import com.carbigdata.br.occurrencetrackingapi.repository.ClienteRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OcorrenciaServiceTest {

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private OcorrenciaService ocorrenciaService;

    private ClienteEntity cliente;
    private EnderecoEntity endereco;
    private OcorrenciaEntity ocorrencia;
    private OcorrenciaCreateDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("12345678900");

        endereco = new EnderecoEntity();
        endereco.setId(1L);
        endereco.setCidade("São Paulo");
        endereco.setLogradouro("Rua A");

        ocorrencia = new OcorrenciaEntity();
        ocorrencia.setId(1L);
        ocorrencia.setCliente(cliente);
        ocorrencia.setEndereco(endereco);
        ocorrencia.setDataOcorrencia(LocalDateTime.now());
        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.ATIVO);

        dto = new OcorrenciaCreateDTO();
        dto.setClienteId(1L);
        dto.setEnderecoId(1L);
        dto.setDataOcorrencia(LocalDateTime.now());
    }

    @Test
    void deveRegistrarOcorrencia() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(ocorrenciaRepository.save(any(OcorrenciaEntity.class))).thenReturn(ocorrencia);

        OcorrenciaDTO resultado = ocorrenciaService.registrarOcorrencia(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getCliente().getNome());
        assertEquals("São Paulo", resultado.getEndereco().getCidade());

        verify(ocorrenciaRepository, times(1)).save(any(OcorrenciaEntity.class));
    }

    @Test
    void deveFinalizarOcorrencia() {
        when(ocorrenciaRepository.findById(1L)).thenReturn(Optional.of(ocorrencia));
        when(ocorrenciaRepository.save(any(OcorrenciaEntity.class))).thenReturn(ocorrencia);

        OcorrenciaDTO resultado = ocorrenciaService.finalizarOcorrencia(1L);

        assertThat(resultado.getStatusOcorrencia()).isEqualTo(StatusOcorrenciaEnum.FINALIZADA);
        verify(ocorrenciaRepository, times(1)).save(any(OcorrenciaEntity.class));
    }

    @Test
    void deveListarOcorrenciasComFiltros() {
        Page<OcorrenciaEntity> ocorrenciaPage = new PageImpl<>(List.of(ocorrencia));
        when(ocorrenciaRepository.findByFilters(any(), any(), any(), any(), any())).thenReturn(ocorrenciaPage);

        Page<OcorrenciaResponseDTO> resultado = ocorrenciaService.listarOcorrencias("12345678900", "João", null, "São Paulo", Pageable.unpaged());

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent().get(0).getClienteNome()).isEqualTo("João Silva");
        verify(ocorrenciaRepository, times(1)).findByFilters(any(), any(), any(), any(), any());
    }

    @Test
    void deveAtualizarOcorrencia() {
        when(ocorrenciaRepository.findById(1L)).thenReturn(Optional.of(ocorrencia));
        when(ocorrenciaRepository.save(any(OcorrenciaEntity.class))).thenReturn(ocorrencia);

        OcorrenciaDTO resultado = ocorrenciaService.atualizarOcorrencia(1L, dto);

        assertThat(resultado).isNotNull();
        verify(ocorrenciaRepository, times(1)).save(any(OcorrenciaEntity.class));
    }



}
