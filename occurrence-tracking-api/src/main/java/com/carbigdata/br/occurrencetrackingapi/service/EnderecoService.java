package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoDTO salvarEndereco(EnderecoDTO enderecoDTO) {
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());

        EnderecoEntity novoEndereco = enderecoRepository.save(endereco);
        return DtoConverter.toEnderecoDTO(novoEndereco);
    }

    public List<EnderecoDTO> listarEnderecos() {
        return enderecoRepository.findAll()
                .stream()
                .map(DtoConverter::toEnderecoDTO)
                .collect(Collectors.toList());
    }

    public Optional<EnderecoDTO> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(DtoConverter::toEnderecoDTO);
    }

    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}
