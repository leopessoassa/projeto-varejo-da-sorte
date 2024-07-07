package com.virtualize.varejo_da_sorte.api.controllers.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

import java.util.List;

@Getter
@SuppressWarnings("serial")
public class AltValidationException extends RuntimeException {

    private final List<Errors> errors;
    
    public AltValidationException(List<Errors> errors) {
        super();
        this.errors = errors;
    }
}
