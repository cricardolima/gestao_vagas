package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ricardo.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.ricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateDTO) {
        var candidate = candidateRepository.findByUsername(authCandidateDTO.username()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        var passwordMatches = passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        var roles = List.of("CANDIDATE");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofMinutes(15));

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("role", roles)
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
        IO.println(token);

        return AuthCandidateResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresAt.toEpochMilli())
                .roles(roles)
                .build();
    }
}
