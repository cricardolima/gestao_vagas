package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.ricardo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.ricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.ApplyJobRepository;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;
import br.com.ricardo.gestao_vagas.modules.company.repository.JobRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class ApplyJobUseCase {
        private final CandidateRepository candidateRepository;
        private final JobRepository jobRepository;
        private final ApplyJobRepository applyJobRepository;

        @SuppressWarnings("null")
        public ApplyJobEntity execute(@NonNull UUID candidateId, @NonNull UUID jobId) {
                var candidate = candidateRepository.findById(candidateId)
                                .orElseThrow(UserNotFoundException::new);
                var job = jobRepository.findById(jobId)
                                .orElseThrow(JobNotFoundException::new);

                var applyJob = ApplyJobEntity.builder()
                                .candidateId(candidate.getId())
                                .jobId(job.getId())
                                .build();

                return applyJobRepository.save(applyJob);
        }
}
