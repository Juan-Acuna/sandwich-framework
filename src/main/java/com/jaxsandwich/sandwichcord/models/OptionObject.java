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

import java.util.HashMap;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.core.util.LanguageHandler;
import net.dv8tion.jda.api.interactions.commands.OptionType;
/**
 * Representa una Opción.
 * Represents an Option.
 * @author Juancho
 * @version 2.0
 * @since 0.0.1
 */
public class OptionObject implements Comparable<OptionObject>{
	private String id;
	private HashMap<Language, String> name;
	private HashMap<Language, String> desc;
	private HashMap<Language, String[]> alias;
	private boolean enabled = true;
	private boolean visible;
	private boolean noStandar;
	private boolean required;
	private OptionType type;
	public OptionObject(Language lang, String id, String desc, String[] alias, boolean enabled, boolean visible, boolean nostandar, boolean required, OptionType type) {
		this.name = new HashMap<Language, String>();
		this.desc= new HashMap<Language, String>();
		this.alias = new HashMap<Language, String[]>();
		this.id=id.toLowerCase();
		this.name.put(lang, id);
		this.desc.put(lang, desc);
		this.alias.put(lang, alias);
		this.enabled = enabled;
		this.visible=visible;
		this.noStandar=nostandar;
		this.required=required;
		this.type=type;
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
	public boolean isNoStandar() {
		return noStandar;
	}
	public void setNoStandar(boolean noStandar) {
		this.noStandar = noStandar;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public OptionType getType() {
		return type;
	}
	public void setType(OptionType type) {
		this.type = type;
	}
	@Override
	public int compareTo(OptionObject o) {
		return id.compareTo(o.id) + (required?50:-50);
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
		OptionObject other = (OptionObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
