package xyz.sandwichframework.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.LanguageHandler;
/**
 * Representa una Categoría.
 * Represents a Category.
 * @author Juancho
 * @version 1.0
 */
public class ModelCategory implements Comparable<ModelCategory>{
	private String id;
	private HashMap<Language,String> name;
	private HashMap<Language,String> desc;
	private boolean nsfw;
	private boolean visible;
	private boolean isSpecial;
	private ArrayList<ModelCommand> commands;
	public ModelCategory(Language lang, String id){
		this.name=new HashMap<Language,String>();
		this.desc=new HashMap<Language,String>();
		this.id=id;
		this.name.put(lang, id);
		commands = new ArrayList<ModelCommand>();
	}
	public ModelCategory(Language lang, String id, String desc) {
		this.name=new HashMap<Language,String>();
		this.desc=new HashMap<Language,String>();
		this.id=id;
		this.name.put(lang, id);
		this.desc.put(lang, desc);
		commands = new ArrayList<ModelCommand>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public boolean isNsfw() {
		return nsfw;
	}
	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public ArrayList<ModelCommand> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<ModelCommand> commands) {
		this.commands = commands;
	}
	public void addCommand(ModelCommand command) {
		this.commands.add(command);
	}
	public boolean isSpecial() {
		return isSpecial;
	}
	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	@Override
	public int compareTo(ModelCategory arg0) {
		return id.compareTo(arg0.id);
	}
	public void sortCommands() {
		Collections.sort(commands);
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
		ModelCategory other = (ModelCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
