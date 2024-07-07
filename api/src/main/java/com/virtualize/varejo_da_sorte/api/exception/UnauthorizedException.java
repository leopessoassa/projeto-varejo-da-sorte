package com.virtualize.varejo_da_sorte.api.exception;

import java.io.Serial;

public class UnauthorizedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6797747533347045124L;

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }

}
