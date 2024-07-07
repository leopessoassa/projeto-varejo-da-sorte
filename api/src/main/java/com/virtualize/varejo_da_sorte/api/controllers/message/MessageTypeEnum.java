package com.virtualize.varejo_da_sorte.api.controllers.message;

public enum MessageTypeEnum {
    INFO("info"),
    SUCCESS("success"),
    WARN("warn"),
    INVALID("invalid"),
    ERROR("error");

    private final String code;

    MessageTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
