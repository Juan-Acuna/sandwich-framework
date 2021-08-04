package com.jaxsandwich.sandwichcord.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Repeatable(Options.class)
@Target(METHOD)
/**
 * [ES] Agrega una Opción al comando.<br>
 * [EN] Adds an Option to the command.
 * @author Juan Acuña
 * @version 1.2
 */
public @interface Option {
	/**
	 * [ES] Identificador de la opcion. También es el nombre en el idioma por defecto.<br>
	 * [EN] Identifier of the option. Also it's the name in the default language.
	 */
	String id();
	/**
	* [ES] Descripción de la opcion en el idioma por defecto.<br>
	* [EN] Option description in the default language.
	*/
	String desc() default "";
	/**
	 * [ES] Alias de la opción. Son otros Strings con los que se puede usar la opcion. No pueden repetirse ni ser iguales a otras opciones dentro del mismo comando.<br>
	 * [EN] Aliases of the option. They are others Strings with you can use the option. They can't be the same as the name of others options inside the same command.
	 */
	String[] alias() default {};
	/**
	 * [ES] Indica que la opción puede ser usada.<br>
	 * [EN] Indicates that the option can be used.
	 */
	boolean enabled() default true;
	/**
	 * [ES] La opción es visible por los comandos de ayuda.<br>
	 * [EN] The option is visible for the help commands.
	 */
	boolean visible() default true;
}
