package br.com.ricardo.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.exceptions.UserFoundException;
import br.com.ricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.ApplyJobUseCase;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.ListAllJobsByFilterUseCase;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.ProfileCandidateUseCase;
import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Operações relacionadas aos candidatos")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;
    private final ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;
    private final ApplyJobUseCase applyJobUseCase;

    @PostMapping
    @Operation(summary = "Criar candidato", description = "Cria um novo candidato no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato criado com sucesso", content = @Content(schema = @Schema(implementation = CandidateEntity.class))),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    public CandidateEntity createCandidate(@Valid @RequestBody CreateCandidateDTO candidateData)
            throws UserFoundException {
        return createCandidateUseCase.execute(candidateData);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Perfil do candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil do candidato encontrado com sucesso", content = @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @SecurityRequirement(name = "jwt_auth")
    public ProfileCandidateResponseDTO profileCandidate(HttpServletRequest request) throws UserNotFoundException {
        var candidateId = request.getAttribute("candidate_id");
        return profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listar vagas", description = "Listar vagas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vagas encontradas com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))),
            @ApiResponse(responseCode = "400", description = "Job not found", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> listAllJobsByFilter(@RequestParam(required = false) String description) {
        return listAllJobsByFilterUseCase.execute(description);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Aplicar para uma vaga", description = "Aplicar para uma vaga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga aplicada com sucesso", content = @Content(schema = @Schema(implementation = ApplyJobEntity.class))),
            @ApiResponse(responseCode = "400", description = "Job not found", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @SecurityRequirement(name = "jwt_auth")
    public ApplyJobEntity applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
        var candidateId = request.getAttribute("candidate_id");
        return applyJobUseCase.execute(UUID.fromString(candidateId.toString()), jobId);
    }
}