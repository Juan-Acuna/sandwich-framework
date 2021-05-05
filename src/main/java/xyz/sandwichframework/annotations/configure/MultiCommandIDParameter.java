package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Identificadores de los comandos en los que este parametro se repite.
 * Identifiers of the commands where this parameter is the same.
 * @author Juan Acuña
 * @version 1.0
 */
public @interface MultiCommandIDParameter {
	String[] value();
}
