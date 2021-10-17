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

import java.util.List;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager.ResponseListener;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.OptionInput;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
/**
 * [ES] <br>
 * [EN] 
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public class SlashCommandPacket extends ReplyablePacket<SlashCommandEvent>{
	/**
	 * [ES] Constructor de SlashCommandPacket.<br>
	 * [EN] Constructor of SlashCommandPacket.
	 */
	public SlashCommandPacket(Bot bot, SlashCommandEvent event) {
		super(bot, bot.getGuildConfigManager().getConfig(event.getGuild()), event, event.getChannel(), event.getMember().getId());
		List<OptionMapping> os = event.getOptions();
		this.options = new OptionInput[os.size()];
		for(int i = 0; i < os.size();i++) {
			this.options[i] = new OptionInput(os.get(i));
		}
		this.messageContent = event.getCommandString();
	}
	/**
	 * [ES] Devuelve el {@link SlashCommandEvent} asociado a este SlashCommandPacket.<br>
	 * [EN] Returns the {@link SlashCommandEvent} associated to this SlashCommandPacket.
	 */
	public SlashCommandEvent getEvent() {
		return super.getEvent();
	}
	@Override
	/**
	 * [ES] Devuelve verdadero si el comando fue llamado desde un canal 'WebHook'. Los comandos slash cno pueden ser enviados en WebHooks.<br>
	 * [EN] Returns true if the command was called from a 'WebHook' channel. The slash commands can´t be sent by WebHooks.
	 */
	public boolean isWebhookMessage() {
		return false;
	}
	@Override
	public String getCommandPath() {
		return this.event.getCommandPath();
	}
	@Override
	public boolean canDoEphemeralReplies() {
		return true;
	}
	@Override
	@NotDocumented
	public ResponseListener<SlashCommandEvent> waitForResponse(String responseCmdName, String[] spectedValues, int maxSeg, int maxMsg, Object...args) throws Exception {
		return this.responseCommandManager.waitForResponse(responseCmdName, this.event, spectedValues, maxSeg, maxMsg, args);
	}
	@Override
	public Member getMember() {
		return event.getMember();
	}
	@Override
	public User getUser() {
		return event.getUser();
	}
	@Override
	public Guild getGuild() {
		return event.getGuild();
	}
	@Override
	public boolean tryDeleteMessage() {
		return false;
	}
	@Override
	public Member getBotAsMember() {
		return event.getGuild().getMember(bot.getSelfUser());
	}
	@Override
	public PacketAction<ReplyAction> deferReply() {
		return new PacketAction<ReplyAction>(this.event.deferReply());
	}
	@Override
	public PacketAction<ReplyAction> deferReply(boolean ephemeral) {
		return new PacketAction<ReplyAction>(this.event.deferReply());
	}
	@Override
	public PacketAction<ReplyAction> reply(String content) {
		return new PacketAction<ReplyAction>(this.event.reply(content));
	}
	@Override
	public PacketAction<ReplyAction> reply(Message message) {
		return new PacketAction<ReplyAction>(this.event.reply(message));
	}
	@Override
	public PacketAction<ReplyAction> deferReply(String content) {
		return new PacketAction<ReplyAction>(this.event.deferReply().setContent(content));
	}
	@Override
	public PacketAction<ReplyAction> deferReply(String content, boolean ephemeral) {
		return new PacketAction<ReplyAction>(this.event.deferReply(ephemeral).setContent(content));
	}
}
