package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Indica que el campo contiene el nombre a mostrar del parametro.
 * Indicates that the field contains the name of the parameter to show.
 * @author Juan Acuña
 * @version 1.0
 * Requiere que el campo contenga una anotacion de tipo {@link CommandID} o {@link MultiCommandIDParameter}.
 * Requires the field to have an annotation of type {@link CommandID} or {@link MultiCommandIDParameter}.
 */
public @interface CommandParameter {
	
}
