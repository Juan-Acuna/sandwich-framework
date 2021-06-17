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
	public static String specialWords(Language lang, String word) {
		switch(word.toLowerCase()) {
			case "command":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Comando";
					case EN:
						return "Command";
					default:
						return word;
				}
			case "commands":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Comandos";
					case EN:
						return "Commands";
					default:
						return word;
				}
			case "category":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Categoría";
					case EN:
						return "Category";
					default:
						return word;
				}
			case "categories":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Categorias";
					case EN:
						return "Categories";
					default:
						return word;
				}
			case "option":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Opción";
					case EN:
						return "Option";
					default:
						return word;
				}
			case "options":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Opciones";
					case EN:
						return "Options";
					default:
						return word;
				}
			case "parameter":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Parámetro";
					case EN:
						return "Parameter";
					default:
						return word;
				}
			case "parameters":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Parámetros";
					case EN:
						return "Parameters";
					default:
						return word;
				}
			case "guild":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Servidor";
					case EN:
						return "Guild";
					default:
						return word;
				}
			case "guilds":
				switch(getLanguageParent(lang)) {
					case ES:
						return "Servidores";
					case EN:
						return "Guilds";
					default:
						return word;
				}
			default:
				return word;
		}
	}
	public static String specialWordsUppercased(Language lang, String word) {
		return specialWords(lang,word).toUpperCase();
	}
	public static String botOffMessage(Language lang) {
		switch(getLanguageParent(lang)) {
			case ES:
				return "Este bot se encuentra fuera de servicio.";
			case EN:
				return "This bot is out of order.";
			default:
				return "";
		}
	}
	public static String commandDisabledMessage(Language lang) {
		switch(getLanguageParent(lang)) {
			case ES:
				return "Este comando no se encuentra habilitado. :pensive:";
			case EN:
				return "This command is not enabled. :pensive:";
			default:
				return "";
		}
	}
}
