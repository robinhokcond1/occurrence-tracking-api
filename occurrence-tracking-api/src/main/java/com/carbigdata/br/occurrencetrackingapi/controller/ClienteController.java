package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.ClienteDTO;
import com.carbigdata.br.occurrencetrackingapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO novoCliente = clienteService.criarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id) {
        Optional<ClienteDTO> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
