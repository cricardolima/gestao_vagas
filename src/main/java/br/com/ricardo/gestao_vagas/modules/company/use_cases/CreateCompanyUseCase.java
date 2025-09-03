package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ricardo.gestao_vagas.modules.company.mapper.CompanyMapper;
import br.com.ricardo.gestao_vagas.modules.company.repository.CompanyRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateCompanyUseCase {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyMapper companyMapper;

    public CompanyEntity execute(CreateCompanyDTO createCompanyDTO) {
        var companyExists = companyRepository
                .findByUsernameOrEmail(createCompanyDTO.getUsername(), createCompanyDTO.getEmail())
                .isPresent();
        if (companyExists) {
            throw new CompanyFoundException();
        }

        CompanyEntity companyEntity = this.companyMapper.toCompanyEntity(createCompanyDTO);
        String encodedPassword = passwordEncoder.encode(createCompanyDTO.getPassword());
        companyEntity.setPassword(encodedPassword);

        return this.companyRepository.save(companyEntity);
    }
}
