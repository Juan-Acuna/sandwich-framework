package xyz.sandwichframework.models;

import java.util.HashMap;

import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.LanguageHandler;
/**
 * Representa una Opción.
 * Represents an Option.
 * @author Juancho
 * @version 1.0
 */
public class ModelOption implements Comparable<ModelOption>{
	private String id;
	private HashMap<Language, String> name;
	private HashMap<Language, String> desc;
	private HashMap<Language, String[]> alias;
	private boolean enabled = true;
	private boolean visible;
	public ModelOption(Language lang, String id, String desc, String[] alias, boolean enabled, boolean visible) {
		this.name = new HashMap<Language, String>();
		this.desc= new HashMap<Language, String>();
		this.alias = new HashMap<Language, String[]>();
		this.id=id;
		this.name.put(lang, id);
		this.desc.put(lang, desc);
		this.alias.put(lang, alias);
		this.enabled = enabled;
		this.visible=visible;
	}
	public String getId() {
		return id;
	}
	public String getName(Language lang) {
		if(name.containsKey(lang)) {
			return name.get(lang);
		}
		if(name.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return name.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[name.size()];
		name.keySet().toArray(langs);
		return name.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	public void setName(Language lang, String name) {
		this.name.put(lang, name);
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
	public String[] getAlias(Language lang) {
		if(alias.containsKey(lang)) {
			return alias.get(lang);
		}
		if(alias.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return alias.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[alias.size()];
		alias.keySet().toArray(langs);
		return alias.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	public void setAlias(Language lang, String[] alias) {
		this.alias.put(lang, alias);
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Override
	public int compareTo(ModelOption o) {
		return id.compareTo(o.id);
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
		ModelOption other = (ModelOption) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
