package com.jaxsandwich.framework.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Define el Parametro del comando. Un comando sin esta anotación no tiene parametro.<br>
 * [EN] Defines the Parameter of the command. A command without this annotation doesn't have a parameter.
 * 
 * @author Juan Acuña
 * @version 1.2
 */
public @interface Parameter {
	/**
	 * [ES] Nombre del parametro en el idioma por defecto. Los parametros no usan identificador puesto que solo hay uno por comando.<br>
	 * [EN] Name of the parameter in the default language. The parameters don't use Identifiers because there's oly one per command.
	 */
	String name();
	/**
	 * [ES] Descripción del parametro.<br>
	 * [EN] Parameter description.
	 */
	String desc();
	/**
	 * [ES] El parametro es visible en los comandos de ayuda.<br>
	 * [EN] The parameter is visible in the help commands.
	 */
	boolean visible() default true;
}
