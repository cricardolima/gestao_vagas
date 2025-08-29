package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.servlet.http.HttpServletRequest;
import br.com.ricardo.gestao_vagas.modules.company.JobRepository;
import br.com.ricardo.gestao_vagas.modules.company.CompanyRepository;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;

@Service
public class CreateJobUseCase {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public CreateJobUseCase(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public JobEntity execute(CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            var jobEntity = new JobEntity();
            BeanUtils.copyProperties(createJobDTO, jobEntity);

            var companyId = UUID.fromString(request.getAttribute("company_id").toString());

            // Fetch the company entity to establish the JPA relationship
            var company = this.companyRepository.findById(companyId)
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            jobEntity.setCompany(company);

            return this.jobRepository.save(jobEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar vaga", e);
        }
    }
}
