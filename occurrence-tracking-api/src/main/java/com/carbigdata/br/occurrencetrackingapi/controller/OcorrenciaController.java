package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaCreateDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.dto.OcorrenciaResponseDTO;
import com.carbigdata.br.occurrencetrackingapi.enums.StatusOcorrenciaEnum;
import com.carbigdata.br.occurrencetrackingapi.exception.RecursoNaoEncontradoException;
import com.carbigdata.br.occurrencetrackingapi.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    @Operation(summary = "Registra uma nova ocorrência")
    @PostMapping
    public ResponseEntity<OcorrenciaDTO> registrarOcorrencia(@Valid @RequestBody OcorrenciaCreateDTO dto) {
        OcorrenciaDTO ocorrencia = ocorrenciaService.registrarOcorrencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrencia);
    }

    @Operation(summary = "Finaliza uma ocorrência")
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OcorrenciaDTO> finalizarOcorrencia(@PathVariable Long id) {
        OcorrenciaDTO dto = ocorrenciaService.finalizarOcorrencia(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Atualiza os dados de uma ocorrência")
    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> atualizarOcorrencia(@PathVariable Long id,
                                                             @Valid @RequestBody OcorrenciaCreateDTO dto) {
        OcorrenciaDTO atualizada = ocorrenciaService.atualizarOcorrencia(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    @Operation(summary = "Lista todas as ocorrências filtrando por status")
    @GetMapping("/status")
    public ResponseEntity<Page<OcorrenciaDTO>> listarPorStatus(@RequestParam StatusOcorrenciaEnum status,
                                                               Pageable pageable) {
        Page<OcorrenciaDTO> lista = ocorrenciaService.listarOcorrenciasPorStatus(status, pageable);
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Lista todas as ocorrências com filtros opcionais")
    @GetMapping
    public ResponseEntity<Page<OcorrenciaResponseDTO>> listarOcorrencias(@RequestParam(required = false) String cpf,
                                                                         @RequestParam(required = false) String nomeCliente,
                                                                         @RequestParam(required = false) LocalDate dataOcorrencia,
                                                                         @RequestParam(required = false) String cidade,
                                                                         Pageable pageable) {
        Page<OcorrenciaResponseDTO> ocorrencias = ocorrenciaService.listarOcorrencias(cpf, nomeCliente, dataOcorrencia, cidade, pageable);
        return ResponseEntity.ok(ocorrencias);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca uma ocorrência por ID",
            description = "Retorna os detalhes de uma ocorrência específica com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ocorrência encontrada"),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<OcorrenciaDTO> buscarPorId(
            @PathVariable @Parameter(description = "ID da ocorrência") Long id) {

        try {
            Optional<OcorrenciaDTO> ocorrencia = ocorrenciaService.buscarPorId(id);

            return ocorrencia
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência não encontrada com o ID: " + id));

        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
