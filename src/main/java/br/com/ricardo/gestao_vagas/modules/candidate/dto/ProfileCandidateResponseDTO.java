package br.com.ricardo.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    private String description;
    private String name;
    private String username;
    private String email;
    private UUID id;
}
