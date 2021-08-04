package com.jaxsandwich.sandwichcord.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Identifica a la opcion en uno o mas comandos.<br>
 * [EN] Identifies the options within one or more commands.
 * @author Juan Acuña<br>
 * @version 1.1<br>
 * [ES] Requiere que el campo contenga una anotacion del tipo {@link CommandID}<br>
 * [EN] Requires the field to have an annotation of type {@link CommandID}
 */
public @interface OptionID {
	String value();
}
