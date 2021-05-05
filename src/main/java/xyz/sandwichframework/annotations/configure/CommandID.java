package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Identifica el Comando a configurar.
 * Identifies the Command to configure.
 * @author Juan Acuña
 * @version 1.0
 */
public @interface CommandID {
	String value();
}
