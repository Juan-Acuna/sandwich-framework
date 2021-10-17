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

package com.jaxsandwich.sandwichcord.core.util;
/**
 * [ES] Herramientas para el manejo de idiomas.<br>
 * [EN] Tools for language handling.
 * @author Juancho
 * @version 1.2
 * @since 0.3.0
 */
public class LanguageHandler {
	/**
	 * [ES] Devuelve el idioma sin un pais asociado.<br>
	 * [EN] Returns the language without any associated country.
	 */
	public static Language getLanguageParent(Language lang) {
		if(lang==null)
			return null;
		String l = lang.toString();
		if(l.length()==2)
			return lang;
		return Language.valueOf(l.substring(0,2));
	}
	/**
	 * [ES] Intenta encontrar el lenguaje mas adecuado entre las opciones disponibles.<br>
	 * [EN] Try to find the best language choise inside the availables choises.
	 */
	public static Language findBestLanguage(Language expected, Language[] availables) {
		if(availables.length<=0)
			return expected;
		expected = getLanguageParent(expected);
		for(Language lang : availables) {
			if(getLanguageParent(lang)==expected) {
				return lang;
			}
		}
		return availables[0];
	}
}
