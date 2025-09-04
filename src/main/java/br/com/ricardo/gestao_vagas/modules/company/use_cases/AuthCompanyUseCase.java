package br.com.ricardo.gestao_vagas.modules.company.use_cases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ricardo.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ricardo.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.ricardo.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ricardo.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.ricardo.gestao_vagas.modules.company.repository.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new CompanyFoundException();
        });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofMinutes(15));

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withClaim("role", Arrays.asList("COMPANY"))
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);

        return AuthCompanyResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresAt.toEpochMilli())
                .build();
    }
}
