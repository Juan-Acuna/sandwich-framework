package com.jaxsandwich.framework.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.core.util.LanguageHandler;

public abstract class CommandBase {
	protected String id = null;
	protected HashMap<Language, String> name = null;
	protected HashMap<Language, String[]> alias = null;
	protected boolean enabled;
	protected boolean visible;
	protected boolean nsfw=false;
	protected boolean helpCommand=false;
	protected ArrayList<ModelOption> options = null;
	protected Method action = null;
	CommandBase(){ }
	public CommandBase(String id) {
		this.id=id;
		name = new HashMap<Language, String>();
		alias = new HashMap<Language, String[]>();
		options = new ArrayList<ModelOption>();
	}
	public String getId() {
		return id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public ArrayList<ModelOption> getOptions() {
		return options;
	}
	public void addOption(ModelOption option) {
		this.options.add(option);
	}
	public Method getAction() {
		return action;
	}
	public void setAction(Method source) {
		this.action = source;
	}
	public boolean isNsfw() {
		return nsfw;
	}
	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}
	public boolean isHelpCommand() {
		return helpCommand;
	}
	public void setHelpCommand(boolean helpCommand) {
		this.helpCommand = helpCommand;
	}
}
