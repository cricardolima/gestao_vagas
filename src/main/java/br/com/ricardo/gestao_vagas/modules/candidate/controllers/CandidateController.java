package br.com.ricardo.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Operações relacionadas aos candidatos")
public class CandidateController {

    private CreateCandidateUseCase createCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar candidato", description = "Cria um novo candidato no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CreateCandidateDTO candidateData) {
        try {
            return ResponseEntity.ok().body(createCandidateUseCase.execute(candidateData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}