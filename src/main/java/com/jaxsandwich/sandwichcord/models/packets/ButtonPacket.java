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

package com.jaxsandwich.sandwichcord.models.packets;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager.ResponseListener;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
/**
 * [ES] <br>
 * [EN] 
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public class ButtonPacket extends ComponentPacket<ButtonClickEvent>{
	public ButtonPacket(Bot bot, GuildConfig config, ButtonClickEvent event, MessageChannel channel, String authorId) {
		super(bot, config, event, channel, authorId);
	}
	@Override
	@NotDocumented
	public ResponseListener<ButtonClickEvent> waitForResponse(String responseCmdName, String[] spectedValues, int maxSeg, int maxMsg, Object...args) throws Exception {
		return this.responseCommandManager.waitForResponse(responseCmdName, this.event, spectedValues, maxSeg, maxMsg, args);
	}
	@Override
	public Member getMember() {
		return this.event.getMember();
	}
	@Override
	public User getUser() {
		return event.getUser();
	}
	@Override
	public Guild getGuild() {
		return this.event.getGuild();
	}
	@Override
	public Member getBotAsMember() {
		return event.getGuild().getMember(bot.getSelfUser());
	}
}
