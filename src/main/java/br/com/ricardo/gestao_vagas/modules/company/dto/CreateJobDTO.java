package br.com.ricardo.gestao_vagas.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
    @NotBlank(message = "O campo [description] não pode ser vazio")
    private String description;
    @NotBlank(message = "O campo [benefits] não pode ser vazio")
    private String benefits;
    @NotBlank(message = "O campo [level] não pode ser vazio")
    private String level;
}
