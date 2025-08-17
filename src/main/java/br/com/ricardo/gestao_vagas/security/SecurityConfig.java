package br.com.ricardo.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    protected static final String[] PERMIT_ALL_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/actuator/**",
            "/candidate",
            "/company",
            "/company/auth",
            "/candidate/auth"
    };

    private SecurityCompanyFilter securityCompanyFilter;
    private SecurityCandidateFilter securityCandidateFilter;

    SecurityConfig(SecurityCompanyFilter securityCompanyFilter, SecurityCandidateFilter securityCandidateFilter) {
        this.securityCompanyFilter = securityCompanyFilter;
        this.securityCandidateFilter = securityCandidateFilter;
    }

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
                        .anyRequest().authenticated())
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
