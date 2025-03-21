package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaResponseDTO;
import com.carbigdata.br.occurrencetrackingapi.repository.OcorrenciaRepository;
import com.carbigdata.br.occurrencetrackingapi.service.MinioService;
import com.carbigdata.br.occurrencetrackingapi.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/ocorrencias")
@Tag(name = "Ocorrências", description = "Gerenciamento de ocorrências e status")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired  // 🔹 Adicionando injeção do MinioService
    private MinioService minioService;

    @Operation(summary = "Registra uma nova ocorrência")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<OcorrenciaDTO> registrarOcorrencia(@RequestBody OcorrenciaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaService.registrarOcorrencia(dto));
    }

    @Operation(summary = "Finaliza uma ocorrência")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OcorrenciaDTO> finalizarOcorrencia(@PathVariable Long id) {
        return ResponseEntity.ok(ocorrenciaService.finalizarOcorrencia(id));
    }

    @Operation(summary = "Lista todas as ocorrências com detalhes do cliente, endereço e evidências")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<Page<OcorrenciaResponseDTO>> listarOcorrencias(
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(required = false) LocalDate dataOcorrencia,
            @RequestParam(required = false) String cidade,
            Pageable pageable) {

        Page<OcorrenciaResponseDTO> response = ocorrenciaService.listarOcorrencias(cpf, nomeCliente, dataOcorrencia, cidade, pageable);
        return ResponseEntity.ok(response);
    }

}
