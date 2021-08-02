package com.jaxsandwich.framework.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.framework.core.util.Language;
/**
 * [ES] Permite organizar textos dentro de la aplicación.<br>
 * [ES] Allows to organize the texts inside the application.
 * @author Juancho
 * @version 1.2
 */
public class Values {
	/**
	 * [ES] Contenedor de valores.<br>
	 * [EN] Container of values.
	 */
	protected static Map<String, Map<Language, String>> values = (Map<String, Map<Language, String>>) Collections.synchronizedMap(new HashMap<String, Map<Language, String>>());
	/**
	 * [ES] Indica si ya se cargaron los valores.<br>
	 * [EN] Indicates if the values are already loaded.
	 */
	private static boolean initialized = false;
	/**
	 * [ES] Devuelve el valor correspondiente al identificador según el idioma.<br>
	 * [EN] Returns the corresponding value for the identifier by the language.
	 */
	public static String value(String id, Language lang) {
		Map<Language, String> s = values.get(id);
		if(s==null)
			return "";
		return ""+s.get(lang);
	}
	/**
	 * [ES] Devuelve el valor correspondiente al identificador según el idioma formateando los argumentos.<br>
	 * [EN] Returns the corresponding value for the identifier by the language formatting the arguments.
	 */
	public static String formatedValue(String id, Language lang, Object...args) {
		Map<Language, String> s = values.get(id);
		if(s==null)
			return "";
		return String.format(s.get(lang)+"",args);
	}
	/**
	 * [ES] Devuelve el valor correspondiente al identificador según el idioma formateado a la cadena ingresada.<br>
	 * [EN] Returns the corresponding value for the identifier by the language formatting it to the input String.
	 */
	public static String valueForFormat(String id, Language lang, String format) {
		Map<Language, String> s = values.get(id);
		if(s==null)
			return String.format(format,"<?>");
		return String.format(format,s.get(lang)+"");
	}
	/**
	 * [ES] Inicializa los valores.<br>
	 * [EN] Initialices the values.
	 */
	protected static void initialize(Map<String, Map<Language, String>> v) {
		if(Values.initialized)
			return;
		for(String s : v.keySet()) {
			Map<Language, String> m = Collections.synchronizedMap(new HashMap<Language, String>());
			for(Language l : v.get(s).keySet()) {
				m.put(l, v.get(s).get(l));
			}
			values.put(s, m);
		}
		Values.initialized=true;
	}
}
