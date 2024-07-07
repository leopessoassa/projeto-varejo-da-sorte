package com.virtualize.varejodasorte.api.util.validator;

import com.google.common.collect.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Stream;


public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static Boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static Boolean isEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    public static Boolean isNotEmptyOrZero(Number value) {
        return !isEmptyOrZero(value);
    }

    public static Boolean isEmptyOrZero(Number value) {
        return value == null || value.longValue() == 0L;
    }

    public static Boolean isEmptyOrZero(Object value) {
        if (value instanceof String) {
            return isEmpty((String) value);
        }
        if (value instanceof Number) {
            return isEmptyOrZero((Number) value);
        }
        return value == null;
    }

    public static Boolean notEmptyOrZero(Object value) {
        return !isEmptyOrZero(value);
    }

    public static Boolean areAllEmptyOrZero(Object... values) {
        return Stream.of(values).allMatch(ValidationUtil::isEmptyOrZero);
    }

    public static <T extends Comparable<T>> Boolean isBetweenOpenClosed(T target, T lowerBound, T upperBound) {
        return Range.openClosed(lowerBound, upperBound).contains(target);
    }

    public static Boolean isPositiveAtMost(BigDecimal target, BigDecimal upperBound) {
        return isBetweenOpenClosed(target, BigDecimal.ZERO, upperBound);
    }

    public static void invokeNestedValidator(String nestedPath, Validator validator, Object target, Errors errors) {
        if (target != null) {
            try {
                errors.pushNestedPath(nestedPath);
                ValidationUtils.invokeValidator(validator, target, errors);
            } finally {
                errors.popNestedPath();
            }
        }
    }

    public static void invokeNestedValidator(String nestedPath, SmartValidator validator, Object target, Errors errors, Object... validationsHints) {
        if (target != null) {
            try {
                errors.pushNestedPath(nestedPath);
                ValidationUtils.invokeValidator(validator, target, errors, validationsHints);
            } finally {
                errors.popNestedPath();
            }
        }
    }

    public static <T> Supplier<T> memoize(Supplier<T> delegate) {
        AtomicReference<T> value = new AtomicReference<>();
        return () -> {
            T val = value.get();
            if (val == null) {
                val = value.updateAndGet(cur -> cur == null ? Objects.requireNonNull(delegate.get()) : cur);
            }
            return val;
        };
    }

    public static <T, D> Function<T, Optional<D>> memoizeOptional(Function<T, Optional<D>> delegate) {
        return new Function<T, Optional<D>>() {
            private final Map<T, Optional<D>> lookup = new HashMap<>();

            @Override
            public Optional<D> apply(T input) {
                return lookup.computeIfAbsent(input, delegate);
            }
        };
    }

    public static <T> LongFunction<Optional<T>> memoizeOptionalLong(LongFunction<Optional<T>> delegate) {
        return new LongFunction<Optional<T>>() {
            private final Map<Long, Optional<T>> lookup = new HashMap<>();

            @Override
            public Optional<T> apply(long input) {
                return lookup.computeIfAbsent(Long.valueOf(input), delegate::apply);
            }
        };
    }

    public static <T> IntFunction<Optional<T>> memoizeOptionalInt(IntFunction<Optional<T>> delegate) {
        return new IntFunction<Optional<T>>() {
            private final Map<Integer, Optional<T>> lookup = new HashMap<>();

            @Override
            public Optional<T> apply(int input) {
                return lookup.computeIfAbsent(Integer.valueOf(input), delegate::apply);
            }
        };
    }

    public static <T, D> Function<T, Optional<D>> memoizeFindById(JpaRepository<D, T> repository) {
        return memoizeOptional(id -> repository.findById(id));
    }

    public static BooleanSupplier memoizeBoolean(BooleanSupplier delegate) {
        return new BooleanSupplier() {
            private final AtomicReference<Boolean> value = new AtomicReference<>();

            @Override
            public boolean getAsBoolean() {
                Boolean val = value.get();
                if (val == null) {
                    val = value.updateAndGet(cur -> cur == null ? Objects.requireNonNull(delegate.getAsBoolean()) : cur);
                }
                return val;
            }
        };
    }

    public static <T> Predicate<T> memoizePredicate(Predicate<T> delegate) {
        return new Predicate<T>() {
            private final Map<T, Boolean> lookup = new HashMap<>();

            @Override
            public boolean test(T input) {
                return lookup.computeIfAbsent(input, delegate::test);
            }
        };
    }

    public static <T> Predicate<T> memoizeChaveIsPresent(Function<T, Optional<?>> delegate) {
        return memoizePredicate(chaveIsPresent(delegate));
    }

    public static <T> Predicate<T> chaveIsPresent(Function<T, Optional<?>> delegate) {
        return key -> key != null ? delegate.apply(key).isPresent() : Boolean.FALSE;
    }

    public static void enableBeanValidation(SmartValidator smartValidator, Object target, Object[] validationHints, Errors errors) {
        if (validationHints == null || validationHints.length == 0) {
            smartValidator.validate(target, errors);
        } else {
            smartValidator.validate(target, errors, validationHints);
        }
    }

    public static Boolean validationHintPresentOrEmpty(Object[] validationHints, Object testHint) {
        return validationHints == null || validationHints.length == 0 || validationHintPresent(validationHints, testHint);
    }

    public static Boolean validationHintPresent(Object[] validationHints, Object testHint) {
        return Stream.of(validationHints).anyMatch(h -> Objects.equals(testHint, h));
    }

    public static <T> void checkRule(ValidationRule<T> rule, T target,
                                     Errors errors) {
        RuleResponse response = rule.apply(target);

        if (response.isViolated()) {
            response.getMessage().ifPresent(message -> errors.reject(message.getCode(), message.getContext(), message.getDefaultMessage()));
        }
    }

    public static <T> void checkRuleIfNotError(ValidationRule<T> rule, T target,
                                               Errors errors) {
        if (!errors.hasGlobalErrors()) {
            checkRule(rule, target, errors);
        }
    }

    public static <T> void checkValueRule(String fieldName, ValidationRule<T> rule, T target,
                                          Errors errors) {
        RuleResponse response = rule.apply(target);

        if (response.isViolated()) {
            response.getMessage().ifPresent(message -> errors.rejectValue(fieldName, message.getCode(), message.getContext(), message.getDefaultMessage()));
        }
    }

    public static <T> void checkValueRuleIfNotError(String fieldName, ValidationRule<T> rule, T target,
                                                    Errors errors) {
        if (!errors.hasFieldErrors(fieldName)) {
            checkValueRule(fieldName, rule, target, errors);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void checkValueRuleIfNotError(String fieldName, ValidationRule<T> rule, Errors errors) {
        if (!errors.hasFieldErrors(fieldName)) {
            checkValueRule(fieldName, rule, (T) errors.getFieldValue(fieldName), errors);
        }
    }


    public static void rejectWithBindException(Object target, String code, String msg) throws BindException {
        DataBinder binder = new DataBinder(target);
        binder.getBindingResult().reject(code, msg);
        throw new BindException(binder.getBindingResult());
    }


    public static void rejectWithBindException(Object target, String namespace, String code, String msg) throws BindException {
        DataBinder binder = new DataBinder(target);
        binder.getBindingResult().rejectValue(namespace, code, msg);
        throw new BindException(binder.getBindingResult());
    }

    public static void validateOrThrowBindingException(Object target, SmartValidator validator, Object... validationHints) throws BindException {
        DataBinder binder = new DataBinder(target);
        binder.setValidator(validator);
        if (validationHints.length > 0) {
            binder.validate(validationHints);
        } else {
            binder.validate();
        }

        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

    public static void validateOrThrowBindingException(Object target, Validator validator) throws BindException {
        DataBinder binder = new DataBinder(target);
        binder.setValidator(validator);
        binder.validate();

        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

    public static <T> boolean streamContains(Stream<T> stream, T subject) {
        return stream.anyMatch(ref -> Objects.equals(ref, subject));
    }

    public static Boolean isEmpty(Number value) {
        return value == null;
    }

    public static Errors validate(Object target, Validator validator) {
        DataBinder binder = new DataBinder(target);
        binder.setValidator(validator);
        binder.validate();

        return binder.getBindingResult();
    }

    public static boolean containsErrorCode(List<Errors> errors, String errorCode) {
        boolean contains = false;
        for (Errors error : errors) {
            for (ObjectError objectError : error.getAllErrors()) {
                if (objectError.getCode().equals(errorCode)) {
                    contains = true;
                    break;
                }
            }
            if (contains) {
                break;
            }
        }
        return contains;
    }


}
