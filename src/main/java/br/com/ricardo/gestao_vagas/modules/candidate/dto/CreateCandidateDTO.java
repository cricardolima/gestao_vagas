package br.com.ricardo.gestao_vagas.modules.candidate.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCandidateDTO {

    @NotNull(message = "O campo [name] é obrigatório")
    private String name;
    @NotNull(message = "O campo [username] é obrigatório")
    private String username;
    @NotNull(message = "O campo [email] é obrigatório")
    @Email(message = "O campo [email] deve conter um e-mail válido")
    private String email;
    @NotNull(message = "O campo [password] é obrigatório")
    @Length(min = 8, message = "O campo [password] deve conter no mínimo 8 caracteres")
    private String password;
    @NotNull(message = "O campo [description] é obrigatório")
    private String description;
}
