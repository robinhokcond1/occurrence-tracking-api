package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
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
import java.util.Optional;

@RestController
@RequestMapping("/ocorrencias")
@Tag(name = "Ocorrências", description = "Gerenciamento de ocorrências e status")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

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
}
