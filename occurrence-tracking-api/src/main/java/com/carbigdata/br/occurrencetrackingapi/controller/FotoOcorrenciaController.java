package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.exception.RecursoNaoEncontradoException;
import com.carbigdata.br.occurrencetrackingapi.service.FotoOcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fotos")
@Tag(name = "Fotos de Ocorrências", description = "Gerenciamento de imagens associadas a ocorrências")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class FotoOcorrenciaController {

    private final FotoOcorrenciaService fotoOcorrenciaService;

    @Operation(summary = "Lista todas as fotos de uma ocorrência")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fotos encontradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma foto encontrada para a ocorrência")
    })
    @GetMapping("/ocorrencia/{ocorrenciaId}")
    public ResponseEntity<Page<FotoOcorrenciaDTO>> listarFotosPorOcorrencia(
            @PathVariable @Parameter(description = "ID da ocorrência") Long ocorrenciaId,
            Pageable pageable) {
        Page<FotoOcorrenciaDTO> fotos = fotoOcorrenciaService.listarFotosPorOcorrencia(ocorrenciaId, pageable);
        return ResponseEntity.ok(fotos);
    }

    @Operation(summary = "Busca uma foto pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto encontrada"),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FotoOcorrenciaDTO> buscarFoto(@PathVariable @Parameter(description = "ID da foto") Long id) {
        return fotoOcorrenciaService.buscarFotoPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Foto não encontrada com o ID: " + id));
    }

    @Operation(summary = "Exclui uma foto pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Foto excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFoto(@PathVariable @Parameter(description = "ID da foto") Long id) {
        fotoOcorrenciaService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Faz upload de uma imagem para uma ocorrência",
            description = "Envia um arquivo de imagem e associa à ocorrência especificada pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Upload realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no upload do arquivo"),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada")
    })
    @PostMapping(value = "/upload/{ocorrenciaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadEvidencia(
            @PathVariable @Parameter(description = "ID da ocorrência") Long ocorrenciaId,
            @RequestParam("file") MultipartFile file) {

        FotoOcorrenciaDTO fotoSalva = fotoOcorrenciaService.salvarFoto(ocorrenciaId, file);
        return ResponseEntity.ok("Arquivo salvo com sucesso! ID da foto: " + fotoSalva.getId());
    }
}
