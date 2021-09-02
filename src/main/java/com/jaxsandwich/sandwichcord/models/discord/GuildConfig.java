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

package com.jaxsandwich.sandwichcord.models.discord;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CommandObject;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
/**
 * [ES] Representa la configuración de un servidor de Discord.
 * [EN] Represents a Discord's guildConfig configuration.
 * @author Juancho
 * @version 1.2
 */
public class GuildConfig {
	protected long id;
	protected Language language;
	protected boolean actuallyJoined = true;
	protected String customPrefix = null;
	protected String customOptionsPrefix = null;
	protected Bot bot;
	protected Map<String, Boolean> allowedCommands = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedCategories = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedRoles = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedMembers = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedChannels = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected boolean defaultDenyCommands = false;
	protected boolean defaultDenyCategories = false;
	protected boolean defaultDenyRoles = false;
	protected boolean defaultDenyMembers = false;
	protected boolean defaultDenyChannels = false;
	public GuildConfig(Bot bot) {
		this.bot=bot;
		this.language=bot.getDefaultLanguage();
	}
	public GuildConfig(Guild guild, Bot bot) {
		id=guild.getIdLong();
		actuallyJoined=true;
		this.language = Language.EN;
		this.bot=bot;
	}
	public GuildConfig(Guild guild, Language language, Bot bot) {
		id=guild.getIdLong();
		actuallyJoined=true;
		this.language = language;
		this.bot=bot;
	}
	public static boolean canRunThisCommand(GuildConfig guild, CommandObject command, MessageChannel channel, User author) {
		if(guild==null) {
			return !command.getCategory().isNsfw();
		}
		Member m = ((TextChannel)channel).getGuild().getMember(author);
		boolean r = guild.isCommandAllowed(command.getId()) 
				&& guild.isCategoryAllowed(command.getCategory().getId()) 
				&& guild.isChannelAllowed(channel.getId())
				&& guild.isMemberAllowed(m.getId());
		List<Role> l = m.getRoles();
		if(l.size()>0) {
			r = r && guild.isRoleAllowed(l.get(0).getId());
		}else {
			r = r && !guild.defaultDenyRoles;
		}
		return r;
	}
	public long getId() {
		return id;
	}
	public String getIdAsString() {
		return id+"";
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public boolean isJoined() {
		return actuallyJoined;
	}
	public void setJoined(boolean joined) {
		this.actuallyJoined = joined;
	}
	public Guild getReferencedGuild() {
		return this.bot.getJDA().getGuildById(id);
	}
	public void setId(long id) {
		this.id = id;
	}
	public Map<String, Boolean> getAllowedCommands() {
		return allowedCommands;
	}
	public void setAllowedCommands(Map<String, Boolean> allowedCommands) {
		this.allowedCommands = allowedCommands;
	}
	public Map<String, Boolean> getAllowedCategories() {
		return allowedCategories;
	}
	public void setAllowedCategories(Map<String, Boolean> allowedCategories) {
		this.allowedCategories = allowedCategories;
	}
	public Map<String, Boolean> getAllowedRoles() {
		return allowedRoles;
	}
	public void setAllowedRoles(Map<String, Boolean> allowedRoles) {
		this.allowedRoles = allowedRoles;
	}
	public Map<String, Boolean> getAllowedMembers() {
		return allowedMembers;
	}
	public void setAllowedMembers(Map<String, Boolean> allowedMembers) {
		this.allowedMembers = allowedMembers;
	}
	public Map<String, Boolean> getAllowedChannels() {
		return allowedChannels;
	}
	public void setAllowedChannels(Map<String, Boolean> allowedChannels) {
		this.allowedChannels = allowedChannels;
	}
	public boolean isDefaultDenyCommands() {
		return defaultDenyCommands;
	}
	public void setDefaultDenyCommands(boolean defaultDenyCommands) {
		this.defaultDenyCommands = defaultDenyCommands;
	}
	public boolean isDefaultDenyCategories() {
		return defaultDenyCategories;
	}
	public void setDefaultDenyCategories(boolean defaultDenyCategories) {
		this.defaultDenyCategories = defaultDenyCategories;
	}
	public boolean isDefaultDenyRoles() {
		return defaultDenyRoles;
	}
	public void setDefaultDenyRoles(boolean defaultDenyRoles) {
		this.defaultDenyRoles = defaultDenyRoles;
	}
	public boolean isDefaultDenyMembers() {
		return defaultDenyMembers;
	}
	public void setDefaultDenyMembers(boolean defaultDenyMembers) {
		this.defaultDenyMembers = defaultDenyMembers;
	}
	public boolean isDefaultDenyChannels() {
		return defaultDenyChannels;
	}
	public void setDefaultDenyChannels(boolean defaultDenyChannels) {
		this.defaultDenyChannels = defaultDenyChannels;
	}
	public boolean isCommandAllowed(String cmdId) {
		Boolean b = allowedCommands.get(cmdId.toLowerCase());
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyCommands;
	}
	public boolean isCategoryAllowed(String categoryId) {
		Boolean b = allowedCategories.get(categoryId.toLowerCase());
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyCategories;
	}
	public boolean isRoleAllowed(String roleId) {
		Boolean b = allowedRoles.get(roleId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyRoles;
	}
	public boolean isMemberAllowed(String memberId) {
		Boolean b = allowedMembers.get(memberId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyMembers;
	}
	public boolean isChannelAllowed(String channelId) {
		Boolean b = allowedChannels.get(channelId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyChannels;
	}
	public void setAllowedCommand(String cmdId, boolean allowed) {
		allowedCommands.put(cmdId.toLowerCase(), allowed);
	}
	public void setAllowedCategory(String categoryId, boolean allowed) {
		allowedCategories.put(categoryId.toLowerCase(), allowed);
	}
	public void setAllowedRole(String roleId, boolean allowed) {
		allowedRoles.put(roleId, allowed);
	}
	public void setAllowedMember(String memberId, boolean allowed) {
		allowedMembers.put(memberId, allowed);
	}
	public void setAllowedChannel(String channelId, boolean allowed) {
		allowedChannels.put(channelId, allowed);
	}
	public boolean isActuallyJoined() {
		return actuallyJoined;
	}
	public void setActuallyJoined(boolean actuallyJoined) {
		this.actuallyJoined = actuallyJoined;
	}
	public String getCustomPrefix() {
		return customPrefix;
	}
	public void setCustomPrefix(String customPrefix) {
		this.customPrefix = customPrefix;
	}
	public String getCustomOptionsPrefix() {
		return customOptionsPrefix;
	}
	public void setCustomOptionsPrefix(String customOptionsPrefix) {
		this.customOptionsPrefix = customOptionsPrefix;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		GuildConfig other = (GuildConfig) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
