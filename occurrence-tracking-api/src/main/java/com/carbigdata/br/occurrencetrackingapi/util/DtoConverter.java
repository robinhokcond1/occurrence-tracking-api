package com.carbigdata.br.occurrencetrackingapi.util;

import com.carbigdata.br.occurrencetrackingapi.dto.*;
import com.carbigdata.br.occurrencetrackingapi.entities.*;

public class DtoConverter {

    public static ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getCodClienteId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setDataNascimento(cliente.getDataNascimento());
        return dto;
    }

    public static EnderecoDTO toEnderecoDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getCodEndercoId());
        dto.setLogradouro(endereco.getLogradouro());
        dto.setBairro(endereco.getBairro());
        dto.setCep(endereco.getCep());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        return dto;
    }

    public static OcorrenciaDTO toOcorrenciaDTO(Ocorrencia ocorrencia) {
        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(ocorrencia.getCodOcorrenciaId());
        dto.setCliente(toClienteDTO(ocorrencia.getCliente()));
        dto.setEndereco(toEnderecoDTO(ocorrencia.getEndereco()));
        dto.setDataOcorrencia(ocorrencia.getDataOcorrencia());
        dto.setStatusOcorrencia(ocorrencia.getStatusOcorrencia());
        return dto;
    }

    public static FotoOcorrenciaDTO toFotoOcorrenciaDTO(FotoOcorrencia foto) {
        FotoOcorrenciaDTO dto = new FotoOcorrenciaDTO();
        dto.setId(foto.getCodFotOcorrencia());
        dto.setPathBucket(foto.getDscPathBucket());
        dto.setHash(foto.getDscHash());
        return dto;
    }
}
