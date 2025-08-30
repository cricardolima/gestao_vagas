package br.com.ricardo.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Gestão de Vagas API")
                                                .version("1.0.0")
                                                .description(
                                                                "API para gestão de vagas de emprego - Sistema completo para candidatos e empresas")
                                                .contact(new Contact()
                                                                .name("Ricardo")
                                                                .email("contato@gestaovagas.com")
                                                                .url("https://github.com/ricardo/gestao-vagas"))
                                                .license(new License()
                                                                .name("MIT License")
                                                                .url("https://opensource.org/licenses/MIT")))
                                .schemaRequirement("jwt_auth", securityScheme());

                // Adiciona a segurança a todas as rotas
                // .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                // .components(new Components().addSecuritySchemes("jwt_auth",
                // securityScheme()));
        }

        private SecurityScheme securityScheme() {
                return new SecurityScheme()
                                .name("jwt_auth")
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER);
        }
}