package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.util.List;

import br.com.ricardo.gestao_vagas.modules.company.JobRepository;
import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;

import org.springframework.stereotype.Service;

@Service
public class ListAllJobsByFilterUseCase {
    private final JobRepository jobRepository;

    public ListAllJobsByFilterUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobEntity> execute(String description) {
        return jobRepository.findByDescriptionContainingIgnoreCase(description);
    }
}
