package br.com.ricardo.gestao_vagas.modules.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ricardo.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CompanyEntity toCompanyEntity(CreateCompanyDTO createCompanyDTO);

    @Mapping(target = "password", ignore = true)
    CreateCompanyDTO toCreateCompanyDTO(CompanyEntity companyEntity);
}
