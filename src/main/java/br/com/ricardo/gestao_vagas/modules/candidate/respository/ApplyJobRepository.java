package br.com.ricardo.gestao_vagas.modules.candidate.respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ricardo.gestao_vagas.modules.candidate.entity.ApplyJobEntity;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

}
