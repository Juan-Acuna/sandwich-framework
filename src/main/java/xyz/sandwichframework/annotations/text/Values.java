package xyz.sandwichframework.annotations.text;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xyz.sandwichframework.core.util.Language;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * Indica que la clase contiene textos para un determinado idioma.
 * Indicates that the class contains text for a determined language.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Values {
	Language value();
}
