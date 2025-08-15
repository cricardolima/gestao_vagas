package br.com.ricardo.gestao_vagas.modules.company;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
}
