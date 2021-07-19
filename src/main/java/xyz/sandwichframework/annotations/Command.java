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
 * @version 1.1
 */
public @interface Command {
	/**
	 * Identificador del comando. Tambien es el nombre en el idioma por defecto indicado.
	 * Identifier of the command. Also it is the name in the default language indicated.
	 */
	String id();
	/**
	 * Descripción del comando en el idioma por defecto indicado.
	 * Description of the command in the default language indicated.
	 */
	String desc() default "";
	/**
	 * Alias del comando. Son otros Strings con los que se puede invocar al comando. Son unicos y no pueden repetirse ni ser iguales a otros comandos ni alias de estos.
	 * Aliases of the command. They are others Strings with you can call the command. They must be unique and can't be the same as the name of others commands or their aliases.
	 */
	String[] alias() default {};
	/**
	 * Es un comando NSFW.
	 * It's a NSFW command.
	 */
	boolean isNSFW() default false;
	/**
	 * Es un comando de ayuda.
	 * It's a help command.
	 */
	boolean isHelpCommand() default false;
	/**
	 * El comando esta activado para su uso.
	 * The command is enabled for use.
	 */
	boolean enabled() default true;
	/**
	 * El comando es visible por los comandos de ayuda.
	 * The command is visible for the help commands.
	 */
	boolean visible() default true;
}
