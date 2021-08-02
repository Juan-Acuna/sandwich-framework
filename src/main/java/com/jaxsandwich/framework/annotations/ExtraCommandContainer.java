package com.jaxsandwich.framework.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * [ES] Indica que la clase contiene comandos extra.<br>
 * [EN] Indicates that the class contains extra commands.
 * @author Juan Acuña
 * @version 1.1
 */
public @interface ExtraCommandContainer {

}
