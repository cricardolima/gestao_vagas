package br.com.ricardo.gestao_vagas.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageDTO {
    private final String message;
    private String field;
}
