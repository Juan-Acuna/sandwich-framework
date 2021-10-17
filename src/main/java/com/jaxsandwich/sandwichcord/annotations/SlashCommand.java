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
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jaxsandwich.sandwichcord.development.InDevelopment;
import com.jaxsandwich.sandwichcord.models.CommandMode;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
/**
 * [ES] Indica que la clase es un comando slash.<br>
 * [EN] Indicates the class is a slash command.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public @interface SlashCommand {
	/**
	 * [ES] Nombre del comando. También es su ID.<br>
	 * [EN] Name of the command. Also its ID.
	 */
	String name();
	/**
	 * [ES] Descripción del comando. No puede estar vacía.<br>
	 * [EN] Description of the command. Can't be empty.
	 */
	String desc() default "?";
	/**
	 * [ES] Servidores(ID) a los que pertenece este comando.<br>
	 * [EN] Guilds(ID) that own this command.
	 */
	String[] guilds() default {};
	@InDevelopment
	/**
	 * [ES] Indica si el comando esta activado.<br>
	 * [EN] Indicates if the command is enabled.
	 */
	boolean enabled() default true;
	@InDevelopment
	/**
	 * [ES] El comando es visible por los comandos de ayuda.<br>
	 * [EN] The command is visible for the help commands.
	 */
	boolean visible() default true;
	@InDevelopment
	/**
	 * [ES] Es un comando NSFW.<br>
	 * [EN] It's a NSFW command.
	 */
	boolean nsfw() default false;
	@InDevelopment
	/**
	 * [ES] Indica el modo de ejecución del comando.<br>
	 * [EN] Indicates the execution mode of command.
	 */
	CommandMode commandMode() default CommandMode.SLASH_COMMAND_ONLY;
}
