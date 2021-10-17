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

import com.jaxsandwich.sandwichcord.development.NotDocumented;

/**
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
@NotDocumented
public class SlashSubCommand extends SlashSubObject{
	private String name;
	private String description;
	private OptionObject[] options;
	private Method action;
	private SlashCommandObject command;
	private SlashSubGroup subGroup;
	public SlashSubCommand(String name, String description, OptionObject[] options, Method action) {
		this.name = name.toLowerCase();
		this.description = description.toLowerCase();
		this.options = options;
		this.action = action;
	}
	public Method getAction() {
		return action;
	}
	public SlashCommandObject getCommand() {
		return command;
	}
	protected void setCommand(SlashCommandObject command) {
		this.command = command;
	}
	public SlashSubGroup getSubGroup() {
		return subGroup;
	}
	protected void setSubGroup(SlashSubGroup subGroup) {
		this.subGroup = subGroup;
		this.setCommand(subGroup.getCommand());
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public OptionObject[] getOptions() {
		return options;
	}
	public String getPath() {
		return ((command!=null?command.getName().toLowerCase():"[?]") + "/" 
				+ (subGroup!=null?subGroup.getName().toLowerCase()+"/":"") 
				+ name.toLowerCase()).toLowerCase();
	}
	@Override
	public String getPattern() {
		return (command!=null?">":"[?]") + "/" + (subGroup!=null?"%":"[?]") + "/#"
				+ ((options!=null && options.length>0)?(options[0].isRequired()?"=!":"=$")+"["+options.length+"]":"");
	}
}
