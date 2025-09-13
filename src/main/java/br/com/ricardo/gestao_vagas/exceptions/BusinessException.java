package br.com.ricardo.gestao_vagas.exceptions;

/**
 * Exception para erros de negócio
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
