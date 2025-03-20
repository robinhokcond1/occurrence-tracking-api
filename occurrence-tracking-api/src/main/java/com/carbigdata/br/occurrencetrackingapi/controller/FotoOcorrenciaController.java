package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.service.FotoOcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/fotos")
@Tag(name = "Fotos de Ocorrências", description = "Gerenciamento de imagens associadas a ocorrências")
public class FotoOcorrenciaController {

    @Autowired
    private FotoOcorrenciaService fotoOcorrenciaService;

    @Operation(summary = "Lista todas as fotos de uma ocorrência")
    @GetMapping("/ocorrencia/{ocorrenciaId}")
    public ResponseEntity<Page<FotoOcorrenciaDTO>> listarFotosPorOcorrencia(
            @PathVariable Long ocorrenciaId,
            Pageable pageable) {
        Page<FotoOcorrenciaDTO> fotos = fotoOcorrenciaService.listarFotosPorOcorrencia(ocorrenciaId, pageable);
        return ResponseEntity.ok(fotos);
    }

    @Operation(summary = "Busca uma foto pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<FotoOcorrenciaDTO> buscarFoto(@PathVariable Long id) {
        Optional<FotoOcorrenciaDTO> foto = fotoOcorrenciaService.buscarFotoPorId(id);
        return foto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui uma foto pelo ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
        fotoOcorrenciaService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Faz upload de uma imagem para uma ocorrência",
            description = "Envia um arquivo de imagem e associa à ocorrência especificada pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem enviada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(value = "/upload/{ocorrenciaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoOcorrenciaDTO> uploadFoto(
            @PathVariable Long ocorrenciaId,
            @RequestParam("file") MultipartFile file) {

        FotoOcorrenciaDTO fotoDTO = fotoOcorrenciaService.salvarFoto(ocorrenciaId, file);
        return ResponseEntity.ok(fotoDTO);
    }

}
