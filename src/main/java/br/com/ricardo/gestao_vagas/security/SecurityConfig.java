package br.com.ricardo.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] PERMIT_ALL_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/actuator/**",
            "/candidate",
            "/company"
    };

    /*
     * This method is used to configure the security filter chain.
     * It disables CSRF protection and returns the built HttpSecurity object.
     *
     * @param http The HttpSecurity object to be configured.
     * 
     * @return The configured SecurityFilterChain object.
     * 
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_ALL_LIST).permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
