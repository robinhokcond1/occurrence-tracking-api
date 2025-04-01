package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.exception.RecursoNaoEncontradoException;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Transactional
    public EnderecoDTO salvarEndereco(EnderecoDTO enderecoDTO) {
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());

        return DtoConverter.toEnderecoDTO(enderecoRepository.save(endereco));
    }

    @Transactional(readOnly = true)
    public Page<EnderecoDTO> listarEnderecos(Pageable pageable) {
        return enderecoRepository.findAll(pageable)
                .map(DtoConverter::toEnderecoDTO);
    }

    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(DtoConverter::toEnderecoDTO);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}
