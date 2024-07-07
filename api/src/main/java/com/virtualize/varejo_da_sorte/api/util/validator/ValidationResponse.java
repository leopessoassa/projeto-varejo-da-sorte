package com.virtualize.varejo_da_sorte.api.util.validator;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.virtualize.varejo_da_sorte.api.controllers.message.MessageTO;
import lombok.Getter;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public final class ValidationResponse {

    private static final ValidationResponse VALID_RESPONSE;

    static {
        VALID_RESPONSE = new ValidationResponse(true, ImmutableList.of(), ImmutableMap.of());
    }

    private final Boolean valid;
    private final ImmutableList<MessageTO> globalErrors;
    private final ImmutableMap<String, ImmutableList<MessageTO>> controlErrors;

    private ValidationResponse(Boolean valid, ImmutableList<MessageTO> globalErrors, ImmutableMap<String, ImmutableList<MessageTO>> controlErrors) {
        this.valid = valid;
        this.globalErrors = globalErrors;
        this.controlErrors = controlErrors;
    }

    public static ValidationResponse merge(ValidationResponse... responses) {
        return getBuilder()
                .setGlobalErrors(Stream.of(responses).map(ValidationResponse::getGlobalErrors).flatMap(List::stream).collect(Collectors.toList()))
                .setAllControlErrors(Stream.of(responses).map(ValidationResponse::getControlErrors).map(Map::entrySet).flatMap(Collection::stream)
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                (value1, value2) -> ImmutableList.copyOf(Stream.concat(value1.stream(), value2.stream()).collect(Collectors.toList())))))
                .build();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static Builder getBuilder(ValidationResponse validationResponse) {
        return new Builder()
                .setGlobalErrors(validationResponse.globalErrors)
                .setAllControlErrors(validationResponse.getControlErrors());
    }

    public static ValidationResponse valid() {
        return VALID_RESPONSE;
    }

    public static class Builder {

        private Boolean valid;
        private List<MessageTO> globalErrors;
        private Map<String, List<MessageTO>> controlErrors;

        public Builder() {
            this.valid = true;
            this.globalErrors = new ArrayList<>();
            this.controlErrors = new HashMap<>();
        }

        public Builder setGlobalErrors(List<MessageTO> errors) {
            this.valid = this.valid && errors.isEmpty();
            this.globalErrors = ImmutableList.copyOf(errors);
            return this;
        }

        public Builder setAllControlErrors(Map<String, ? extends List<MessageTO>> controlErrors) {
            this.valid = this.valid && controlErrors.entrySet().stream()
                    .map(Entry::getValue)
                    .allMatch(List::isEmpty);
            this.controlErrors = controlErrors.entrySet().stream()
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
            return this;
        }

        public Builder setControlErrors(String control, List<MessageTO> errors) {
            this.valid = this.valid && errors.isEmpty();
            this.controlErrors.put(control, errors);
            return this;
        }

        public ValidationResponse build() {
            return new ValidationResponse(valid, ImmutableList.copyOf(globalErrors), ImmutableMap.copyOf(
                    this.controlErrors.entrySet().stream()
                            .collect(Collectors.toMap(Entry::getKey, entry -> ImmutableList.copyOf(entry.getValue())))));
        }

    }

}
