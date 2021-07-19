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
 * @version 1.1
 */
public @interface Option {
	/**
	 * Identificador de la opcion. También es el nombre en el idioma por defecto.
	 * Identifier of the option. Also it's the name in the default language.
	 */
	String id();
	/**
	* Descripción de la opcion en el idioma por defecto.
	* Option description in the default language.
	*/
	String desc() default "";
	/**
	 * Alias de la opción. Son otros Strings con los que se puede usar la opcion. No pueden repetirse ni ser iguales a otras opciones dentro del mismo comando.
	 * Aliases of the option. They are others Strings with you can use the option. They can't be the same as the name of others options inside the same command.
	 */
	String[] alias() default {};
	/**
	 * Indica que la opción puede ser usada.
	 * Indicates that the option can be used.
	 */
	boolean enabled() default true;
	/**
	 * La opción es visible por los comandos de ayuda.
	 * The option is visible for the help commands.
	 */
	boolean visible() default true;
}
