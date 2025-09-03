package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.UserFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.mapper.CandidateMapper;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateCandidateUseCase {
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;
    private final CandidateMapper candidateMapper;

    public CandidateEntity execute(CreateCandidateDTO candidate) throws UserFoundException {
        boolean userExists = candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .isPresent();

        if (userExists) {
            throw new UserFoundException();
        }

        CandidateEntity candidateEntity = candidateMapper.toCandidateEntity(candidate);

        String encodedPassword = passwordEncoder.encode(candidate.getPassword());
        candidateEntity.setPassword(encodedPassword);

        return candidateRepository.save(candidateEntity);
    }
}
