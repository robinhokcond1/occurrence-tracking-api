package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.EnderecoDTO;
import com.carbigdata.br.occurrencetrackingapi.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Gerenciamento de endereços dos clientes")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Cadastra um novo endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO novoEndereco = enderecoService.salvarEndereco(enderecoDTO);
        URI uri = URI.create("/enderecos/" + novoEndereco.getId());
        return ResponseEntity.created(uri).body(novoEndereco);
    }

    @Operation(summary = "Lista todos os endereços com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> listarEnderecos(Pageable pageable) {
        return ResponseEntity.ok(enderecoService.listarEnderecos(pageable));
    }

    @Operation(summary = "Busca um endereço pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(
            @PathVariable @Parameter(description = "ID do endereço") Long id) {
        return enderecoService.buscarEnderecoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui um endereço pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(
            @PathVariable @Parameter(description = "ID do endereço") Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
