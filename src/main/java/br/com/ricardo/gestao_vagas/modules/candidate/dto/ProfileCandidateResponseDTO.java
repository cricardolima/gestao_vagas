package br.com.ricardo.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(description = "Descrição do candidato", example = "Sou um desenvolvedor Java")
    private String description;
    @Schema(description = "Nome do candidato", example = "João da Silva")
    private String name;
    @Schema(description = "Username do candidato", example = "joao.silva")
    private String username;
    @Schema(description = "Email do candidato", example = "joao.silva@gmail.com")
    private String email;
    @Schema(description = "ID do candidato", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
}
