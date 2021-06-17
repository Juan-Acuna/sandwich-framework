package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * Indica que la clase contiene comandos extra.
 * Indicates that the class contains extra commands.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface ExtraCommandContainer {

}
