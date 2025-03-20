package com.carbigdata.br.occurrencetrackingapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Occurrence Tracking API")
                        .version("1.0")
                        .description("API para rastreamento de ocorrências, incluindo cadastro de clientes, endereços e upload de imagens para MinIO."))
                .servers(List.of(new Server().url("http://localhost:8080").description("Servidor Local")));
    }
}
