package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.ClienteDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.ClienteRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setup() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João da Silva");
        clienteDTO.setCpf("12345678900");
        clienteDTO.setDataNascimento(LocalDate.of(1990, 1, 1).atStartOfDay());

        clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setNome("João da Silva");
        clienteEntity.setCpf("12345678900");
        clienteEntity.setDataNascimento(LocalDate.of(1990, 1, 1).atStartOfDay());
    }

    @Test
    void deveCriarClienteComSucesso() {
        when(clienteRepository.findByCpf(clienteDTO.getCpf())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        ClienteDTO resultado = clienteService.criarCliente(clienteDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo(clienteDTO.getNome());
        assertThat(resultado.getCpf()).isEqualTo(clienteDTO.getCpf());

        verify(clienteRepository, times(1)).findByCpf(clienteDTO.getCpf());
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void naoDeveCriarClienteQuandoCpfJaExiste() {
        when(clienteRepository.findByCpf(clienteDTO.getCpf())).thenReturn(Optional.of(clienteEntity));

        assertThatThrownBy(() -> clienteService.criarCliente(clienteDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("CPF já cadastrado!");

        verify(clienteRepository, times(1)).findByCpf(clienteDTO.getCpf());
        verify(clienteRepository, never()).save(any(ClienteEntity.class));
    }

    @Test
    void deveListarTodosOsClientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ClienteEntity> page = new PageImpl<>(List.of(clienteEntity));

        when(clienteRepository.findAll(pageable)).thenReturn(page);

        Page<ClienteDTO> resultado = clienteService.listarTodos(pageable);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo(clienteEntity.getNome());

        verify(clienteRepository, times(1)).findAll(pageable);
    }

    @Test
    void deveBuscarClientePorIdComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        Optional<ClienteDTO> resultado = clienteService.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo(clienteEntity.getNome());

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoClienteNaoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ClienteDTO> resultado = clienteService.buscarPorId(1L);

        assertThat(resultado).isEmpty();

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void deveDeletarClienteComSucesso() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarClienteQuandoNaoExiste() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> clienteService.deletarCliente(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente não encontrado!");

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, never()).deleteById(1L);
    }
}
