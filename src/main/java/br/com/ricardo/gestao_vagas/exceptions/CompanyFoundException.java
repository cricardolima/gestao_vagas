package br.com.ricardo.gestao_vagas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CompanyFoundException extends BusinessException {

    public CompanyFoundException() {
        super("Company already exists");
    }
}
