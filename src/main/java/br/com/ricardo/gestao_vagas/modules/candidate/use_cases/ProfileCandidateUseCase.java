package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.UserFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;

@Service
public class ProfileCandidateUseCase {
    private final CandidateRepository candidateRepository;

    public ProfileCandidateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public ProfileCandidateResponseDTO execute(UUID id) {
        var candidate = candidateRepository.findById(id).orElseThrow(() -> {
            throw new UserFoundException("User not found");
        });

        var profileCandidateResponseDTO = ProfileCandidateResponseDTO.builder().build();
        BeanUtils.copyProperties(candidate, profileCandidateResponseDTO);

        return profileCandidateResponseDTO;
    }
}
