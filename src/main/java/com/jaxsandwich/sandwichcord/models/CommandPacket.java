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

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager.ResponseListener;
import com.jaxsandwich.sandwichcord.core.GuildConfigManager;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.development.HalfDocumented;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.StoreChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
/**
 * [ES] Paquete con todo el contenido necesario para un comando.
 * [EN] Packet with all the required content for a command.
 * @author Juan Acuña
 * @version 1.4
 */
public class CommandPacket {
	/**
	 * [ES] Indica si el mensaje fue recibido desde un servidor.<br>
	 * [EN] Indicates if the message was received from a guild.
	 */
	protected boolean fromGuild = false;
	/**
	 * [ES] Indica si el mensaje fue recibido en un canal 'WebHook'<br>
	 * [EN] Indicates if the message was received from a 'WebHook' channel.
	 */
	protected boolean isWebhookMessage = false;
	/**
	 * [ES] Bot asocciado al CommandPacket.<br>
	 * [EN] Bot associated to the CommandPacket.
	 */
	protected Bot bot;
	/**
	 * [ES] Gestor de comandos extra asociado al CommandPacket.<br>
	 * [EN] Extra command manager associated to the CommandPacket.
	 */
	protected ResponseCommandManager responseCommandManager;
	/**
	 * [ES] Gestor de configuraciones de servidor asociado al CommandPacket.<br>
	 * [EN] Guild configurations manager associated to the CommandPacket.
	 */
	protected GuildConfigManager guildsManager;
	/**
	 * [ES] Lista con las opciones/parametros ingresados por el usuario<br>
	 * [EN] List of parameters/options entered by the user.
	 */
	protected OptionInput[] parameters = null;
	/**
	 * [ES] {@link MessageReceivedEvent} asociado al CommandPacket.<br>
	 * [EN] {@link MessageReceivedEvent} associated to the CommandPacket.
	 */
	protected MessageReceivedEvent messageReceived;
	/**
	 * [ES] {@link GuildConfig} asociado al CommandPacket.<br>
	 * [EN] {@link GuildConfig} associated to the CommandPacket.
	 */
	protected GuildConfig guildConfig = null;
	/**
	 * [ES] Contenido del mensaje como texto.<br>
	 * [EN] Message content as text.
	 */
	protected String messageContent = null;
	/**
	 * [ES] Identificador del autor del mensaje.<br>
	 * [EN] Identifier of the message author.
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
	 * [ES] {@link MessageChannel} asociado al CommandPacket.<br>
	 * [EN] {@link MessageChannel} associated to the CommandPacket.
	 */
	protected MessageChannel channel = null;
	/**
	 * [ES] {@link TextChannel} asociado al CommandPacket.<br>
	 * [EN] {@link TextChannel} associated to the CommandPacket. 
	 */
	protected TextChannel textChannel = null;
	/**
	 * [ES] {@link PrivateChannel} asociado al CommandPacket.<br>
	 * [EN] {@link PrivateChannel} associated to the CommandPacket.
	 */
	protected PrivateChannel privateChannel = null;
	/**
	 * [ES] {@link StoreChannel} asociado al CommandPacket.<br>
	 * [EN] {@link StoreChannel} associated to the CommandPacket.
	 */
	protected StoreChannel storeChannel = null;
	/**
	 * [ES] {@link Category} asociado al CommandPacket.<br>
	 * [EN] {@link Category} associated to the CommandPacket.
	 */
	protected Category category = null;
	/**
	 * [ES] Constructor de CommandPacket.<br>
	 * [EN] Constructor of CommandPacket.
	 */
	public CommandPacket(Bot bot, GuildConfig config, MessageReceivedEvent event) {
		this.bot=bot;
		this.responseCommandManager=bot.getExtraCmdManager();
		this.messageReceived=event;
		this.guildsManager=bot.getGuildsManager();
		this.guildConfig=config;
		if(this.guildConfig!=null) {
			this.prefLang=this.guildConfig.getLanguage();
		}else {
			this.prefLang=this.bot.getDefaultLanguage();
		}
		this.fromGuild=event.isFromGuild();
		this.isWebhookMessage=event.isWebhookMessage();
		this.channel=event.getChannel();
		this.messageContent=event.getMessage().getContentRaw();
		this.authorId=event.getAuthor().getId();
		loadChannels(channel);
	}
	/**
	 * [ES] Constructor de CommandPacket.<br>
	 * [EN] Constructor of CommandPacket.
	 */
	public CommandPacket(Bot bot, OptionInput[] parameters, MessageReceivedEvent event) {
		this.bot=bot;
		this.responseCommandManager=bot.getExtraCmdManager();
		this.guildsManager=bot.getGuildsManager();
		this.parameters=parameters;
		this.messageReceived=event;
		this.fromGuild=event.isFromGuild();
		this.isWebhookMessage=event.isWebhookMessage();
		this.guildConfig=guildsManager.getConfig(event.getGuild());
		if(this.guildConfig!=null) {
			this.prefLang=this.guildConfig.getLanguage();
		}else {
			this.prefLang=this.bot.getDefaultLanguage();
		}
		this.channel=event.getChannel();
		this.authorId=event.getAuthor().getId();
		this.messageContent=event.getMessage().getContentRaw();
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
	 * [ES] Devuelve el {@link Bot} asociado a este CommandPacket.<br>
	 * [EN] Returns the {@link Bot} associated to this CommandPacket.
	 */
	public Bot getBot() {
		return bot;
	}
	/**
	 * [ES] Devuelve el {@link ResponseCommandManager} asociado a este CommandPacket.<br>
	 * [EN] Returns the {@link ResponseCommandManager} associated to this CommandPacket.
	 */
	public ResponseCommandManager getExtraCmdManager() {
		return responseCommandManager;
	}
	/**
	 * [ES] Devuelve el {@link GuildConfigManager} asociado a este CommandPacket.<br>
	 * [EN] Returns the {@link GuildConfigManager} associated to this CommandPacket.
	 */
	public GuildConfigManager getGuildsManager() {
		return guildsManager;
	}
	/**
	 * [ES] Devuelve la lista de parametros/opciones ingresados por el usuario.<br>
	 * [EN] Returns the list of parameters/options entered by the user.
	 */
	public OptionInput[] getParameters() {
		return parameters;
	}
	/**
	 * [ES] Devuelve el {@link MessageReceivedEvent} asociado a este CommandPacket.<br>
	 * [EN] Returns the {@link MessageReceivedEvent} associated to this CommandPacket.
	 */
	public MessageReceivedEvent getMessageReceivedEvent() {
		return messageReceived;
	}
	/**
	 * [ES] Devuelve verdadero si el comando fue llamado desde un servidor.<br>
	 * [EN] Returns true if the command was called from a guild.
	 */
	public boolean isFromGuild() {
		return this.fromGuild;
	}
	/**
	 * [ES] Devuelve verdadero si el comando fue llamado desde un canal 'WebHook'.<br>
	 * [EN] Returns true if the command was called from a 'WebHook' channel.
	 */
	public boolean isWebhookMessage() {
		return isWebhookMessage;
	}
	/**
	 * [ES] [ES] Devuelve el {@link GuildConfig} asociado a este CommandPacket.<br>
	 * [EN] Returns the {@link GuildConfig} associated to this CommandPacket.
	 */
	public GuildConfig getGuildConfig() {
		return this.guildConfig;
	}
	/**
	 * [ES] [ES] Devuelve el mensaje como texto.<br>
	 * [EN] Returns the message as text. 
	 */
	public String getMessageContent() {
		return messageContent;
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
	 * [ES] Devuelve el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Returns the {@link MessageChannel} associated to this CommandPacket.
	 */
	public MessageChannel getChannel() {
		return channel;
	}
	/**
	 * [ES] Devuelve el {@link TextChannel} asociado a este CommandPacket. Puede retornar null si no existe ninguno asociado.<br>
	 * [EN] Returns the {@link TextChannel} associated to this CommandPacket. Can return null if there is no one associated.
	 */
	public TextChannel getTextChannel() {
		return this.textChannel;
	}
	/**
	 * [ES] Devuelve el {@link PrivateChannel} asociado a este CommandPacket. Puede retornar null si no existe ninguno asociado.<br>
	 * [EN] Returns the {@link PrivateChannel} associated to this CommandPacket. Can return null if there is no one associated.
	 */
	public PrivateChannel getPrivateChannel() {
		return this.privateChannel;
	}
	/**
	 * [ES] Devuelve el {@link StoreChannel} asociado a este CommandPacket. Puede retornar null si no existe ninguno asociado.<br>
	 * [EN] Returns the {@link StoreChannel} associated to this CommandPacket. Can return null if there is no one associated.
	 */
	public StoreChannel getStoreChannel() {
		return this.storeChannel;
	}
	/**
	 * [ES] Devuelve la {@link Category}(no confundir con {@link com.jaxsandwich.sandwichcord.annotations.Category}) asociada a este CommandPacket. Puede retornar null si no existe ninguna asociada. No se espera que este metodo deba ser usado.<br>
	 * [EN] Returns the {@link Category}(do not confuse with {@link com.jaxsandwich.sandwichcord.annotations.Category}) associated to this CommandPacket. Can return null if there is no one associated. this method is not spected to be used.
	 */
	public Category getCategoryChannel() {
		return this.category;
	}
	/**
	 * [ES] Devuelve el tipo de canal por el cual se recibió el mensaje.<br>
	 * [EN] Returns the type of the channel where the messaje was recieved.
	 */
	public ChannelType getChannelType() {
		return channelType;
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this CommandPacket.
	 */
	public MessageAction sendMessage(String message) {
		return this.channel.sendMessage(message);
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} associated to this CommandPacket.
	 */
	public MessageAction sendMessage(Message message) {
		return this.channel.sendMessage(message);
	}
	/**
	 * [ES] Envía uno o mas {@link MessageEmbed} en el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Sends one or more {@link MessageEmbed} to the {@link MessageChannel} associated to this CommandPacket.
	 */
	public MessageAction sendMessage(@Nonnull MessageEmbed embed, MessageEmbed...embeds) {
		if(embeds!=null)
			return this.channel.sendMessageEmbeds(embed,embeds);
		return this.channel.sendMessageEmbeds(embed);
	}
	/**
	 * [ES] Envía uno o más {@link MessageEmbed} en el {@link MessageChannel} asociado a este CommandPacket y se elimina después del tiempo especificado.<br>
	 * [EN] Sends one or more {@link MessageEmbed} to the {@link MessageChannel} associated to this CommandPacket and deletes itself after the specified time.
	 */
	public void SendAndDestroy(MessageEmbed emb, int time, MessageEmbed...embeds) {
		this.channel.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este CommandPacket y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this CommandPacket and deletes itself after the specified time.
	 */
	public void SendAndDestroy(String msg,int time) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} asociado a este CommandPacket y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} associated to this CommandPacket and deletes itself after the specified time.
	 */
	public void SendAndDestroy(Message msg,int time) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un {@link MessageEmbed} en el {@link MessageChannel} asociado a este CommandPacket y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link MessageEmbed} to the {@link MessageChannel} associated to this CommandPacket and deletes itself after the specified time.
	 */
	public void SendAndDestroy(MessageEmbed emb, int time, TimeUnit unit) {
		this.channel.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este CommandPacket y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this CommandPacket and deletes itself after the specified time.
	 */
	public void SendAndDestroy(String msg,int time, TimeUnit unit) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} asociado a este CommandPacket y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} associated to this CommandPacket and deletes itself after the specified time.
	 */
	public void SendAndDestroy(Message msg,int time, TimeUnit unit) {
		this.channel.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this CommandPacket.
	 */
	@HalfDocumented
	public MessageAction sendMessage(String message, Collection<MessageEmbed> embeds, Collection<Component> components) {
		MessageBuilder b = new MessageBuilder();
		if(message!=null)
			b.setContent(message);
		if(embeds!=null & embeds.size()>0)
			b.setEmbeds(embeds);
		if(components!=null)
			b.setActionRows(ActionRow.of(components));
		return this.channel.sendMessage(b.build());
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this CommandPacket.
	 */
	@HalfDocumented
	public MessageAction sendMessage(Collection<MessageEmbed> embeds, Component...components) {
		MessageBuilder b = new MessageBuilder();
		if(embeds!=null & embeds.size()>0)
			b.setEmbeds(embeds);
		if(components!=null)
			b.setActionRows(ActionRow.of(components));
		return this.channel.sendMessage(b.build());
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} asociado a este CommandPacket.<br>
	 * [EN] Sends a message to the {@link MessageChannel} associated to this CommandPacket.
	 */
	@HalfDocumented
	public MessageAction sendMessage(String message, Component...components) {
		MessageBuilder b = new MessageBuilder();
		if(message!=null)
			b.setContent(message);
		if(components!=null)
			b.setActionRows(ActionRow.of(components));
		return this.channel.sendMessage(b.build());
	}
	@NotDocumented
	public ResponseListener waitForResponse(String responseCmdName, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		return this.responseCommandManager.waitForResponse(responseCmdName, this.getMessageReceivedEvent(), spectedValues, maxSeg, maxMsg, args);
	}
	@NotDocumented
	public ResponseListener waitForResponse(String responseCmdName, String[] spectedValues, Object...args) {
		return this.responseCommandManager.waitForResponse(responseCmdName, this.getMessageReceivedEvent(), spectedValues, 60, 5, args);
	}
}
