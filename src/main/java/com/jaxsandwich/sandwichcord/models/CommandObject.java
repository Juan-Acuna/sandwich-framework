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
import com.jaxsandwich.sandwichcord.models.actionable.ActionableType;
import com.jaxsandwich.sandwichcord.models.actionable.IActionable;
import com.jaxsandwich.sandwichcord.models.packets.CommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.SlashCommandPacket;
/**
 * Representa un Comando clásico.
 * Represents a clasic Command.
 * @author Juancho
 * @version 2.1
 * @since 0.0.1
 */
public class CommandObject extends CommandBase implements Comparable<CommandObject>, IActionable<ReplyablePacket<?>>{
	private static Map<String, CommandObject> cont = Collections.synchronizedMap(new HashMap<String, CommandObject>());
	protected HashMap<Language, String> desc = null;
	protected CategoryObject category = null;
	private CommandMode commandMode;
	
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
	public CommandObject(Language lang, String id, CategoryObject category, Method source, CommandMode commandMode) {
		super(id);
		this.name=new HashMap<Language, String>();
		this.desc=new HashMap<Language, String>();
		this.alias=new HashMap<Language, String[]>();
		this.name.put(lang, id);
		this.category = category;
		this.action = source;
		options = new ArrayList<OptionObject>();
		category.addCommand(this);
		this.commandMode=commandMode;
		this.path = this.category.getId() + "/" + this.id;
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
	public CommandMode getCommandMode() {
		return commandMode;
	}
	public void sortOptions() {
		Collections.sort(options);
	}
	@Override
	public int compareTo(CommandObject arg0) {
		return id.compareTo(arg0.id);
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
	@Override
	public void execute(ReplyablePacket<?> packet) throws Exception{
		if(packet instanceof CommandPacket) {
			this.action.invoke(null, (CommandPacket)packet);
		}else if(packet instanceof SlashCommandPacket){
			this.action.invoke(null, (SlashCommandPacket)packet);
		}else if(packet instanceof ResponseCommandPacket){
			this.action.invoke(null, (ResponseCommandPacket)packet);
		}else {
			this.action.invoke(null, packet);
		}
	}
	@Override
	public ActionableType getActionableType() {
		return ActionableType.TEXT_COMMAND;
	}
}
