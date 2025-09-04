package br.com.ricardo.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ricardo.gestao_vagas.modules.company.use_cases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/job")
@Tag(name = "Vagas", description = "Informações de vagas")
public class JobController {
    private final CreateJobUseCase createJobUseCase;

    @PostMapping()
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary = "Cadastro de vagas", description = "Essa rota é responsável por cadastrar uma nova vaga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga cadastrada com sucesso", content = @Content(schema = @Schema(implementation = JobEntity.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar a vaga", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @SecurityRequirement(name = "jwt_auth")
    public JobEntity createJob(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        return createJobUseCase.execute(createJobDTO, request);
    }

}
