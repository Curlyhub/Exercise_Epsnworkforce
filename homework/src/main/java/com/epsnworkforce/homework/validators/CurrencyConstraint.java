package com.epsnworkforce.homework.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyConstraint {
    /**
     *
     */
    public static final String INVALID_CURRENCY = "Invalid currency";

    String message() default INVALID_CURRENCY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
