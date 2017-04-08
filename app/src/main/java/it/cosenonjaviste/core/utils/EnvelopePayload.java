package it.cosenonjaviste.core.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An annotation for identifying the payload that we want to extract from an API response wrapped in
 * an envelope object.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface EnvelopePayload {
    String value() default "";
}