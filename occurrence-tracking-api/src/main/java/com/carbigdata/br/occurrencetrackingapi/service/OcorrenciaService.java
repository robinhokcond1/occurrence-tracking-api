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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

        return DtoConverter.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public Page<OcorrenciaDTO> listarOcorrenciasPorStatus(StatusOcorrenciaEnum status, Pageable pageable) {
        return ocorrenciaRepository.findByStatusOcorrencia(status, pageable)
                .map(DtoConverter::toOcorrenciaDTO);
    }

    public Page<OcorrenciaDTO> listarOcorrencias(
            String cpf,
            String nomeCliente,
            LocalDate dataOcorrencia,
            String cidade,
            Pageable pageable) {

        return ocorrenciaRepository.findByFilters(cpf, nomeCliente, dataOcorrencia, cidade, pageable)
                .map(DtoConverter::toOcorrenciaDTO);
    }

    public Optional<OcorrenciaDTO> buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id).map(DtoConverter::toOcorrenciaDTO);
    }

    public OcorrenciaDTO atualizarOcorrencia(Long id, OcorrenciaCreateDTO dto) {
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        if (ocorrencia.getStatusOcorrencia() == StatusOcorrenciaEnum.FINALIZADA) {
            throw new RuntimeException("Ocorrência finalizada não pode ser alterada.");
        }

        ocorrencia.setDataOcorrencia(dto.getDataOcorrencia());
        return DtoConverter.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaDTO finalizarOcorrencia(Long id) {
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        if (ocorrencia.getStatusOcorrencia() == StatusOcorrenciaEnum.FINALIZADA) {
            throw new RuntimeException("Ocorrência já foi finalizada e não pode ser alterada.");
        }

        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.FINALIZADA);
        return DtoConverter.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }
}
