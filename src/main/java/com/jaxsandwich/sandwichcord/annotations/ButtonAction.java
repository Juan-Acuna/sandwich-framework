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

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Indica que el metodo es una acción relacionada a un botón de Discord.<br>
 * [EN] Indicates the method is an action related to a Discord button.
 * @author Juan Acuña
 * @version 1.0<br>
 * [ES] Requiere ser usado en una clase con la anotacion {@link ActionContainer}.<br>
 * [EN] Requires be used in a class with the annotation {@link ActionContainer}.
 * @since 0.8.0
 */
public @interface ButtonAction {
	/**
	 * [ES] Identificador del botón.
	 * [EN] Identifier of th button.
	 */
	String value();
}
