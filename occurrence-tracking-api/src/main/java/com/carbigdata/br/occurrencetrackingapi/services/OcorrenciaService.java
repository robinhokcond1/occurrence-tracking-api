package com.carbigdata.br.occurrencetrackingapi.services;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entities.Cliente;
import com.carbigdata.br.occurrencetrackingapi.entities.Endereco;
import com.carbigdata.br.occurrencetrackingapi.entities.Ocorrencia;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrencia;
import com.carbigdata.br.occurrencetrackingapi.repositories.ClienteRepository;
import com.carbigdata.br.occurrencetrackingapi.repositories.EnderecoRepository;
import com.carbigdata.br.occurrencetrackingapi.repositories.OcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public OcorrenciaDTO registrarOcorrencia(OcorrenciaCreateDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setCliente(cliente);
        ocorrencia.setEndereco(endereco);
        ocorrencia.setDataOcorrencia(dto.getDataOcorrencia());
        ocorrencia.setStatusOcorrencia(StatusOcorrencia.ATIVO);

        Ocorrencia ocorrenciaSalva = ocorrenciaRepository.save(ocorrencia);
        return DtoConverter.toOcorrenciaDTO(ocorrenciaSalva);
    }

    public List<OcorrenciaDTO> listarOcorrencias() {
        return ocorrenciaRepository.findAll()
                .stream()
                .map(DtoConverter::toOcorrenciaDTO)
                .collect(Collectors.toList());
    }

    public Optional<OcorrenciaDTO> buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id)
                .map(DtoConverter::toOcorrenciaDTO);
    }

    public OcorrenciaDTO finalizarOcorrencia(Long id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrencia.setStatusOcorrencia(StatusOcorrencia.FINALIZADA);
        Ocorrencia ocorrenciaAtualizada = ocorrenciaRepository.save(ocorrencia);
        return DtoConverter.toOcorrenciaDTO(ocorrenciaAtualizada);
    }
}
