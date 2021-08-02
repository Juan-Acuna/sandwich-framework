package com.jaxsandwich.framework.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Requerido por otras anotaciones.<br>
 * [EN] Required by others annotations.
 * @author Juan Acuña
 * @version 1.1
 */
public @interface Options {
	Option[] value();
}
