package com.virtualize.varejodasorte.api.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException{
    
    @Serial
    private static final long serialVersionUID = -6797747533347028124L;

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }

}
