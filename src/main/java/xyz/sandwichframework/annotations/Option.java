package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Repeatable(Options.class)
@Target(METHOD)
/**
 * Agrega una Opción al comando.
 * Adds an Option to the command.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Option {
	String name();
	String desc() default "NoDesc";
	String[] alias() default {};
	boolean enabled() default true;
	boolean visible() default true;
}
