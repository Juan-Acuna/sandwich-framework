package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Indica que el campo es la descripción de la opción.
 * Indicates that the field is the description of the option.
 * @author Juan Acuña
 * @version 1.0
 * Requiere que el campo contenga una anotacion del tipo {@link OptionID}
 * Requires the field to have an annotation of type {@link OptionID}
 */
public @interface OptionDescription {
}
