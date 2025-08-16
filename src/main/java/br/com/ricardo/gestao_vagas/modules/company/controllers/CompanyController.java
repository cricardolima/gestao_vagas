package br.com.ricardo.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.ricardo.gestao_vagas.modules.company.use_cases.CreateCompanyUseCase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Operações relacionadas às empresas")
public class CompanyController {

    private CreateCompanyUseCase createCompanyUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar empresa", description = "Cria uma nova empresa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Object> createCompany(@Valid @RequestBody CreateCompanyDTO companyData) {
        try {
            return ResponseEntity.ok().body(createCompanyUseCase.execute(companyData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}