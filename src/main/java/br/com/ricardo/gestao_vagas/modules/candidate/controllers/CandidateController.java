package br.com.ricardo.gestao_vagas.modules.candidate.controllers;

import java.util.Collections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import br.com.ricardo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateEntity candidate) {
        try {
            return ResponseEntity.ok().body(createCandidateUseCase.execute(candidate));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
