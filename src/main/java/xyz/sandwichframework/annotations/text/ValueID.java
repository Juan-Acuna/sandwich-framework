package xyz.sandwichframework.annotations.text;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Indica el identificador para el texto.
 * Indicates the identifier for the text.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface ValueID {
	String value();
}
