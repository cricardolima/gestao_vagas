package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.servlet.http.HttpServletRequest;
import br.com.ricardo.gestao_vagas.modules.company.JobRepository;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;

@Service
public class CreateJobUseCase {

    private final JobRepository jobRepository;

    public CreateJobUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobEntity execute(CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            var jobEntity = new JobEntity();
            BeanUtils.copyProperties(createJobDTO, jobEntity);

            var companyId = request.getAttribute("company_id").toString();
            jobEntity.setCompanyId(UUID.fromString(companyId));

            return this.jobRepository.save(jobEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar vaga", e);
        }
    }
}
