/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright 2021 Juan Acuña                                                   *
 *                                                                             *
 * Licensed under the Apache License, Version 2.0 (the "License");             *
 * you may not use this file except in compliance with the License.            *
 * You may obtain a copy of the License at                                     *
 *                                                                             *
 *     http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                             *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.                                              *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.jaxsandwich.sandwichcord.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jaxsandwich.sandwichcord.development.InDevelopment;
import com.jaxsandwich.sandwichcord.models.CommandMode;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * [ES] Indica que el metodo es un Comando.<br>
 * [EN] Indicates that the method is a Command.
 * @author Juan Acuña
 * @version 1.3
 * @since 0.0.1
 */
public @interface Command {
	/**
	 * [ES] Identificador del comando. Tambien es el nombre en el idioma por defecto indicado.<br>
	 * [EN] Identifier of the command. Also it is the name in the default language indicated.
	 */
	String id();
	/**
	 * [ES] Descripción del comando en el idioma por defecto indicado.<br>
	 * [EN] Description of the command in the indicated default language.
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
	@InDevelopment
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
	/**
	 * [ES] Indica el modo de ejecución del comando.<br>
	 * [EN] Indicates the execution mode of command.
	 */
	CommandMode commandMode() default CommandMode.UNSET;
}
