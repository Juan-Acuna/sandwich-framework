package com.jaxsandwich.framework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Identifica el Comando a configurar.<br>
 * [EN] Identifies the Command to configure.
 * @author Juan Acuña
 * @version 1.1
 */
public @interface CommandID {
	String value();
}
