package br.com.ricardo.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID companyId, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofMinutes(15));

        return JWT.create()
                .withIssuer("javagas")
                .withSubject(companyId.toString())
                .withClaim("role", Arrays.asList("COMPANY"))
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);

    }
}
