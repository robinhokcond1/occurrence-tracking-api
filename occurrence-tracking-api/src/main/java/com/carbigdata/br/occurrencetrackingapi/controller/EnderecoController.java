package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Gerenciamento de endereços dos clientes")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Cadastra um novo endereço")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.salvarEndereco(enderecoDTO));
    }

    @Operation(summary = "Lista todos os endereços com paginação")
    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> listarEnderecos(Pageable pageable) {
        Page<EnderecoDTO> enderecos = enderecoService.listarEnderecos(pageable);
        return ResponseEntity.ok(enderecos);
    }

    @Operation(summary = "Busca um endereço pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable Long id) {
        Optional<EnderecoDTO> endereco = enderecoService.buscarEnderecoPorId(id);
        return endereco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui um endereço pelo ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
