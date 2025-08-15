package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ricardo.gestao_vagas.modules.company.JobRepository;

@Service
public class CreateJobUseCase {

    private final JobRepository jobRepository;

    public CreateJobUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobEntity execute(JobEntity job) {
        return jobRepository.save(job);
    }
}
