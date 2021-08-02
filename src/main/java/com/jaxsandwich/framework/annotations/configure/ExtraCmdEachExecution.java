package com.jaxsandwich.framework.annotations.configure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Identifica el metodo que se ejecutara cada medio segundo mientras se espera respuesta.<br>
 * [EN] Identifies the method wich will run every half second while the extra command wait for a response.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere ser usado en una clase con la anotacion {@link ExtraCommandContainer}.<br>
 * [EN] Requires be used in a class with the annotation {@link ExtraCommandContainer}.
 */
public @interface ExtraCmdEachExecution {
	String name();
}
