package br.com.ricardo.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ricardo.gestao_vagas.modules.company.use_cases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/job")
@Tag(name = "Vagas", description = "Informações de vagas")
public class JobController {
    private CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping()
    @Operation(summary = "Cadastro de vagas", description = "Essa rota é responsável por cadastrar uma nova vaga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar a vaga")
    })
    public ResponseEntity<Object> createJob(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(createJobUseCase.execute(createJobDTO, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
