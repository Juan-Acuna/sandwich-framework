package com.jaxsandwich.framework.annotations.configure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Identifica el nombre del comando extra. No es obligatorio.<br>
 * [EN] Identifies the name of the extra command. It's not required.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere ser usado en una clase con la anotacion {@link ExtraCommandContainer}.<br>
 * [EN] Requires be used in a class with the annotation {@link ExtraCommandContainer}.
 */
public @interface ExtraCmdExecutionName {
	String value();
}
