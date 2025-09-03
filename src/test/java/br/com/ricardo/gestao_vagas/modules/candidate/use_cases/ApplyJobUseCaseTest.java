package br.com.ricardo.gestao_vagas.modules.candidate.use_cases;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ricardo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.ricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.ApplyJobRepository;
import br.com.ricardo.gestao_vagas.modules.candidate.respository.CandidateRepository;
import br.com.ricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ricardo.gestao_vagas.modules.company.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
class ApplyJobUseCaseTest {

    @InjectMocks
    private ApplyJobUseCase applyJobUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply for a job with candidate not found")
    void itShouldNotBeAbleToApplyForAJobWithCandidateNotFound() {
        try {
            applyJobUseCase.execute(UUID.randomUUID(), UUID.randomUUID());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply for a job with job not found")
    void itShouldNotBeAbleToApplyForAJobWithJobNotFound() {
        Mockito.when(candidateRepository.findById(any())).thenReturn(Optional.of(new CandidateEntity()));

        try {
            applyJobUseCase.execute(UUID.randomUUID(), UUID.randomUUID());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to apply for a job")
    void itShouldBeAbleToApplyForAJob() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();

        applyJob.setId(UUID.randomUUID());

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        Mockito.when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));
        Mockito.when(applyJobRepository.save(any(ApplyJobEntity.class))).thenReturn(applyJob);

        var result = applyJobUseCase.execute(candidateId, jobId);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
