package com.musala.drones.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = DroneIdExistsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface DroneIdExists {

    String message() default "DroneIdExists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
