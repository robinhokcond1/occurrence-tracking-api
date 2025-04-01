package com.carbigdata.br.occurrencetrackingapi.service;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaResponseDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import com.carbigdata.br.occurrencetrackingapi.exception.NegocioException;
import com.carbigdata.br.occurrencetrackingapi.exception.RecursoNaoEncontradoException;
import com.carbigdata.br.occurrencetrackingapi.repository.ClienteRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.EnderecoRepository;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final MinioService minioService;

    @Transactional
    public OcorrenciaDTO registrarOcorrencia(OcorrenciaCreateDTO dto) {
        ClienteEntity cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com ID: " + dto.getClienteId()));

        EnderecoEntity endereco = enderecoRepository.findById(dto.getEnderecoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado com ID: " + dto.getEnderecoId()));

        OcorrenciaEntity ocorrencia = new OcorrenciaEntity();
        ocorrencia.setCliente(cliente);
        ocorrencia.setEndereco(endereco);
        ocorrencia.setDataOcorrencia(dto.getDataOcorrencia());
        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.ATIVO);

        return DtoConverter.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }

    @Transactional(readOnly = true)
    public Page<OcorrenciaDTO> listarOcorrenciasPorStatus(StatusOcorrenciaEnum status, Pageable pageable) {
        return ocorrenciaRepository.findByStatusOcorrencia(status, pageable)
                .map(DtoConverter::toOcorrenciaDTO);
    }

    @Transactional(readOnly = true)
    public Page<OcorrenciaResponseDTO> listarOcorrencias(String cpf, String nomeCliente, LocalDate dataOcorrencia, String cidade, Pageable pageable) {
        return ocorrenciaRepository.findByFilters(cpf, nomeCliente, dataOcorrencia, cidade, pageable)
                .map(ocorrencia -> {
                    OcorrenciaResponseDTO dto = new OcorrenciaResponseDTO();
                    dto.setId(ocorrencia.getId());
                    dto.setClienteNome(ocorrencia.getCliente().getNome());
                    dto.setCpf(ocorrencia.getCliente().getCpf());
                    dto.setEndereco(ocorrencia.getEndereco().getLogradouro() + ", " + ocorrencia.getEndereco().getCidade());
                    dto.setCidade(ocorrencia.getEndereco().getCidade());
                    dto.setDataOcorrencia(ocorrencia.getDataOcorrencia());

                    dto.setEvidenciasUrl(
                            (ocorrencia.getEvidenciaPath() != null && !ocorrencia.getEvidenciaPath().isEmpty())
                                    ? minioService.getFileUrl(ocorrencia.getEvidenciaPath())
                                    : null
                    );

                    return dto;
                });
    }

    @Transactional(readOnly = true)
    public Optional<OcorrenciaDTO> buscarPorId(Long id) {
        return Optional.ofNullable(ocorrenciaRepository.findById(id)
                .map(DtoConverter::toOcorrenciaDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência não encontrada com ID: " + id)));
    }

    @Transactional
    public OcorrenciaDTO atualizarOcorrencia(Long id, OcorrenciaCreateDTO dto) {
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência não encontrada com ID: " + id));

        if (ocorrencia.getStatusOcorrencia() == StatusOcorrenciaEnum.FINALIZADA) {
            throw new NegocioException("Ocorrência finalizada não pode ser alterada.");
        }

        ocorrencia.setDataOcorrencia(dto.getDataOcorrencia());
        return DtoConverter.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }

    @Transactional
    public OcorrenciaDTO finalizarOcorrencia(Long id) {
        OcorrenciaEntity ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência não encontrada com ID: " + id));

        if (ocorrencia.getStatusOcorrencia() == StatusOcorrenciaEnum.FINALIZADA) {
            throw new NegocioException("Ocorrência já foi finalizada e não pode ser alterada.");
        }

        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.FINALIZADA);
        return DtoConverter.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }
}