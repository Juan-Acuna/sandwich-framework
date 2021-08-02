package com.jaxsandwich.framework.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * [ES] Indica que el etodo es un Comando.<br>
 * [EN] Indicates that the method is a Command.
 * @author Juan Acuña
 * @version 1.2
 */
public @interface Command {
	/**
	 * [ES] Identificador del comando. Tambien es el nombre en el idioma por defecto indicado.<br>
	 * [EN] Identifier of the command. Also it is the name in the default language indicated.
	 */
	String id();
	/**
	 * [ES] Descripción del comando en el idioma por defecto indicado.<br>
	 * [EN] Description of the command in the default language indicated.
	 */
	String desc() default "";
	/**
	 * [ES] Alias del comando. Son otros Strings con los que se puede invocar al comando. Son unicos y no pueden repetirse ni ser iguales a otros comandos ni alias de estos.<br>
	 * [EN] Aliases of the command. They are others Strings with you can call the command. They must be unique and can't be the same as the name of others commands or their aliases.
	 */
	String[] alias() default {};
	/**
	 * [ES] Es un comando NSFW.<br>
	 * [EN] It's a NSFW command.
	 */
	boolean isNSFW() default false;
	/**
	 * [ES] Es un comando de ayuda.<br>
	 * [EN] It's a help command.
	 */
	boolean isHelpCommand() default false;
	/**
	 * [ES] El comando esta activado para su uso.<br>
	 * [EN] The command is enabled for use.
	 */
	boolean enabled() default true;
	/**
	 * [ES] El comando es visible por los comandos de ayuda.<br>
	 * [EN] The command is visible for the help commands.
	 */
	boolean visible() default true;
}
