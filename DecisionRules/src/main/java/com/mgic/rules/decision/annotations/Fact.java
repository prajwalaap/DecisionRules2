package com.mgic.rules.decision.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Interface Fact.
 */
@Documented
@Retention(value = RUNTIME)
@Target(value = { FIELD })
public @interface Fact {
    String fieldName();
}
