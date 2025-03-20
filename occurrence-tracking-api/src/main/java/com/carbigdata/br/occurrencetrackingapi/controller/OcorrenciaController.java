package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import com.carbigdata.br.occurrencetrackingapi.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @PostMapping
    public ResponseEntity<OcorrenciaDTO> registrarOcorrencia(@RequestBody OcorrenciaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaService.registrarOcorrencia(dto));
    }

    @GetMapping
    public ResponseEntity<Page<OcorrenciaDTO>> listarOcorrencias(
            @RequestParam(defaultValue = "ATIVO") StatusOcorrenciaEnum status,
            Pageable pageable) {
        Page<OcorrenciaDTO> ocorrencias = ocorrenciaService.listarOcorrencias(status, pageable);
        return ResponseEntity.ok(ocorrencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> buscarOcorrencia(@PathVariable Long id) {
        Optional<OcorrenciaDTO> ocorrencia = ocorrenciaService.buscarPorId(id);
        return ocorrencia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OcorrenciaDTO> finalizarOcorrencia(@PathVariable Long id) {
        return ResponseEntity.ok(ocorrenciaService.finalizarOcorrencia(id));
    }
}
