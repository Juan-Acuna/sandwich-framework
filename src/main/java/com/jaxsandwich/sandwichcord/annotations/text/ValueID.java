package com.jaxsandwich.sandwichcord.annotations.text;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Indica el identificador para el texto.<br>
 * [ES] Indicates the identifier for the text.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Solo puede ser usada en una clase con la anotación {@link ValuesContainer}.<br>
 * [EN] Only can be used in a class annotated with {@link ValuesContainer}.
 */
public @interface ValueID {
	String value();
}
