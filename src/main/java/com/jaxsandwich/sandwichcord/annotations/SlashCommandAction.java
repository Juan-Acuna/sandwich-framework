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
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jaxsandwich.sandwichcord.development.InDevelopment;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Indica que el metodo es la acción de un comando slash o un subcomando.<br>
 * [EN] Indicates the method is the action of a slash command or a subcommand.
 * @author Juan Acuña.
 * @version 1.0
 * @since 0.9.0
 */
public @interface SlashCommandAction {
	/**
	 * [ES] Nombre del subcomando. Si es la acción de un comando slash este campo se ignora.<br>
	 * [EN] Name of the subcommand. If it's the action of a slash command this field is ignored.
	 */
	String name();
	/**
	 * [ES] Descripción del subcomando. No puede estar vacía. Si es la acción de un comando slash este campo se ignora.<br>
	 * [EN] Description of the subcommand. Can't be empty. If it's the action of a slash command this field is ignored.
	 */
	String desc() default "?";
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
}
