package com.carbigdata.br.occurrencetrackingapi.services;

import com.carbigdata.br.occurrencetrackingapi.entities.Endereco;
import com.carbigdata.br.occurrencetrackingapi.repositories.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {


    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}
