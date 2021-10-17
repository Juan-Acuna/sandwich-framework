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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jaxsandwich.sandwichcord.development.InDevelopment;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * [ES] Indica que la clase anidada es un subgrupo de comandos.<br>
 * [EN] Indicates the nested class is a command subgroup.
 * @author Juan Acuña
 * @version 1.0<br>
 * [ES] Requiere ser usado en una clase anidada cuya clase que la encierra debe usar la anotación {@link SlashCommand}.<br>
 * [EN] Requires be used in a nested class which enclosing class must use the {@link SlashCommand} annotation.
 * @since 0.9.0
 */
public @interface SlashCommandSubgroup {
	/**
	 * [ES] Nombre del subgrupo.<br>
	 * [EN] Name of the subgroup.
	 */
	String name();
	/**
	 * [ES] Descripción del subgrupo. No puede estar vacía.
	 * [EN] Description of the subgroup. Can't be empty.
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
