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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.development.InDevelopment;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.actionable.ActionableType;
import com.jaxsandwich.sandwichcord.models.actionable.IActionable;
import com.jaxsandwich.sandwichcord.models.packets.CommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.SlashCommandPacket;


/**
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
@NotDocumented
public class SlashCommandObject implements Comparable<SlashCommandObject>, IActionable<ReplyablePacket<?>>{
	private static Map<String, SlashCommandObject> cont = Collections.synchronizedMap(new HashMap<String, SlashCommandObject>());
	private boolean hasSubCommands = false;
	private boolean hasSubGroups = false;
	private String name;
	private String desc;
	private SlashSubGroup[] subGroups = null;
	private SlashSubCommand[] subCommands = null;
	private Map<String, Method> actions;
	private OptionObject[] options = null;
	private String[] guilds = null;
	@InDevelopment
	private CommandMode commandMode;
	public static final void compute(SlashCommandObject command) {
		cont.put(command.name.toLowerCase(), command);
	}
	public static final SlashCommandObject find(String name) {
		return cont.get(name.toLowerCase());
	}
	public static final ArrayList<SlashCommandObject> getAsList() {
		ArrayList<SlashCommandObject> l = new ArrayList<SlashCommandObject>(cont.values());
		Collections.sort(l);
		return l;
	}
	public static final int getCommandCount() {
		return cont.size();
	}
	public SlashCommandObject(String name, String desc, SlashSubCommand subcommand,CommandMode commandMode, String[] guilds) {
		this.name = name.toLowerCase();
		this.desc = desc.toLowerCase();
		this.actions = Collections.synchronizedMap(new HashMap<String, Method>());
		this.actions.put(name.toLowerCase(), subcommand.getAction());
		this.hasSubCommands = false;
		this.options=subcommand.getOptions();
		this.commandMode=commandMode;
		this.guilds=guilds;
	}
	public SlashCommandObject(String name, String desc,CommandMode commandMode, String[] guilds, Collection<? extends SlashSubObject> sub) {
		this.name = name.toLowerCase();
		this.desc = desc.toLowerCase();
		this.actions = Collections.synchronizedMap(new HashMap<String, Method>());
		this.commandMode=commandMode;
		this.guilds=guilds;
		boolean b = false;
		for(SlashSubObject s : sub) {
			if(s instanceof SlashSubGroup)
				b =true;
			break;
		}
		if(b) {
			this.subGroups = new SlashSubGroup[sub.size()];
			sub.toArray(this.subGroups);
			for(SlashSubGroup s : this.subGroups) {
				s.setCommand(this);
				for(SlashSubCommand ssc : s.getSubCommands()) {
					this.actions.put(ssc.getPath(), ssc.getAction());
				}
			}
			this.hasSubGroups = true;
		}else {
			this.subCommands = new SlashSubCommand[sub.size()];
			sub.toArray(this.subCommands);
			for(SlashSubCommand s : this.subCommands) {
				s.setCommand(this);
				this.actions.put(s.getPath(), s.getAction());
			}
		}
		this.hasSubCommands = true;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public SlashSubGroup[] getSubGroups() {
		return subGroups;
	}
	public SlashSubCommand[] getSubCommands() {
		return subCommands;
	}
	public OptionObject[] getOptions() {
		return options;
	}
	public boolean hasSubCommands() {
		return hasSubCommands;
	}
	public boolean hasSubGroups() {
		return hasSubGroups;
	}
	public CommandMode getCommandMode() {
		return commandMode;
	}
	public String[] getGuilds() {
		return guilds;
	}
	@Override
	public int compareTo(SlashCommandObject o) {
		return this.name.compareTo(o.name);
	}
	@Override
	public String getId() {
		return this.name.toLowerCase();
	}
	@Override
	public void execute(ReplyablePacket<?> packet) throws Exception {
		Method a = actions.get(packet.getCommandPath());
		if(a==null)
			return;
		if(packet instanceof SlashCommandPacket) {
			a.invoke(null, (SlashCommandPacket)packet);
		}else if(packet instanceof CommandPacket){
			a.invoke(null, (CommandPacket)packet);
		}else if(packet instanceof ResponseCommandPacket){
			a.invoke(null, (ResponseCommandPacket)packet);
		}else {
			a.invoke(null, packet);
		}
	}
	@Override
	public ActionableType getActionableType() {
		return ActionableType.SLASH_COMMAND;
	}
}
