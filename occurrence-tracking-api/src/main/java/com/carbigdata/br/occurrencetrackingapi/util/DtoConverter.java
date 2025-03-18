package com.carbigdata.br.occurrencetrackingapi.util;

import com.carbigdata.br.occurrencetrackingapi.dto.*;
import com.carbigdata.br.occurrencetrackingapi.entity.*;

public class DtoConverter {

    public static ClienteDTO toClienteDTO(ClienteEntity cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setDataNascimento(cliente.getDataNascimento());
        return dto;
    }

    public static EnderecoDTO toEnderecoDTO(EnderecoEntity endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setLogradouro(endereco.getLogradouro());
        dto.setBairro(endereco.getBairro());
        dto.setCep(endereco.getCep());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        return dto;
    }

    public static OcorrenciaDTO toOcorrenciaDTO(OcorrenciaEntity ocorrencia) {
        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(ocorrencia.getId());
        dto.setCliente(toClienteDTO(ocorrencia.getCliente()));
        dto.setEndereco(toEnderecoDTO(ocorrencia.getEndereco()));
        dto.setDataOcorrencia(ocorrencia.getDataOcorrencia());
        dto.setStatusOcorrencia(ocorrencia.getStatusOcorrencia());
        return dto;
    }

    public static FotoOcorrenciaDTO toFotoOcorrenciaDTO(FotoOcorrenciaEntity foto) {
        FotoOcorrenciaDTO dto = new FotoOcorrenciaDTO();
        dto.setId(foto.getId());
        dto.setPathBucket(foto.getDscPathBucket());
        dto.setHash(foto.getDscHash());
        return dto;
    }
}
