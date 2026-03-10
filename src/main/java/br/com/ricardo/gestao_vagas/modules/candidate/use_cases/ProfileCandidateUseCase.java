package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.mapper.CandidateMapper;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class ProfileCandidateUseCase {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper profileCandidateMapper;

    public ProfileCandidateResponseDTO execute(@NonNull UUID id) throws UserNotFoundException {
        var candidate = candidateRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return profileCandidateMapper.toProfileCandidateResponseDTO(candidate);
    }
}
