package com.virtualize.varejo_da_sorte.api.util.validator;


import com.google.common.collect.ImmutableList;
import com.virtualize.varejo_da_sorte.api.controllers.message.MessageTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public final class RuleResponse {

    private final boolean violated;

    private final ImmutableList<MessageTO> messages;

    private RuleResponse(final boolean violated, final MessageTO message) {
        this.violated = violated;
        this.messages = Optional.ofNullable(message)
                .map(ImmutableList::of)
                .orElse(ImmutableList.of());
    }

    private RuleResponse(final boolean violated, final List<MessageTO> messages) {
        this.violated = violated;
        this.messages = ImmutableList.copyOf(messages);
    }

    public static RuleResponse and(RuleResponse... rules) {
        return new RuleResponse(
                Stream.of(rules).anyMatch(RuleResponse::isViolated),
                Stream.of(rules)
                        .map(RuleResponse::getMessages)
                        .flatMap(ImmutableList::stream)
                        .collect(Collectors.toList()));
    }

    public static RuleResponse ok() {
        return new RuleResponse(false, Collections.emptyList());
    }

    public static RuleResponse ok(@NotNull final MessageTO message) {
        return new RuleResponse(false, message);
    }

    public static RuleResponse violated(@NotNull final MessageTO message) {
        return new RuleResponse(true, message);
    }

    public boolean isOk() {
        return !isViolated();
    }

    public Optional<MessageTO> getMessage() {
        return messages.stream().findFirst();
    }

    public RuleResponse replaceMessagePartial(Function<MessageTO.Builder, MessageTO> messageReplacer) {
        if (this.violated) {
            return new RuleResponse(this.violated, messageReplacer.apply(MessageTO.getBuilder(this.getMessage().orElse(MessageTO.getBuilder().build()))));
        } else {
            return RuleResponse.ok();
        }
    }

    public <T> RuleResponse replaceMessagePartial(BiFunction<MessageTO.Builder, T, MessageTO> messageReplacer, T target) {
        if (this.violated) {
            return new RuleResponse(this.violated, messageReplacer.apply(MessageTO.getBuilder(this.getMessage().orElse(MessageTO.getBuilder().build())), target));
        } else {
            return RuleResponse.ok();
        }
    }
}
