package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * Define el Parametro del comando. Un comando sin esta anotación no tiene parametro.
 * Defines the Parameter of the command. A command without this annotation does not have a parameter.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Parameter {
	String name() default "Parameter";
	String desc();
	boolean visible() default true;
}
