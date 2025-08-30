package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.UserFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CandidateEntity execute(CreateCandidateDTO candidate) {
        candidateRepository.findByUsernameOrEmail(candidate.getUsername(),
                candidate.getEmail()).ifPresent(user -> {
                    throw new UserFoundException("Candidate already exists");
                });
        var candidateEntity = new CandidateEntity();
        BeanUtils.copyProperties(candidate, candidateEntity);

        var password = passwordEncoder.encode(candidate.getPassword());
        candidateEntity.setPassword(password);

        return candidateRepository.save(candidateEntity);
    }
}
