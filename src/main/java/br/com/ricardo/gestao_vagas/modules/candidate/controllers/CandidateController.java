package br.com.ricardo.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.ProfileCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Operações relacionadas aos candidatos")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase,
            ProfileCandidateUseCase profileCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
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

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Perfil do candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil do candidato encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Object> profileCandidate(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");
        try {
            return ResponseEntity.ok().body(profileCandidateUseCase.execute(UUID.fromString(candidateId.toString())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}