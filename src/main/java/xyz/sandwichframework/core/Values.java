package xyz.sandwichframework.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import xyz.sandwichframework.core.util.Language;
/**
 * Permite organizar textos dentro de la aplicación.
 * Allows to organize the texts inside the application.
 * @author Juancho
 * @version 1.0
 */
public class Values {
	protected static Map<String, Map<Language, String>> values = (Map<String, Map<Language, String>>) Collections.synchronizedMap(new HashMap<String, Map<Language, String>>());
	private static boolean initialized = false;
	
	public static String value(String id, Language lang) {
		Map<Language, String> s = values.get(id);
		if(s==null)
			return "";
		return ""+s.get(lang);
	}
	
	public static String formatedValue(String id, Language lang, Object...args) {
		Map<Language, String> s = values.get(id);
		if(s==null)
			return "";
		return String.format(s.get(lang)+"",args);
	}
	
	public static String valueForFormat(String id, Language lang, String format) {
		Map<Language, String> s = values.get(id);
		if(s==null)
			return String.format(format,"<?>");
		return String.format(format,s.get(lang)+"");
	}
	
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
