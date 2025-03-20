package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.salvarEndereco(enderecoDTO));
    }

    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> listarEnderecos(Pageable pageable) {
        Page<EnderecoDTO> enderecos = enderecoService.listarEnderecos(pageable);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable Long id) {
        Optional<EnderecoDTO> endereco = enderecoService.buscarEnderecoPorId(id);
        return endereco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
