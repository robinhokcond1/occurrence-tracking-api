package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private EnderecoDTO enderecoDTO;
    private EnderecoEntity enderecoEntity;

    @BeforeEach
    void setUp() {
        enderecoDTO = new EnderecoDTO();
        enderecoDTO.setLogradouro("Rua A");
        enderecoDTO.setBairro("Centro");
        enderecoDTO.setCep("12345-678");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setEstado("SP");

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setId(1L);
        enderecoEntity.setLogradouro("Rua A");
        enderecoEntity.setBairro("Centro");
        enderecoEntity.setCep("12345-678");
        enderecoEntity.setCidade("São Paulo");
        enderecoEntity.setEstado("SP");
    }

    @Test
    void deveSalvarEndereco() {
        when(enderecoRepository.save(any(EnderecoEntity.class))).thenReturn(enderecoEntity);

        EnderecoDTO result = enderecoService.salvarEndereco(enderecoDTO);

        assertThat(result).isNotNull();
        assertThat(result.getLogradouro()).isEqualTo(enderecoDTO.getLogradouro());

        verify(enderecoRepository).save(any(EnderecoEntity.class));
    }

    @Test
    void deveListarEnderecosPaginados() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EnderecoEntity> page = new PageImpl<>(List.of(enderecoEntity));

        when(enderecoRepository.findAll(pageable)).thenReturn(page);

        Page<EnderecoDTO> resultado = enderecoService.listarEnderecos(pageable);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getContent().get(0).getCidade()).isEqualTo("São Paulo");

        verify(enderecoRepository).findAll(pageable);
    }

    @Test
    void deveBuscarEnderecoPorId() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoEntity));

        Optional<EnderecoDTO> resultado = enderecoService.buscarEnderecoPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCep()).isEqualTo(enderecoEntity.getCep());

        verify(enderecoRepository).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoIdNaoEncontrado() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EnderecoDTO> resultado = enderecoService.buscarEnderecoPorId(1L);

        assertThat(resultado).isEmpty();

        verify(enderecoRepository).findById(1L);
    }

    @Test
    void deveDeletarEnderecoPorId() {
        when(enderecoRepository.existsById(1L)).thenReturn(true); // simula que o endereço existe

        enderecoService.deletarEndereco(1L);

        verify(enderecoRepository).existsById(1L);
        verify(enderecoRepository).deleteById(1L);
    }

}
