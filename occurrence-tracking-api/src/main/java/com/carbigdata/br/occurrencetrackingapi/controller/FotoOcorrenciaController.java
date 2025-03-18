package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.FotoOcorrenciaDTO;
import com.carbigdata.br.occurrencetrackingapi.service.FotoOcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fotos")
public class FotoOcorrenciaController {

    @Autowired
    private FotoOcorrenciaService fotoOcorrenciaService;

    @GetMapping("/ocorrencia/{ocorrenciaId}")
    public ResponseEntity<List<FotoOcorrenciaDTO>> listarFotosPorOcorrencia(@PathVariable Long ocorrenciaId) {
        return ResponseEntity.ok(fotoOcorrenciaService.listarFotosPorOcorrencia(ocorrenciaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoOcorrenciaDTO> buscarFoto(@PathVariable Long id) {
        Optional<FotoOcorrenciaDTO> foto = fotoOcorrenciaService.buscarFotoPorId(id);
        return foto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
        fotoOcorrenciaService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }
}
