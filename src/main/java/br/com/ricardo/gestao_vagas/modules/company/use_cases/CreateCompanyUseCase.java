package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ricardo.gestao_vagas.modules.company.CompanyRepository;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;

@Service
public class CreateCompanyUseCase {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    CreateCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CompanyEntity execute(CreateCompanyDTO createCompanyDTO) {
        companyRepository.findByUsernameOrEmail(createCompanyDTO.getUsername(), createCompanyDTO.getEmail())
                .ifPresent(user -> {
                    throw new CompanyFoundException("Company already exists");
                });

        /*
         * var companyEntity = new CompanyEntity();
         * companyEntity.setUsername(createCompanyDTO.getUsername());
         * companyEntity.setEmail(createCompanyDTO.getEmail());
         * companyEntity.setName(createCompanyDTO.getName());
         * companyEntity.setWebsite(createCompanyDTO.getWebsite());
         * companyEntity.setDescription(createCompanyDTO.getDescription());
         * // ... para cada propriedade
         */

        var companyEntity = new CompanyEntity();
        BeanUtils.copyProperties(createCompanyDTO, companyEntity);

        var password = passwordEncoder.encode(createCompanyDTO.getPassword());
        companyEntity.setPassword(password);

        return companyRepository.save(companyEntity);
    }
}
