package com.jaxsandwich.framework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Identificadores de los comandos en los que esta presente esta opción.<br>
 * [ES] Identifiers of the commands where this option is present.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere que el campo contenga una anotacion del tipo {@link OptionID}<br>
 * [ES] Requires the field to have an annotation of type {@link OptionID}
 */
public @interface MultiCommandIDOption {
	String[] value();
}
