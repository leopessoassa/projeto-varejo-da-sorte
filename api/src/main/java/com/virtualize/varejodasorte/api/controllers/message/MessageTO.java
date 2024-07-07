package com.virtualize.varejodasorte.api.controllers.message;

import com.virtualize.varejodasorte.api.entity.enums.ChaveDescricao;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class MessageTO {

    private final String fieldName;
    private final String code;
    private final String defaultMessage;
    private final Object[] context;
    private final MessageTypeEnum type;

    protected MessageTO(String fieldName, String code, String defaultMessage, Object[] context, MessageTypeEnum type) {
        this.fieldName = fieldName;
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.context = context;
        this.type = type;
    }

    public static MessageTO of(String code, String message, MessageTypeEnum type) {
        return new MessageTO(null,
                code,
                message,
                new Object[0],
                type);
    }

    public static MessageTO of(ChaveDescricao<? extends Object> chaveDescricao, MessageTypeEnum type) {
        return of(chaveDescricao.getChave().toString(), chaveDescricao.getDescricao(), type);
    }

    public static MessageTO forValidationError(ObjectError objectError) {
        return new MessageTO(null,
                objectError.getCode(),
                objectError.getDefaultMessage(),
                objectError.getArguments(),
                MessageTypeEnum.INVALID);
    }

    public static MessageTO forValidationError(FieldError fieldError) {
        return new MessageTO(fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage(),
                fieldError.getArguments(),
                MessageTypeEnum.INVALID);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static Builder getBuilder(MessageTO other) {
        return new Builder()
                .setCode(other.getCode())
                .setContext(other.getContext())
                .setDefaultMessage(other.getDefaultMessage())
                .setFieldName(other.getFieldName())
                .setType(other.getType());
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public Object[] getContext() {
        return context;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public static class Builder {
        private String fieldName;
        private String code;
        private String defaultMessage;
        private Object[] context;
        private MessageTypeEnum type;

        protected Builder() {
            this.fieldName = null;
            this.code = null;
            this.defaultMessage = null;
            this.context = new Object[0];
            this.type = MessageTypeEnum.INVALID;
        }

        public Builder setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setDefaultMessage(String defaultMessage) {
            this.defaultMessage = defaultMessage;
            return this;
        }

        public Builder setContext(Object[] context) {
            this.context = context;
            return this;
        }

        public Builder setType(MessageTypeEnum type) {
            this.type = type;
            return this;
        }

        public MessageTO build() {
            return new MessageTO(fieldName, code, defaultMessage, context, type);
        }
    }

}
