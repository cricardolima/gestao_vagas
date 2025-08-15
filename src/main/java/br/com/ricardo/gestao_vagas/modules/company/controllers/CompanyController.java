package br.com.ricardo.gestao_vagas.modules.company.controllers;

import java.util.Collections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ricardo.gestao_vagas.modules.company.use_cases.CreateCompanyUseCase;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> createCompany(@Valid @RequestBody CompanyEntity company) {
        try {
            return ResponseEntity.ok().body(createCompanyUseCase.execute(company));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
