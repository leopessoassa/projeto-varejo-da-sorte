package com.virtualize.varejodasorte.api.util.validator;


import com.virtualize.varejodasorte.api.controllers.message.MessageTO;
import com.virtualize.varejodasorte.api.entity.enums.ChaveDescricao;
import com.virtualize.varejodasorte.api.util.DateUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ValidationRule<T> extends Function<T, RuleResponse> {

    static <T extends Number> ValidationRule<T> notZero() {
        return target -> {
            if (target != null && ValidationUtil.isEmptyOrZero(target)) {
                return RuleResponse.violated(MessageTO.getBuilder()
                        .setCode("CannotBeZero")
                        .build());
            }
            return RuleResponse.ok();
        };
    }

    static <T> ValidationRule<T> attributeExists(Predicate<T> existsPredicate) {
        return attributeExists(Function.identity(), existsPredicate);
    }

    static <T, C> ValidationRule<T> attributeExists(Function<T, C> attributeGetter, Predicate<C> existsPredicate) {
        return target -> {
            C attributeValue = attributeGetter.apply(target);
            if (attributeValue == null || existsPredicate.test(attributeValue)) {
                return RuleResponse.ok();
            } else {
                return RuleResponse.violated(MessageTO.getBuilder()
                        .setContext(new Object[]{attributeValue})
                        .setCode("DoesNotExist").build());
            }
        };
    }

    static <T, O> ValidationRule<T> attributeExistsFromSupplier(Function<T, Optional<O>> optionalSupplier) {
        return attributeExists(t -> optionalSupplier.apply(t).isPresent());
    }

    static <T, C, O> ValidationRule<T> attributeExistsFromSupplier(Function<T, C> attributeGetter,
                                                                   Function<C, Optional<O>> optionalSupplier) {
        return attributeExists(attributeGetter, c -> optionalSupplier.apply(c).isPresent());
    }

    static <T, C> ValidationRule<T> supplierDoesNotExist(Supplier<Optional<C>> optionalSupplier) {
        return target -> {
            if (optionalSupplier.get().isPresent()) {
                return RuleResponse.ok();
            } else {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("DoesNotExist").build());
            }
        };
    }

    static <T, C> ValidationRule<T> supplierExists(Supplier<Optional<C>> optionalSupplier) {
        return target -> {
            if (optionalSupplier.get().isPresent()) {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("Exists").build());
            } else {
                return RuleResponse.ok();
            }
        };
    }

    static ValidationRule<LocalDate> pastOrPresent() {
        return pastOrPresent(Function.identity());
    }

    static <T> ValidationRule<T> pastOrPresent(Function<T, LocalDate> attributeGetter) {
        return target -> {
            LocalDate reference = attributeGetter.apply(target);
            LocalDate hoje = DateUtil.hojeLocalDate();
            if (reference != null && reference.isAfter(hoje)) {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("PastOrPresent")
                        .setDefaultMessage("Não pode ser posterior à data atual.")
                        .setContext(new Object[]{reference, hoje}).build());
            } else {
                return RuleResponse.ok();
            }
        };
    }

    static <T, V extends Comparable<V>> ValidationRule<T> cannotBeGreather(Function<T, V> firstGetter,
                                                                           Function<T, V> secondGetter) {
        return invalidIfBiPredicate((first, second) -> first.compareTo(second) > 0, firstGetter, secondGetter)
                .replaceMessagePartial(builder -> builder.setCode("CannotBeAfter")
                        .setDefaultMessage("Primeiro valor não pode ser maior que o segundo.").build());
    }

    static <T, V extends Comparable<V>> ValidationRule<T> cannotBeLower(Function<T, V> firstGetter,
                                                                        Function<T, V> secondGetter) {
        return invalidIfBiPredicate((first, second) -> first.compareTo(second) < 0, firstGetter, secondGetter)
                .replaceMessagePartial(builder -> builder.setCode("CannotBeBefore")
                        .setDefaultMessage("Primeiro valor não pode ser menor que o segundo.").build());
    }

    static <T, V> ValidationRule<T> invalidIfBiPredicate(BiPredicate<V, V> predicate, Function<T, V> firstGetter,
                                                         Function<T, V> secondGetter) {
        return target -> {
            if (target != null) {
                V first = firstGetter.apply(target);
                V second = secondGetter.apply(target);
                if (first != null && second != null && predicate.test(first, second)) {
                    return RuleResponse.violated(MessageTO.getBuilder().setCode("InvalidIfBiPredicate")
                            .setDefaultMessage("Predicado violado.").setContext(new Object[]{first, second})
                            .build());
                }
            }
            return RuleResponse.ok();
        };
    }

    static <T> ValidationRule<T> localDateBetween(Function<T, LocalDate> attributeGetter, LocalDate startDate,
                                                  LocalDate endDate) {
        return target -> {
            Predicate<LocalDate> localDateNotBetweenPredicate = ref -> ref.isBefore(startDate) || ref.isAfter(endDate);
            Optional<LocalDate> referenceOpt = Optional.ofNullable(target).map(attributeGetter);
            boolean localDateNotBetween = referenceOpt.filter(localDateNotBetweenPredicate).isPresent();
            if (localDateNotBetween) {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("LocalDateNotBetween")
                        .setDefaultMessage("Data fora do período permitido.")
                        .setContext(new Object[]{referenceOpt.get(), startDate, endDate}).build());
            } else {
                return RuleResponse.ok();
            }
        };
    }

    static <T, C extends Number, O extends ChaveDescricao<C>> ValidationRule<T> optionsContainsChaveDescricao(
            Stream<O> options, Function<T, Optional<C>> chaveGetter, Function<C, Optional<O>> contentGetter) {
        return ValidationRule.contentMatchesOptions(options, chaveGetter, contentGetter,
                ChaveDescricao::forChaveContent, Stream::anyMatch);
    }

    static <T, C extends Number, O extends ChaveDescricao<C>> ValidationRule<T> optionsDoesNotContainChaveDescricao(
            Stream<O> options, Function<T, Optional<C>> chaveGetter, Function<C, Optional<O>> contentGetter) {
        return ValidationRule.contentMatchesOptions(options, chaveGetter, contentGetter,
                ChaveDescricao::forChaveContent, Stream::noneMatch);
    }

    static <T, C, O> ValidationRule<T> contentMatchesOptions(Stream<O> options, Function<T, Optional<C>> chaveGetter,
                                                             Function<C, Optional<O>> contentGetter, BiFunction<C, Optional<O>, Object> contextGenerator,
                                                             BiFunction<Stream<O>, Predicate<O>, Boolean> condition) {
        return target -> {
            Optional<C> chaveOpt = chaveGetter.apply(target);

            if (chaveOpt.isPresent()) {
                Optional<O> contentOpt = chaveOpt.flatMap(contentGetter);
                if (contentOpt.filter(s1 -> condition.apply(options, s2 -> Objects.equals(s1, s2))).isPresent()) {
                    return RuleResponse.violated(MessageTO.getBuilder().setCode("content")
                            .setContext(new Object[]{contextGenerator.apply(chaveOpt.get(), contentOpt)})
                            .setDefaultMessage(
                                    "Cadastro não permitido. Verifique a situação do Benefício na opção ART29NB.")
                            .build());
                }
            }
            return RuleResponse.ok();
        };
    }

    static <T, C, K> ValidationRule<T> duplicatedAttribute(Function<T, ? extends Collection<C>> collectionGetter,
                                                           Function<C, K> attributeGetter, Predicate<K> valueChecker) {
        return target -> {
            Set<K> invalidValues = collectionGetter.apply(target).stream().map(attributeGetter).filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
                    .filter(entry -> valueChecker.test(entry.getKey()) && entry.getValue() > 1L).map(Entry::getKey)
                    .collect(Collectors.toSet());

            if (invalidValues.isEmpty()) {
                return RuleResponse.ok();
            } else {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("DuplicatedAttribute")
                        .setContext(new Object[]{invalidValues}).build());
            }
        };
    }

    static <T> ValidationRule<T> required(Function<T, ? extends Object> attributeGetter) {
        return target -> {
            if (ValidationUtil.isEmptyOrZero(attributeGetter.apply(target))) {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("NotNull").build());
            } else {
                return RuleResponse.ok();
            }
        };
    }

    static <T> ValidationRule<T> requiredTogether(
            @SuppressWarnings("unchecked") Function<T, ? extends Object>... attributeGetter) {
        return acceptAllOrNone(Stream.of(attributeGetter).map(ValidationRule::required).collect(Collectors.toList()))
                .replaceMessagePartial(builder -> builder.setCode("RequiredTogether").build());
    }

    static <T> ValidationRule<T> requiredAny(
            @SuppressWarnings("unchecked") Function<T, ? extends Object>... attributeGetter) {
        return acceptAny(Stream.of(attributeGetter).map(ValidationRule::required).collect(Collectors.toList()))
                .replaceMessagePartial(builder -> builder.setCode("atLeastOneRequired").setDefaultMessage("É necessário informar pelo menos um campo.").build());
    }

    static <T> ValidationRule<T> getIdentity() {
        return target -> RuleResponse.ok();
    }

    static <T> ValidationRule<T> acceptAllOrNone(Collection<ValidationRule<T>> rules) {
        return target -> {
            boolean allViolated = rules.stream().map(rule -> rule.apply(target)).allMatch(RuleResponse::isViolated);
            boolean noneViolated = rules.stream().map(rule -> rule.apply(target)).noneMatch(RuleResponse::isViolated);
            if (allViolated || noneViolated) {
                return RuleResponse.ok();
            } else {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("AllOrNone").build());
            }

        };
    }

    static <T> ValidationRule<T> acceptAny(Collection<ValidationRule<T>> rules) {
        return target -> {
            boolean anyNotViolated = rules.stream().map(rule -> rule.apply(target)).anyMatch(r -> !r.isViolated());
            if (anyNotViolated) {
                return RuleResponse.ok();
            } else {
                return RuleResponse.violated(MessageTO.getBuilder().setCode("AllViolated").build());
            }

        };
    }

    static <T> ValidationRule<T> responseOnAnyViolation(Collection<ValidationRule<T>> rules) {
        return target -> rules.stream().map(rule -> rule.apply(target)).filter(RuleResponse::isViolated).findAny()
                .orElse(RuleResponse.ok());
    }

    default ValidationRule<T> and(ValidationRule<T> other) {
        return t -> RuleResponse.and(this.apply(t), other.apply(t));
    }

    default ValidationRule<T> ifViolated(ValidationRule<T> other) {
        return t -> {
            RuleResponse otherResponse = other.apply(t);
            if (otherResponse.isViolated()) {
                return this.apply(t);
            } else {
                return RuleResponse.ok();
            }
        };
    }

    default ValidationRule<T> ifNotViolated(ValidationRule<T> other) {
        return t -> {
            RuleResponse otherResponse = other.apply(t);
            if (!otherResponse.isViolated()) {
                return this.apply(t);
            } else {
                return RuleResponse.ok();
            }
        };
    }

    default ValidationRule<T> replaceMessagePartial(Function<MessageTO.Builder, MessageTO> messageReplacer) {
        return t -> this.apply(t).replaceMessagePartial(messageReplacer);
    }

    default ValidationRule<T> replaceMessagePartial(BiFunction<MessageTO.Builder, T, MessageTO> messageReplacer) {
        return t -> this.apply(t).replaceMessagePartial(messageReplacer, t);
    }

    default ValidationRule<T> filter(Predicate<T> predicate) {
        return t -> {
            if (predicate.test(t)) {
                return this.apply(t);
            } else {
                return RuleResponse.ok();
            }
        };
    }

}
