package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.ClienteDTO;
import com.carbigdata.br.occurrencetrackingapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Lista todos os clientes com paginação")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso")
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(Pageable pageable) {
        return ResponseEntity.ok(clienteService.listarTodos(pageable));
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(
            @PathVariable @Parameter(description = "ID do cliente") Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastra um novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO novoCliente = clienteService.criarCliente(clienteDTO);
        URI uri = URI.create("/clientes/" + novoCliente.getId());
        return ResponseEntity.created(uri).body(novoCliente);
    }

    @Operation(summary = "Exclui um cliente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(
            @PathVariable @Parameter(description = "ID do cliente") Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
