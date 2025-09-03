package br.com.ricardo.gestao_vagas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserFoundException extends BusinessException {
    public UserFoundException() {
        super("User already exists");
    }
}
