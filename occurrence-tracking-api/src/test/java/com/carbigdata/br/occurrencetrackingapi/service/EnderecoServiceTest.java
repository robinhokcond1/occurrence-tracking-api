package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    void setup() {
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
    void deveSalvarEnderecoComSucesso() {
        when(enderecoRepository.save(any(EnderecoEntity.class))).thenReturn(enderecoEntity);

        EnderecoDTO resultado = enderecoService.salvarEndereco(enderecoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getLogradouro()).isEqualTo(enderecoDTO.getLogradouro());
        assertThat(resultado.getCidade()).isEqualTo(enderecoDTO.getCidade());

        verify(enderecoRepository, times(1)).save(any(EnderecoEntity.class));
    }

    @Test
    void deveListarTodosOsEnderecos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EnderecoEntity> page = new PageImpl<>(List.of(enderecoEntity));

        when(enderecoRepository.findAll(pageable)).thenReturn(page);

        Page<EnderecoDTO> resultado = enderecoService.listarEnderecos(pageable);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.getContent().get(0).getCidade()).isEqualTo(enderecoEntity.getCidade());

        verify(enderecoRepository, times(1)).findAll(pageable);
    }

    @Test
    void deveBuscarEnderecoPorIdComSucesso() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoEntity));

        Optional<EnderecoDTO> resultado = enderecoService.buscarEnderecoPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCidade()).isEqualTo(enderecoEntity.getCidade());

        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoEnderecoNaoExiste() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EnderecoDTO> resultado = enderecoService.buscarEnderecoPorId(1L);

        assertThat(resultado).isEmpty();

        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void deveDeletarEnderecoComSucesso() {
        doNothing().when(enderecoRepository).deleteById(1L);

        enderecoService.deletarEndereco(1L);

        verify(enderecoRepository, times(1)).deleteById(1L);
    }
}
