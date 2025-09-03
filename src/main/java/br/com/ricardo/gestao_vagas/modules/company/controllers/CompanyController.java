package br.com.ricardo.gestao_vagas.modules.company.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ricardo.gestao_vagas.modules.company.use_cases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/company")
@Tag(name = "Company", description = "Operações relacionadas às empresas")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;

    @PostMapping
    @Operation(summary = "Criar empresa", description = "Cria uma nova empresa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso", content = @Content(schema = @Schema(implementation = CompanyEntity.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    public CompanyEntity createCompany(@Valid @RequestBody CreateCompanyDTO companyData)
            throws CompanyFoundException {
        return createCompanyUseCase.execute(companyData);
    }
}