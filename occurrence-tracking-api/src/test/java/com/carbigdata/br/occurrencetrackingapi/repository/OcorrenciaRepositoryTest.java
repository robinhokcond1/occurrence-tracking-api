package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class OcorrenciaRepositoryTest {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private ClienteEntity cliente;
    private EnderecoEntity endereco;
    private OcorrenciaEntity ocorrencia;

    @BeforeEach
    void setup() {
        // Limpa os dados antigos do banco antes de inserir novos registros
        ocorrenciaRepository.deleteAll();
        clienteRepository.deleteAll();
        enderecoRepository.deleteAll();

        // Criando Cliente
        cliente = new ClienteEntity();
        cliente.setNome("João da Silva");
        cliente.setCpf("12345678900");
        cliente = clienteRepository.save(cliente);

        // Criando Endereço
        endereco = new EnderecoEntity();
        endereco.setCidade("São Paulo");
        endereco.setBairro("Centro");
        endereco.setEstado("SP");
        endereco.setCep("01001000");
        endereco = enderecoRepository.save(endereco);

        // Criando Ocorrência
        ocorrencia = new OcorrenciaEntity();
        ocorrencia.setCliente(cliente);
        ocorrencia.setEndereco(endereco);
        ocorrencia.setDataOcorrencia(LocalDateTime.now());
        ocorrencia.setStatusOcorrencia(StatusOcorrenciaEnum.ATIVO);
        ocorrencia = ocorrenciaRepository.save(ocorrencia);
    }

    @Test
    void deveEncontrarOcorrenciaPorStatus() {
        List<OcorrenciaEntity> ocorrencias = ocorrenciaRepository
                .findByStatusOcorrencia(StatusOcorrenciaEnum.ATIVO, PageRequest.of(0, 10))
                .getContent();

        assertThat(ocorrencias).isNotEmpty();
        assertThat(ocorrencias.get(0).getStatusOcorrencia()).isEqualTo(StatusOcorrenciaEnum.ATIVO);
    }

    @Test
    void deveFiltrarOcorrenciasPorCpfENome() {
        List<OcorrenciaEntity> ocorrencias = ocorrenciaRepository.findByFilters(
                "12345678900", "João", null, null, PageRequest.of(0, 10)).getContent();

        assertThat(ocorrencias).isNotEmpty();
        assertThat(ocorrencias.get(0).getCliente().getCpf()).isEqualTo("12345678900");
    }

    @Test
    void deveFiltrarOcorrenciasPorCidade() {
        List<OcorrenciaEntity> ocorrencias = ocorrenciaRepository.findByFilters(
                null, null, null, "São Paulo", PageRequest.of(0, 10)).getContent();

        assertThat(ocorrencias).isNotEmpty();
        assertThat(ocorrencias.get(0).getEndereco().getCidade()).isEqualTo("São Paulo");
    }

}
