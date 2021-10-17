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
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.OptionInput;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.requests.RestAction;
/**
 * [ES] <br>
 * [EN] 
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public abstract class ReplyablePacket<E extends GenericEvent> extends Packet<E>{
	/**
	 * [ES] Lista con las opciones/parametros ingresados por el usuario<br>
	 * [EN] List of parameters/options entered by the user.
	 */
	protected OptionInput[] options = null;
	/**
	 * [ES] Contenido del mensaje como texto.<br>
	 * [EN] Message content as text.
	 */
	protected String messageContent = null;
	public ReplyablePacket(Bot bot, GuildConfig config, E event, MessageChannel channel, String authorId) {
		super(bot, config, event, channel, authorId);
	}
	/**
	 * [ES] [ES] Devuelve el mensaje como texto.<br>
	 * [EN] Returns the message as text. 
	 */
	public String getMessageContent() {
		return messageContent;
	}
	/**
	 * [ES] Devuelve la lista de parametros/opciones ingresados por el usuario.<br>
	 * [EN] Returns the list of parameters/options entered by the user.
	 */
	public OptionInput[] getOptions() {
		return options;
	}
	@NotDocumented
	public abstract String getCommandPath();
	@NotDocumented
	public abstract boolean isWebhookMessage();
	@NotDocumented
	public abstract boolean canDoEphemeralReplies();
	@NotDocumented
	public abstract boolean tryDeleteMessage();
	@NotDocumented
	public abstract PacketAction<? extends RestAction<?>> reply(String content);
	@NotDocumented
	public abstract PacketAction<? extends RestAction<?>> reply(Message message);
	@NotDocumented
	public abstract PacketAction<? extends RestAction<?>> deferReply();
	@NotDocumented
	public abstract PacketAction<? extends RestAction<?>> deferReply(String content);
	@NotDocumented
	public abstract PacketAction<? extends RestAction<?>> deferReply(String content, boolean ephemeral);
	@NotDocumented
	public abstract PacketAction<? extends RestAction<?>> deferReply(boolean ephemeral);
}
