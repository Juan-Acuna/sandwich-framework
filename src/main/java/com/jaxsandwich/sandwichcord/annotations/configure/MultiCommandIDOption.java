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

package com.jaxsandwich.sandwichcord.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Identificadores de los comandos en los que esta presente esta opción.<br>
 * [ES] Identifiers of the commands where this option is present.
 * @author Juan Acuña
 * @since 0.3.1
 * @version 1.1<br>
 * [ES] Requiere que el campo contenga una anotacion del tipo {@link OptionID}<br>
 * [ES] Requires the field to have an annotation of type {@link OptionID}
 */
public @interface MultiCommandIDOption {
	/**
	 * [ES] Identificadores de comandos.<br>
	 * [EN] Identifiers of commands.
	 */
	String[] value();
}
