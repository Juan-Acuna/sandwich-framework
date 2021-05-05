package xyz.sandwichframework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Identifica la Categoría a configurar.
 * Identifies the Category to configure.
 * @author Juan Acuña
 * @version 1.0
 */
public @interface CategoryID {
	String value();
}
