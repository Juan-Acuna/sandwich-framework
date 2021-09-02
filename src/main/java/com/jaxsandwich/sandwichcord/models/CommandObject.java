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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.core.util.LanguageHandler;
/**
 * Representa un Comando.
 * Represents a Command.
 * @author Juancho
 * @version 2.0
 */
public class CommandObject extends CommandBase implements Comparable<CommandObject>{
	private static Map<String, CommandObject> cont = Collections.synchronizedMap(new HashMap<String, CommandObject>());
	protected HashMap<Language, String> desc = null;
	protected CategoryObject category = null;
	
	public static final void compute(CommandObject command) {
		cont.put(command.id.toLowerCase(), command);
	}
	public static final CommandObject find(String id) {
		return cont.get(id.toLowerCase());
	}
	
	public static final ArrayList<CommandObject> getAsList() {
		ArrayList<CommandObject> l = new ArrayList<CommandObject>(cont.values());
		Collections.sort(l);
		return l;
	}
	public static final int getCommandCount() {
		return cont.size();
	}
	public CommandObject(Language lang, String id, CategoryObject category, Method source) {
		super(id);
		this.name=new HashMap<Language, String>();
		this.desc=new HashMap<Language, String>();
		this.alias=new HashMap<Language, String[]>();
		this.name.put(lang, id);
		this.category = category;
		this.action = source;
		options = new ArrayList<OptionObject>();
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
	public CategoryObject getCategory() {
		return category;
	}
	public void setCategory(CategoryObject category) {
		this.category = category;
	}
	@Override
	public int compareTo(CommandObject arg0) {
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
		CommandObject other = (CommandObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
