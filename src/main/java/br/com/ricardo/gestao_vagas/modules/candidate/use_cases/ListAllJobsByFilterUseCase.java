package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.util.List;

import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ricardo.gestao_vagas.modules.company.repository.JobRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListAllJobsByFilterUseCase {
    private final JobRepository jobRepository;

    public List<JobEntity> execute(String description) {
        if (description == null) {
            return jobRepository.findAll();
        }
        return jobRepository.findByDescriptionContainingIgnoreCase(description);
    }
}
