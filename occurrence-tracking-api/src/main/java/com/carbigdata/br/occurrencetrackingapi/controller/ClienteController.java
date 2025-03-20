package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.ClienteDTO;
import com.carbigdata.br.occurrencetrackingapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Lista todos os clientes com paginação")
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(Pageable pageable) {
        return ResponseEntity.ok(clienteService.listarTodos(pageable));
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastra um novo cliente")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.criarCliente(clienteDTO));
    }

    @Operation(summary = "Exclui um cliente pelo ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
