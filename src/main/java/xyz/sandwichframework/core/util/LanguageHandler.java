package xyz.sandwichframework.core.util;
/**
 * Herramientas para el manejo de idiomas.
 * Tools for language handling.
 * @author Juancho
 * @version 1.1
 */
public class LanguageHandler {
	public static Language getLanguageParent(Language lang) {
		if(lang==null) {
			return null;
		}
		switch(lang) {
			case ES_MX:
			case ES_ES:
				return Language.ES;
			case EN_US:
			case EN_EN:
				return Language.EN;
			default:
				return lang;
		}
	}
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
