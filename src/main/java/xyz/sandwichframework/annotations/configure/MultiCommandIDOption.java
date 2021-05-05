package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Identificadores de los comandos en los que esta presente esta opción.
 * Identifiers of the commands where this option is present.
 * @author Juan Acuña
 * @version 1.0
 * Requiere que el campo contenga una anotacion del tipo {@link OptionID}
 * Requires the field to have an annotation of type {@link OptionID}
 */
public @interface MultiCommandIDOption {
	String[] value();
}
