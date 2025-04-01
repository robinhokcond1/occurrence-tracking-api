package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.ClienteDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.exception.NegocioException;
import com.carbigdata.br.occurrencetrackingapi.exception.RecursoNaoEncontradoException;
import com.carbigdata.br.occurrencetrackingapi.repository.ClienteRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        clienteRepository.findByCpf(clienteDTO.getCpf())
                .ifPresent(c -> {
                    throw new NegocioException("CPF já cadastrado!");
                });

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());

        return DtoConverter.toClienteDTO(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public Page<ClienteDTO> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(DtoConverter::toClienteDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id).map(DtoConverter::toClienteDTO);
    }

    @Transactional
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado com ID:" + id);
        }
        clienteRepository.deleteById(id);
    }
}
