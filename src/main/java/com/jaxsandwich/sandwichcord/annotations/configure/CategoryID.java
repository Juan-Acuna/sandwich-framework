package com.jaxsandwich.sandwichcord.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Identifica la Categoría a configurar.<br>
 * [EN] Identifies the Category to configure.
 * @author Juan Acuña
 * @version 1.1
 */
public @interface CategoryID {
	String value();
}
