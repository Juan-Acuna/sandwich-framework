package com.jaxsandwich.framework.core.util;
/**
 * [ES] Herramientas para el manejo de idiomas.<br>
 * [EN] Tools for language handling.
 * @author Juancho
 * @version 1.2
 */
public class LanguageHandler {
	/**
	 * [ES] Devuelve el idioma sin un pais asociado.<br>
	 * [EN] Returns the language without any associated country.
	 */
	public static Language getLanguageParent(Language lang) {
		if(lang==null) {
			return null;
		}
		switch(lang) {
			case ES_MX:
			case ES_ES:
				return Language.ES;
			case EN_US:
			case EN_GB:
				return Language.EN;
			default:
				return lang;
		}
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
