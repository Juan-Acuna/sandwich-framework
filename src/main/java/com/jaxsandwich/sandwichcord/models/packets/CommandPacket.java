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
import com.jaxsandwich.sandwichcord.models.CommandObject;
import com.jaxsandwich.sandwichcord.models.OptionInput;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
/**
 * [ES] Paquete con todo el contenido necesario para un comando.
 * [EN] Packet with all the required content for a command.
 * @author Juan Acuña
 * @version 2.0
 */
public class CommandPacket extends ReplyablePacket<MessageReceivedEvent>{
	protected String commandPath = null;
	/**
	 * [ES] Indica si el mensaje fue recibido en un canal 'WebHook'<br>
	 * [EN] Indicates if the message was received from a 'WebHook' channel.
	 */
	protected boolean isWebhookMessage = false;
	/**
	 * [ES] Constructor de CommandPacket.<br>
	 * [EN] Constructor of CommandPacket.
	 */
	protected Message message = null;
	/**
	 * [ES] Constructor de CommandPacket.<br>
	 * [EN] Constructor of CommandPacket.
	 */
	public CommandPacket(Bot bot, OptionInput[] parameters, MessageReceivedEvent event, String commandPath) {
		super(bot, bot.getGuildConfigManager().getConfig(event.getGuild()), event, event.getChannel(), event.getAuthor().getId());
		this.options=parameters;
		this.isWebhookMessage=event.isWebhookMessage();
		this.message=event.getMessage();
		this.messageContent=this.message.getContentRaw();
		this.commandPath=commandPath;
	}
	/**
	 * [ES] Constructor de CommandPacket.<br>
	 * [EN] Constructor of CommandPacket.
	 */
	public CommandPacket(Bot bot, OptionInput[] parameters, MessageReceivedEvent event, CommandObject command) {
		super(bot, bot.getGuildConfigManager().getConfig(event.getGuild()), event, event.getChannel(), event.getAuthor().getId());
		this.options=parameters;
		this.isWebhookMessage=event.isWebhookMessage();
		this.message=event.getMessage();
		this.messageContent=this.message.getContentRaw();
		this.commandPath=command.getCommandPath();
	}
	@NotDocumented
	public List<User> getMentionedUsers() {
		return event.getMessage().getMentionedUsers();
	}
	@NotDocumented
	public List<TextChannel> getMentionedChannels(){
		return event.getMessage().getMentionedChannels();
	}
	@NotDocumented
	public List<Role> getMentionedRoles() {
		return event.getMessage().getMentionedRoles();
	}
	@NotDocumented
	public List<Member> getMentionedMembers() {
		return event.getMessage().getMentionedMembers();
	}
	@NotDocumented
	public Message getMessage() {
		return message;
	}
	@NotDocumented
	public List<Attachment> getMessageAttachments() {
		return message.getAttachments();
	}
	@Override
	/**
	 * [ES] Devuelve el {@link MessageReceivedEvent} asociado a este Packet.<br>
	 * [EN] Returns the {@link MessageReceivedEvent} associated to this Packet.
	 */
	public MessageReceivedEvent getEvent() {
		return super.getEvent();
	}
	@Override
	/**
	 * [ES] Devuelve verdadero si el comando fue llamado desde un canal 'WebHook'.<br>
	 * [EN] Returns true if the command was called from a 'WebHook' channel.
	 */
	public boolean isWebhookMessage() {
		return isWebhookMessage;
	}
	@Override
	public String getCommandPath() {
		return this.commandPath;
	}
	@Override
	public boolean canDoEphemeralReplies() {
		return false;
	}
	@Override
	@NotDocumented
	public ResponseListener<MessageReceivedEvent> waitForResponse(String responseCmdName, String[] spectedValues, int maxSeg, int maxMsg, Object...args) throws Exception {
		return this.responseCommandManager.waitForResponse(responseCmdName, this.event, spectedValues, maxSeg, maxMsg, args);
	}
	@Override
	public Member getMember() {
		return event.getMember();
	}
	@Override
	public User getUser() {
		return event.getAuthor();
	}
	@Override
	public Guild getGuild() {
		return event.getGuild();
	}
	@Override
	public boolean tryDeleteMessage() {
		try {
			this.event.getChannel().purgeMessagesById(event.getMessageId());
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	@Override
	public Member getBotAsMember() {
		return event.getGuild().getMember(bot.getSelfUser());
	}

	@Override
	public PacketAction<MessageAction> deferReply() {
		return new PacketAction<MessageAction>(this.message);
	}
	@Override
	public PacketAction<MessageAction> deferReply(boolean ephemeral) {
		return new PacketAction<MessageAction>(this.message);
	}
	@Override
	public PacketAction<MessageAction> reply(String content) {
		return new PacketAction<MessageAction>(this.message.reply(content));
	}
	@Override
	public PacketAction<MessageAction> reply(Message message) {
		return new PacketAction<MessageAction>(this.message.reply(message));
	}
	@Override
	public PacketAction<MessageAction> deferReply(String content) {
		return new PacketAction<MessageAction>(this.message.reply(content));
	}
	@Override
	public PacketAction<MessageAction> deferReply(String content, boolean ephemeral) {
		return new PacketAction<MessageAction>(this.message.reply(content));
	}
}
