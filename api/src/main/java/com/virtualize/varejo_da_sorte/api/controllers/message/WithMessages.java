package com.virtualize.varejo_da_sorte.api.controllers.message;


import com.virtualize.varejo_da_sorte.api.controllers.exception.AltValidationException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WithMessages<T> {

    private T body;
    private List<MessageTO> globalMessages;
    private Map<String, Set<MessageTO>> namespacedMessages;

    public WithMessages(@Nullable T body, @Nullable Set<MessageTO> messages) {
        this.setBody(body);
        this.setGlobalMessages(Optional.ofNullable(messages).orElse(Collections.emptySet()).stream()
                .filter(message -> Objects.isNull(message.getFieldName())).collect(Collectors.toList()));
        this.setNamespacedMessages(Optional.ofNullable(messages).orElse(Collections.emptySet()).stream()
                .filter(message -> Objects.nonNull(message.getFieldName()))
                .collect(Collectors.groupingBy(MessageTO::getFieldName, Collectors.toSet())));
    }

    public WithMessages(BindException bindException) {
        this.body = null;
        this.setGlobalMessages(bindException.getGlobalErrors().stream().map(MessageTO::forValidationError)
                .collect(Collectors.toList()));
        this.setNamespacedMessages(bindException.getFieldErrors().stream().map(MessageTO::forValidationError)
                .collect(Collectors.groupingBy(MessageTO::getFieldName, Collectors.toSet())));
    }

    public WithMessages(@Nullable Set<MessageTO> messages) {
        this(null, messages);
    }

    public WithMessages(@Nullable MessageTO message) {
        this(null, Stream.of(message).collect(Collectors.toSet()));
    }

    public WithMessages(T body, @Nullable MessageTO message) {
        this(body, Stream.of(message).collect(Collectors.toSet()));
    }

    public WithMessages(AltValidationException altValidationException) {
        this.body = null;
        List<ObjectError> objectErrors = new ArrayList<>();
        altValidationException.getErrors().forEach(a -> objectErrors.addAll(a.getAllErrors()));
        this.setGlobalMessages(objectErrors.stream().sorted(Comparator.comparing(ObjectError::getDefaultMessage)).map(MessageTO::forValidationError).collect(Collectors.toList()));
    }

    public static <T> WithMessages<T> empty() {
        return new WithMessages<>(Collections.emptySet());
    }

    public static <T> WithMessages<T> success() {
        return success("Operação realizada com sucesso.");
    }

    public static <T> WithMessages<T> success(T body) {
        return success(body, "Operação realizada com sucesso.");
    }

    public static <T> WithMessages<T> success(String message) {
        return success(null, message);
    }

    public static <T> WithMessages<T> success(T body, String message) {
        return new WithMessages<>(body, MessageTO.getBuilder().setCode("success")
                .setDefaultMessage(message).setType(MessageTypeEnum.SUCCESS).build());
    }

    public boolean isSuccess() {
        return !this.getGlobalMessages().isEmpty() && this.getGlobalMessages().stream().allMatch(message -> MessageTypeEnum.SUCCESS.equals(message.getType()));
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<MessageTO> getGlobalMessages() {
        return globalMessages;
    }

    public void setGlobalMessages(List<MessageTO> globalMessages) {
        this.globalMessages = globalMessages;
    }

    public Map<String, Set<MessageTO>> getNamespacedMessages() {
        return namespacedMessages;
    }

    public void setNamespacedMessages(Map<String, Set<MessageTO>> namespacedMessages) {
        this.namespacedMessages = namespacedMessages;
    }

}
