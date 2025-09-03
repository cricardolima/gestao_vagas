package br.com.ricardo.gestao_vagas.modules.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "company", ignore = true)
    JobEntity toJobEntity(CreateJobDTO createJobDTO);

    CreateJobDTO toCreateJobDTO(JobEntity jobEntity);
}
