package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Identifica a la opcion en uno o mas comandos.
 * Identifies the options within one or more commands.
 * @author Juan Acuña
 * Requiere que el campo contenga una anotacion del tipo {@link CommandID}
 * Requires the field to have an annotation of type {@link CommandID}
 */
public @interface OptionID {
	String value();
}
