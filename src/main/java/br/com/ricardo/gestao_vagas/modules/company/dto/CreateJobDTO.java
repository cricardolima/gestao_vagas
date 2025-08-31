package br.com.ricardo.gestao_vagas.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
    @NotBlank(message = "O campo [description] não pode ser vazio")
    @Schema(description = "Descrição da vaga", example = "Vaga para pessoa desenvolvedora junior - Java", requiredMode = RequiredMode.REQUIRED)
    private String description;
    @NotBlank(message = "O campo [benefits] não pode ser vazio")
    @Schema(description = "Benefícios da vaga", example = "GYMPass, Plano Odontológico, Plano de Saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
    @NotBlank(message = "O campo [level] não pode ser vazio")
    @Schema(description = "Nível da vaga", example = "Junior", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
