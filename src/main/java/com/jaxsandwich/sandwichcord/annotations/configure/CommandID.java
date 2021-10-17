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
 * [ES] Identifica el Comando a configurar.<br>
 * [EN] Identifies the Command to configure.
 * @author Juan Acuña
 * @version 1.1
 * @since 0.3.0
 * @deprecated <br>
 * [ES] En su lugar, use {@link com.jaxsandwich.sandwichcord.core.LanguageConfiguration}.<br>
 * [EN] Instead, use {@link com.jaxsandwich.sandwichcord.core.LanguageConfiguration}.
 */
@Deprecated
public @interface CommandID {
	/**
	 * [ES] Identificador del comando.<br>
	 * [EN] Identifier of command.
	 */
	String value();
}
