package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import com.carbigdata.br.occurrencetrackingapi.repository.ClienteRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
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
        ClienteEntity cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        EnderecoEntity endereco = enderecoRepository.findById(dto.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        OcorrenciaEntity ocorrencia = new OcorrenciaEntity();
        ocorrencia.setCliente(cliente);
        ocorrencia.setEndereco(endereco);
        ocorrencia.setDataOcorrencia(dto.getDataOcorrencia());
        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.ATIVO);

        OcorrenciaEntity ocorrenciaSalva = ocorrenciaRepository.save(ocorrencia);
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
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.FINALIZADA);
        OcorrenciaEntity ocorrenciaAtualizada = ocorrenciaRepository.save(ocorrencia);
        return DtoConverter.toOcorrenciaDTO(ocorrenciaAtualizada);
    }
}
