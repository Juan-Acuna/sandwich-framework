package xyz.sandwichframework.core;

import xyz.sandwichframework.core.util.Language;
/**
 * Utilidades de idiomas.
 * Language utilities.
 * @author Juancho
 * @version 1.0
 */
public class LanguageHandler {
	public static Language getLanguageParent(Language lang) {
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
		
		expected = getLanguageParent(expected);
		for(Language lang : availables) {
			if(getLanguageParent(lang)==expected) {
				return lang;
			}
		}
		return availables[0];
	}
	public static String DescriptionNotFound(Language lang) {
		switch(getLanguageParent(lang)) {
			case ES:
				return "No se encontró descripción";
			case EN:
				return "Description not found";
			default:
				return "";
		}
	}
	public static String helpHint(Language lang) {
		switch(getLanguageParent(lang)) {
			case ES:
				return "\nPara saber más sobre este comando, escriba %s%s %sayuda.";
			case EN:
				return "\nTo know more about this command, type %s%s %shelp.";
			default:
				return "";
		}
	}
	public static String notAvailable(Language lang) {
		switch(getLanguageParent(lang)) {
			case ES:
				return "No disponible";
			case EN:
				return "Not available";
			default:
				return "";
		}
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
