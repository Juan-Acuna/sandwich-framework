/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright 2021 Juan Acuña                                                   *
 *                                                                             *
 * Licensed under the Apache License, Version 2.0 (the "License");             *
 * you may not use this file except in compliance with the License.            *
 * You may obtain a copy of the License at                                     *
 *                                                                             *
 *     http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                             *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.                                              *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.jaxsandwich.sandwichcord.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.core.util.LanguageHandler;
/**
 * Representa una Categoría.
 * Represents a Category.
 * @author Juancho
 * @version 2.0
 * @since 0.0.1
 */
public class CategoryObject implements Comparable<CategoryObject>{
	private static Map<String, CategoryObject> cont = Collections.synchronizedMap(new HashMap<String, CategoryObject>());
	private String id;
	private HashMap<Language,String> name;
	private HashMap<Language,String> desc;
	private boolean nsfw;
	private boolean visible;
	private boolean enabled;
	private boolean isSpecial;
	private CommandMode commandMode;
	private String[] guilds = null;
	private ArrayList<CommandObject> commands;
	

	public static void compute(CategoryObject cat) {
		cont.put(cat.id.toLowerCase(), cat);
	}
	public static CategoryObject find(String id) {
		return cont.get(id.toLowerCase());
	}
	
	public static ArrayList<CategoryObject> getAsList() {
		ArrayList<CategoryObject> l = new ArrayList<CategoryObject>(cont.values());
		Collections.sort(l);
		return l;
	}
	public static int getCommandCount() {
		return cont.size();
	}
	public CategoryObject(Language lang, String id, CommandMode commandMode, String[] guilds){
		this.name=new HashMap<Language,String>();
		this.desc=new HashMap<Language,String>();
		this.id=id.toLowerCase();
		this.name.put(lang, id);
		commands = new ArrayList<CommandObject>();
		this.commandMode=commandMode;
		this.guilds=guilds;
	}
	public CategoryObject(Language lang, String id, String desc, CommandMode commandMode, String[] guilds) {
		this.name=new HashMap<Language,String>();
		this.desc=new HashMap<Language,String>();
		this.id=id.toLowerCase();
		this.name.put(lang, id);
		this.desc.put(lang, desc);
		commands = new ArrayList<CommandObject>();
		this.commandMode=commandMode;
		this.guilds=guilds;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public ArrayList<CommandObject> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<CommandObject> commands) {
		this.commands = commands;
	}
	public void addCommand(CommandObject command) {
		this.commands.add(command);
	}
	public boolean isSpecial() {
		return isSpecial;
	}
	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	@Override
	public int compareTo(CategoryObject arg0) {
		return id.compareTo(arg0.id);
	}
	public void sortCommands() {
		Collections.sort(commands);
	}
	public CommandMode getCommandMode() {
		return commandMode;
	}
	public String[] getGuilds() {
		return guilds;
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
		CategoryObject other = (CategoryObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
