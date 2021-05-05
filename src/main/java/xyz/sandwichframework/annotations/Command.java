package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Indica que el etodo es un Comando.
 * Indicates that the method is a Command.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Command {
	/**
	 * Identificador del comando. Tambien es el nombre en el idioma por defecto indicado.
	 * Identifier of the command. Also it is the name in the default language indicated.
	 */
	String name();
	String desc() default "";
	String[] alias() default {};
	boolean enabled() default true;
	boolean visible() default true;
}
