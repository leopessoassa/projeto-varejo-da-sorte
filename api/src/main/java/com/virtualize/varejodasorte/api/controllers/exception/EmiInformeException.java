package com.virtualize.varejodasorte.api.controllers.exception;

import java.io.Serial;

public class EmiInformeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2376709688043891871L;

    public EmiInformeException(String message) {
        super(message);
    }

    public EmiInformeException(String message, Throwable e) {
        super(message, e);
    }

}
