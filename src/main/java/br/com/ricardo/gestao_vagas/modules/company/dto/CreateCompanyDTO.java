package br.com.ricardo.gestao_vagas.modules.company.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCompanyDTO {
    @NotBlank(message = "O campo [username] não pode ser vazio")
    @Pattern(regexp = "\\S+", message = "O campo [username] não pode conter espaços em branco")
    private String username;

    @Length(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    @NotBlank(message = "O campo [password] não pode ser vazio")
    private String password;

    @Email(message = "O [email] informado não é válido")
    @NotBlank(message = "O campo [email] não pode ser vazio")
    private String email;

    private String description;
    private String website;
    private String name;
}