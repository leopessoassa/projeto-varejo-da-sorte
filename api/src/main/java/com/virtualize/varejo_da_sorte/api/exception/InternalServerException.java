package com.virtualize.varejo_da_sorte.api.exception;

import java.io.Serial;

public class InternalServerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6515965345792182878L;

    public InternalServerException(String msg) {
        super(msg);
    }

    public InternalServerException(String msg, Throwable t) {
        super(msg, t);
    }
}
