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

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import com.jaxsandwich.sandwichcord.development.NotDocumented;

@Retention(RUNTIME)
@Repeatable(Options.class)
@Target(METHOD)
/**
 * [ES] Agrega una Opción al comando.<br>
 * [EN] Adds an Option to the command.
 * @author Juan Acuña
 * @version 1.4
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
	@NotDocumented
	boolean noStandar() default false;
	@NotDocumented
	boolean required() default false;
	@NotDocumented
	OptionType type() default OptionType.STRING;
}
