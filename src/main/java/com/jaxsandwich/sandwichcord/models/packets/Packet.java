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

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.GuildConfigManager;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager.ResponseListener;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.development.HalfDocumented;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.StoreChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
/**
 * [ES] <br>
 * [EN] 
 * @author Juan Acuña
 * @version 1.1
 * @since 0.9.0
 */
public abstract class Packet<E extends GenericEvent> {
	/**
	 * [ES] Indica si el evento fue recibido desde un servidor.<br>
	 * [EN] Indicates if the evento was received from a guild.
	 */
	protected boolean fromGuild = false;
	/**
	 * [ES] Bot asocciado al Packet.<br>
	 * [EN] Bot associated to the Packet.
	 */
	protected Bot bot;
	/**
	 * [ES] Gestor de comandos extra asociado al Packet.<br>
	 * [EN] Extra command manager associated to the Packet.
	 */
	protected ResponseCommandManager responseCommandManager;
	/**
	 * [ES] Gestor de configuraciones de servidor asociado al Packet.<br>
	 * [EN] Guild configurations manager associated to the Packet.
	 */
	protected GuildConfigManager guildsManager;
	/**
	 * [ES] {@link GenericEvent} asociado al Packet.<br>
	 * [EN] {@link GenericEvent} associated to the Packet.
	 */
	protected E event;
	/**
	 * [ES] {@link GuildConfig} asociado al Packet. Retorna nulo si {@link Packet#fromGuild} es falso.<br>
	 * [EN] {@link GuildConfig} associated to the Packet. Return null if {@link Packet#fromGuild} is false.
	 */
	protected GuildConfig guildConfig = null;
	/**
	 * [ES] Identificador del autor del mensaje o interacción.<br>
	 * [EN] Identifier of author of the message or interaction.
	 */
	protected String authorId = null;
	/**
	 * [ES] Idioma recomendado, basado en la configuración del servidor.<br>
	 * [EN] Recommended language, based on the configuration of the guild.
	 */
	protected Language prefLang = null;
	/**
	 * [ES] Tipo de canal donde se recibió el mensaje.<br>
	 * [EN] Type of the channel where the message was received.
	 */
	protected ChannelType channelType;
	/**
	 * [ES] {@link MessageChannel} asociado al Packet.<br>
	 * [EN] {@link MessageChannel} associated to the Packet.
	 */
	protected MessageChannel channel = null;
	/**
	 * [ES] {@link TextChannel} asociado al Packet.<br>
	 * [EN] {@link TextChannel} associated to the Packet. 
	 */
	protected TextChannel textChannel = null;
	/**
	 * [ES] {@link PrivateChannel} asociado al Packet.<br>
	 * [EN] {@link PrivateChannel} associated to the Packet.
	 */
	protected PrivateChannel privateChannel = null;
	/**
	 * [ES] {@link StoreChannel} asociado al Packet.<br>
	 * [EN] {@link StoreChannel} associated to the Packet.
	 */
	protected StoreChannel storeChannel = null;
	/**
	 * [ES] {@link Category} asociado al Packet.<br>
	 * [EN] {@link Category} associated to the Packet.
	 */
	protected Category category = null;
	/**
	 * [ES] Constructor de Packet.<br>
	 * [EN] Constructor of Packet.
	 */
	public Packet(Bot bot, GuildConfig config, E event, MessageChannel channel, String authorId) {
		this.bot=bot;
		this.guildConfig=config;
		this.event=event;
		this.responseCommandManager=bot.getResponseCommandManager();
		this.guildsManager=bot.getGuildConfigManager();
		this.guildConfig=config;
		if(this.guildConfig!=null) {
			this.prefLang=this.guildConfig.getLanguage();
			this.fromGuild=true;
		}else {
			this.prefLang=this.bot.getDefaultLanguage();
		}
		this.channel=channel;
		this.authorId=authorId;
		loadChannels(channel);
	}
	/**
	 * [ES] Asigna el {@link MessageChannel} a un tipo especifico de canal.<br>
	 * [EN] Assigns the {@link MessageChannel} to a specific type of channel.
	 */
	protected void loadChannels(MessageChannel channel) {
		this.channelType=channel.getType();
		switch(this.channelType) {
		case TEXT:
			this.textChannel=(TextChannel) channel;
			break;
		case PRIVATE:
			this.privateChannel=(PrivateChannel) channel;
			break;
		case STORE:
			this.storeChannel=(StoreChannel) channel;
			break;
		case CATEGORY:
			this.category=(Category) channel;
			break;
		default:
			break;
		}
	}
	/**
	 * [ES] Devuelve el {@link Bot} asociado a este Packet.<br>
	 * [EN] Returns the {@link Bot} associated to this Packet.
	 */
	public Bot getBot() {
		return bot;
	}
	/**
	 * [ES] Devuelve el {@link ResponseCommandManager} asociado a este Packet.<br>
	 * [EN] Returns the {@link ResponseCommandManager} associated to this Packet.
	 */
	public ResponseCommandManager getExtraCmdManager() {
		return responseCommandManager;
	}
	/**
	 * [ES] Devuelve el {@link GuildConfigManager} asociado a este Packet.<br>
	 * [EN] Returns the {@link GuildConfigManager} associated to this Packet.
	 */
	public GuildConfigManager getGuildsManager() {
		return guildsManager;
	}
	/**
	 * [ES] Devuelve el evento asociado a este Packet.<br>
	 * [EN] Returns the event associated to this Packet.
	 */
	public E getEvent() {
		return event;
	}
	/**
	 * [ES] Devuelve verdadero si el comando fue llamado desde un servidor.<br>
	 * [EN] Returns true if the command was called from a guild.
	 */
	public boolean isFromGuild() {
		return this.fromGuild;
	}
	/**
	 * [ES] [ES] Devuelve el {@link GuildConfig} asociado a este Packet.<br>
	 * [EN] Returns the {@link GuildConfig} associated to this Packet.
	 */
	public GuildConfig getGuildConfig() {
		return this.guildConfig;
	}
	/**
	 * [ES] [ES] Devuelve el identificador del autor del mensaje.<br>
	 * [EN] Returns the identifier of the message author.
	 */
	public String getAuthorId() {
		return authorId;
	}
	/**
	 * [ES] Devuelve el idioma recomendado, basado en la configuración del servidor.<br>
	 * [EN] Returns the recommended language, based on the configuration of the guild.
	 */
	public Language getPreferredLang() {
		return prefLang;
	}
	/**
	 * [ES] Devuelve el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Returns the {@link MessageChannel} associated to this Packet.
	 */
	public MessageChannel getChannel() {
		return channel;
	}
	/**
	 * [ES] Devuelve el {@link TextChannel} asociado a este Packet. Puede retornar null si no existe ninguno asociado.<br>
	 * [EN] Returns the {@link TextChannel} associated to this Packet. Can return null if there is no one associated.
	 */
	public TextChannel getTextChannel() {
		return this.textChannel;
	}
	/**
	 * [ES] Devuelve el {@link PrivateChannel} asociado a este Packet. Puede retornar null si no existe ninguno asociado.<br>
	 * [EN] Returns the {@link PrivateChannel} associated to this Packet. Can return null if there is no one associated.
	 */
	public PrivateChannel getPrivateChannel() {
		return this.privateChannel;
	}
	/**
	 * [ES] Devuelve el {@link StoreChannel} asociado a este Packet. Puede retornar null si no existe ninguno asociado.<br>
	 * [EN] Returns the {@link StoreChannel} associated to this Packet. Can return null if there is no one associated.
	 */
	public StoreChannel getStoreChannel() {
		return this.storeChannel;
	}
	/**
	 * [ES] Devuelve la {@link Category}(no confundir con {@link com.jaxsandwich.sandwichcord.annotations.Category}) asociada a este Packet. Puede retornar null si no existe ninguna asociada. No se espera que este metodo deba ser usado.<br>
	 * [EN] Returns the {@link Category}(do not confuse with {@link com.jaxsandwich.sandwichcord.annotations.Category}) associated to this Packet. Can return null if there is no one associated. this method is not spected to be used.
	 */
	public Category getCategoryChannel() {
		return this.category;
	}
	/**
	 * [ES] Devuelve el tipo de canal por el cual se recibió el mensaje.<br>
	 * [EN] Returns the type of the channel where the message was recieved.
	 */
	public ChannelType getChannelType() {
		return channelType;
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this Packet.
	 */
	public PacketAction<MessageAction> sendMessage(String message) {
		return new PacketAction<MessageAction>(this.channel.sendMessage(message));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} associated to this Packet.
	 */
	public PacketAction<MessageAction> sendMessage(Message message) {
		return new PacketAction<MessageAction>(this.channel.sendMessage(message));
	}
	/**
	 * [ES] Envía uno o mas {@link MessageEmbed} en el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Sends one or more {@link MessageEmbed} to the {@link MessageChannel} associated to this Packet.
	 */
	public PacketAction<MessageAction> sendMessage(@Nonnull MessageEmbed embed, MessageEmbed...embeds) {
		if(embeds!=null)
			return new PacketAction<MessageAction>(this.channel.sendMessageEmbeds(embed,embeds));
		return new PacketAction<MessageAction>(this.channel.sendMessageEmbeds(embed));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this Packet.
	 */
	@HalfDocumented
	public PacketAction<MessageAction> sendMessage(String message, Collection<MessageEmbed> embeds, Collection<Component> components) {
		MessageBuilder b = new MessageBuilder();
		if(message!=null)
			b.setContent(message);
		if(embeds!=null & embeds.size()>0)
			b.setEmbeds(embeds);
		if(components!=null)
			b.setActionRows(ActionRow.of(components));
		return new PacketAction<MessageAction>(this.channel.sendMessage(b.build()));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this Packet.
	 */
	@HalfDocumented
	public PacketAction<MessageAction> sendMessage(Collection<MessageEmbed> embeds, Component...components) {
		MessageBuilder b = new MessageBuilder();
		if(embeds!=null & embeds.size()>0)
			b.setEmbeds(embeds);
		if(components!=null)
			b.setActionRows(ActionRow.of(components));
		return new PacketAction<MessageAction>(this.channel.sendMessage(b.build()));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este Packet.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this Packet.
	 */
	@HalfDocumented
	public PacketAction<MessageAction> sendMessage(String message, Component...components) {
		MessageBuilder b = new MessageBuilder();
		if(message!=null)
			b.setContent(message);
		if(components!=null)
			b.setActionRows(ActionRow.of(components));
		return new PacketAction<MessageAction>(this.channel.sendMessage(b.build()));
	}
	/**
	 * [ES] Envía uno o más {@link MessageEmbed} en el {@link MessageChannel} asociado a este Packet y se elimina después del tiempo especificado.<br>
	 * [EN] Sends one or more {@link MessageEmbed} to the {@link MessageChannel} associated to this Packet and deletes itself after the specified time.
	 */
	public void SendAndDestroy(MessageEmbed emb, int time, MessageEmbed...embeds) {
		this.channel.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este Packet y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this Packet and deletes itself after the specified time.
	 */
	public void SendAndDestroy(String msg,int time) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} asociado a este Packet y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} associated to this Packet and deletes itself after the specified time.
	 */
	public void SendAndDestroy(Message msg,int time) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un {@link MessageEmbed} en el {@link MessageChannel} asociado a este Packet y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link MessageEmbed} to the {@link MessageChannel} associated to this Packet and deletes itself after the specified time.
	 */
	public void SendAndDestroy(MessageEmbed emb, int time, TimeUnit unit) {
		this.channel.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este Packet y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this Packet and deletes itself after the specified time.
	 */
	public void SendAndDestroy(String msg,int time, TimeUnit unit) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} asociado a este Packet y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} associated to this Packet and deletes itself after the specified time.
	 */
	public void SendAndDestroy(Message msg,int time, TimeUnit unit) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
	@NotDocumented
	public abstract ResponseListener<E> waitForResponse(String responseCmdName, String[] spectedValues, int maxSeg, int maxMsg, Object...args) throws Exception;
	@NotDocumented
	public abstract Member getMember();
	@NotDocumented
	public abstract User getUser();
	@NotDocumented
	public abstract Guild getGuild();
	@NotDocumented
	public abstract Member getBotAsMember();
}
