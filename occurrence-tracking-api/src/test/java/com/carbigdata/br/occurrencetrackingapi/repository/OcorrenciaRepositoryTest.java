package com.carbigdata.br.occurrencetrackingapi.repository;

import com.carbigdata.br.occurrencetrackingapi.entity.ClienteEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.EnderecoEntity;
import com.carbigdata.br.occurrencetrackingapi.entity.OcorrenciaEntity;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers  // 🔹 Garante que o Testcontainers está ativo
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

    // 🔹 Configurando o PostgreSQL Container para os testes
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16.1")
                    .withDatabaseName("occurrence-tracking-test")
                    .withUsername("admin")
                    .withPassword("admin");

    // 🔹 Define as propriedades dinâmicas para o Spring usar o Testcontainers
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Transactional
    @BeforeEach
    void setup() {
        // Limpa os dados antigos do banco antes de inserir novos registros
        ocorrenciaRepository.deleteAll();
        enderecoRepository.deleteAll();
        clienteRepository.deleteAll();

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
