package xyz.sandwichframework.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.LanguageHandler;
/**
 * Representa un Comando.
 * Represents a Command.
 * @author Juancho
 * @version 1.1
 */
public class ModelCommand extends CommandBase implements Comparable<ModelCommand>{
	private static Map<String, ModelCommand> cont = Collections.synchronizedMap(new HashMap<String, ModelCommand>());
	protected HashMap<Language, String> desc = null;
	protected HashMap<Language, String> parameter = null;
	protected HashMap<Language, String> parameterDesc = null;
	protected ModelCategory category = null;
	
	public static final void compute(ModelCommand command) {
		cont.put(command.id.toLowerCase(), command);
	}
	public static final ModelCommand find(String id) {
		return cont.get(id.toLowerCase());
	}
	
	public static final ArrayList<ModelCommand> getAsList() {
		ArrayList<ModelCommand> l = new ArrayList<ModelCommand>(cont.values());
		Collections.sort(l);
		return l;
	}
	public static final int getCommandCount() {
		return cont.size();
	}
	public ModelCommand(Language lang, String id, ModelCategory category, Method source) {
		super(id);
		this.name=new HashMap<Language, String>();
		this.desc=new HashMap<Language, String>();
		this.alias=new HashMap<Language, String[]>();
		this.parameter = new HashMap<Language, String>();
		this.parameterDesc = new HashMap<Language, String>();
		this.name.put(lang, id);
		this.category = category;
		this.action = source;
		options = new ArrayList<ModelOption>();
		category.addCommand(this);
	}
	public String getDesc(Language lang) {
		if(desc.containsKey(lang)) {
			return desc.get(lang);
		}
		if(desc.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return desc.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[desc.size()];
		desc.keySet().toArray(langs);
		return desc.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	public void setDesc(Language lang, String desc) {
		this.desc.put(lang, desc);
	}
	public String getParameter(Language lang) {
		if(parameter.containsKey(lang)) {
			return parameter.get(lang);
		}
		if(parameter.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return parameter.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[parameter.size()];
		parameter.keySet().toArray(langs);
		return parameter.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	public void setParameter(Language lang, String parameter) {
		this.parameter.put(lang, parameter);
	}
	public String getParameterDesc(Language lang) {
		if(parameterDesc.containsKey(lang)) {
			return parameterDesc.get(lang);
		}
		if(parameterDesc.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return parameterDesc.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[parameterDesc.size()];
		parameterDesc.keySet().toArray(langs);
		return parameterDesc.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	public void setParameterDesc(Language lang, String parameterDesc) {
		this.parameterDesc.put(lang, parameterDesc);
	}
	public ModelCategory getCategory() {
		return category;
	}
	public void setCategory(ModelCategory category) {
		this.category = category;
	}
	@Override
	public int compareTo(ModelCommand arg0) {
		return id.compareTo(arg0.id);
	}
	public void sortOptions() {
		Collections.sort(options);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelCommand other = (ModelCommand) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
