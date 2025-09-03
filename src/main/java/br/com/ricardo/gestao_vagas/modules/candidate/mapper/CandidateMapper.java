package br.com.ricardo.gestao_vagas.modules.candidate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ricardo.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.ricardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    /**
     * Converte CandidateEntity para ProfileCandidateResponseDTO
     */
    ProfileCandidateResponseDTO toProfileCandidateResponseDTO(CandidateEntity candidateEntity);

    /**
     * Converte CreateCandidateDTO para CandidateEntity
     * Ignora campos que são gerenciados pelo sistema
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "curriculum", ignore = true)
    CandidateEntity toCandidateEntity(CreateCandidateDTO createCandidateDTO);

    /**
     * Converte CandidateEntity para CreateCandidateDTO
     * Útil para operações de atualização
     */
    @Mapping(target = "password", ignore = true)
    CreateCandidateDTO toCreateCandidateDTO(CandidateEntity candidateEntity);

    /**
     * Converte lista de CandidateEntity para lista de ProfileCandidateResponseDTO
     */
    List<ProfileCandidateResponseDTO> toProfileCandidateResponseDTOList(List<CandidateEntity> candidateEntities);

    /**
     * Converte lista de CreateCandidateDTO para lista de CandidateEntity
     */
    List<CandidateEntity> toCandidateEntityList(List<CreateCandidateDTO> createCandidateDTOs);
}
