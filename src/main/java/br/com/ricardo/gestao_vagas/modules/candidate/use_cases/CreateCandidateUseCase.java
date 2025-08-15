package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.ricardo.gestao_vagas.exceptions.UserFoundException;

@Service
public class CreateCandidateUseCase {
    private final CandidateRepository candidateRepository;

    CreateCandidateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public CandidateEntity execute(CandidateEntity candidate) {
        candidateRepository.findByUsernameOrEmail(candidate.getUsername(),
                candidate.getEmail()).ifPresent(user -> {
                    throw new UserFoundException("Candidate already exists");
                });

        return candidateRepository.save(candidate);
    }
}
