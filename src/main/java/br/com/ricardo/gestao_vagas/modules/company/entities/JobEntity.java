package br.com.ricardo.gestao_vagas.modules.company.entities;

import java.util.UUID;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "job")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @NotBlank(message = "Level is required")
    private String level;
    private String benefits;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Column(name = "company_id", nullable = false, insertable = false, updatable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
