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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
/**
 * [ES] Indica que la clase es una Categoría, la cual será convertida en un objeto {@link com.jaxsandwich.sandwichcord.models.CategoryObject} por el framework.<br>
 * [EN] Indicates that the class is a Category, which will be turned into a {@link com.jaxsandwich.sandwichcord.models.CategoryObject} object by the framework.
 * @author Juan Acuña
 * @version 1.2
 * @since 0.1.0
 */
public @interface Category {
	/**
	 * [ES] Identificador de la categoría. Tambien es el nombre en el idioma por defecto indicado. Si no esta presente, se usa el nombre de la clase.<br>
	 * [EN] Identifier of the category. Also it is the name in the default language indicated. If it is not present, it uses the class name.
	 */
	String id() default "";
	/**
	 * [ES] Descripción de la categoría.<br>
	 * [EN] Description of the category.
	 */
	String desc() default "";
	/**
	 * [ES] Indica si la categoría es NSFW.<br>
	 * [EN] Indicates if the category is NSFW.
	 */
	boolean nsfw() default false;
	/**
	 * [ES] La categoría es visible para los comandos de ayuda.<br>
	 * [EN] The category is visible for the help commands.
	 */
	boolean visible() default true;
	/**
	 * [ES] Los comandos de esta categoría pueden ser ejecutados.<br>
	 * [EN] The commands of this category can be executed.
	 */
	boolean enabled() default true;
	/**
	 * [ES] Indica si esta es una categoría especial.<br>
	 * [EN] Indicates if this is a special category.
	 */
	boolean isSpecial() default false;
}
