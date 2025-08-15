package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ricardo.gestao_vagas.modules.company.CompanyRepository;
import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;

@Service
public class CreateCompanyUseCase {
    private final CompanyRepository companyRepository;

    CreateCompanyUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyEntity execute(CompanyEntity company) {
        companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail()).ifPresent(user -> {
            throw new CompanyFoundException("Company already exists");
        });

        return companyRepository.save(company);
    }
}
