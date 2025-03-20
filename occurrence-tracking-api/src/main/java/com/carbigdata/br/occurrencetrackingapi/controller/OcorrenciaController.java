package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import com.carbigdata.br.occurrencetrackingapi.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Registra uma nova ocorrência", description = "Cria uma ocorrência associada a um cliente e endereço.")
    @PostMapping
    public ResponseEntity<OcorrenciaDTO> registrarOcorrencia(@RequestBody OcorrenciaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaService.registrarOcorrencia(dto));
    }

    @Operation(summary = "Lista todas as ocorrências com filtros", description = "Filtra ocorrências por CPF, nome do cliente, data da ocorrência e cidade.")
    @GetMapping
    public ResponseEntity<Page<OcorrenciaDTO>> listarOcorrencias(
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(required = false) LocalDate dataOcorrencia,
            @RequestParam(required = false) String cidade,
            Pageable pageable) {

        Page<OcorrenciaDTO> ocorrencias = ocorrenciaService.listarOcorrencias(cpf, nomeCliente, dataOcorrencia, cidade, pageable);
        return ResponseEntity.ok(ocorrencias);
    }

    @Operation(summary = "Lista ocorrências por status", description = "Filtra ocorrências pelo status (ATIVO, FINALIZADA).")
    @GetMapping("/status")
    public ResponseEntity<Page<OcorrenciaDTO>> listarOcorrenciasPorStatus(
            @RequestParam(defaultValue = "ATIVO") StatusOcorrenciaEnum status,
            Pageable pageable) {
        Page<OcorrenciaDTO> ocorrencias = ocorrenciaService.listarOcorrenciasPorStatus(status, pageable);
        return ResponseEntity.ok(ocorrencias);
    }

    @Operation(summary = "Busca uma ocorrência pelo ID", description = "Retorna os detalhes de uma ocorrência específica.")
    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> buscarOcorrencia(@PathVariable Long id) {
        Optional<OcorrenciaDTO> ocorrencia = ocorrenciaService.buscarPorId(id);
        return ocorrencia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Finaliza uma ocorrência", description = "Altera o status da ocorrência para FINALIZADA.")
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OcorrenciaDTO> finalizarOcorrencia(@PathVariable Long id) {
        return ResponseEntity.ok(ocorrenciaService.finalizarOcorrencia(id));
    }

    @Operation(summary = "Atualiza uma ocorrência", description = "Modifica os dados de uma ocorrência. Não pode modificar se já estiver finalizada.")
    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> atualizarOcorrencia(@PathVariable Long id, @RequestBody OcorrenciaCreateDTO dto) {
        OcorrenciaDTO ocorrenciaAtualizada = ocorrenciaService.atualizarOcorrencia(id, dto);
        return ResponseEntity.ok(ocorrenciaAtualizada);
    }
}

