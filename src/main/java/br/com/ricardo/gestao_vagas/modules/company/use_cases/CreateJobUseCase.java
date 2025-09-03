package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.servlet.http.HttpServletRequest;
import br.com.ricardo.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.ricardo.gestao_vagas.modules.company.mapper.JobMapper;
import br.com.ricardo.gestao_vagas.modules.company.repository.CompanyRepository;
import br.com.ricardo.gestao_vagas.modules.company.repository.JobRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateJobUseCase {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;

    public JobEntity execute(CreateJobDTO createJobDTO, HttpServletRequest request) {
        var jobEntity = jobMapper.toJobEntity(createJobDTO);

        var companyId = UUID.fromString(request.getAttribute("company_id").toString());

        var company = this.companyRepository.findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);

        jobEntity.setCompany(company);

        return this.jobRepository.save(jobEntity);
    }
}
