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

package com.jaxsandwich.sandwichcord.core.builders;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CommandBase;
import com.jaxsandwich.sandwichcord.models.OptionInput;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;
import com.jaxsandwich.sandwichcord.models.packets.CommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.jaxsandwich.sandwichcord.models.packets.SlashCommandPacket;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Herramienta para la creacion de paquetes para comandos({@link CommandPacket} o {@link SlashCommandPacket}).<br>
 * [EN] Tool for the creation of packets for commands({@link CommandPacket} o {@link SlashCommandPacket}).
 * @author Juan Acuña.
 * @version 2.0
 * @since 0.6.0
 */
public class CommandPacketBuilder<E extends GenericEvent, P extends ReplyablePacket<E>> extends ReplyablePacketBuilder<P> {
	/**
	 * [ES] {@link Bot} asociado al packete objetivo.<br>
	 * [EN] Associated {@link Bot} for the target packet.
	 */
	private Bot bot;
	/**
	 * [ES] Parametros para el paquete que se creará.<br>
	 * [EN] Parameters for the packet wich will be created.
	 */
	private OptionInput[] options = null;
	/**
	 * [ES] {@link MessageReceivedEvent} asociado al paquete.<br>
	 * [EN] {@link MessageReceivedEvent} associated to the packet.
	 */
	private E event;
	/**
	 * [ES] {@link GuildConfig} asociado al paquete si el {@link MessageReceivedEvent} proviene de un servidor.<br>
	 * [EN] {@link GuildConfig} associated to the packet if the {@link MessageReceivedEvent} is from a guildConfig.
	 */
	private GuildConfig config = null;
	/**
	 * [ES] {@link MessageChannel} donde fue enviado el comando.<br>
	 * [EN] {@link MessageChannel} where the command was send.
	 */
	private MessageChannel channel = null;
	/**
	 * [ES] Id del autor del comando({@link User#getId()} o {@link Member#getId()}).<br>
	 * [EN] Id of the author of the command({@link User#getId()} or {@link Member#getId()}).
	 */
	private String authorId;
	/**
	 * [ES] Ruta del comando(útil para comandos slash).<br>
	 * [EN] Path of the command(useful for slash commands).
	 */
	private String cmdPath = null;
	/**
	 * [ES] Constructor de CommandPacketBuilder.<br>
	 * [EN] Constructor of CommandPacketBuilder.
	 * @param bot <br>[ES] bot asociado al Packet. [EN] Packet associated bot.
	 * @param event <br>[ES] evento asociado al Packet. [EN] Packet associated event.
	 * @param actualLang <br>[ES] idioma por defecto del Packet. [EN] default language of Packet.
	 * @param cmd <br>[ES] comando asociado al Packet. [EN] Packet associated command.
	 * @param oprtxt <br>[ES] prefijo de opciones. [EN] options prefix.
	 */
	public CommandPacketBuilder(Bot bot, E event, Language actualLang, CommandBase cmd, String oprtxt) {
		this.bot=bot;
		this.event=event;
		if(event instanceof SlashCommandEvent) {
			this.channel=((SlashCommandEvent)event).getChannel();
		}else if(event instanceof MessageReceivedEvent) {
			this.channel=((MessageReceivedEvent)event).getChannel();
			this.options = this.findParameters(actualLang, ((MessageReceivedEvent)event).getMessage().getContentRaw(), cmd, oprtxt);
		}
		this.cmdPath = (cmd!=null?cmd.getCommandPath():null);
	}
	/**
	 * [ES] Construye un {@link CommandPacket} o un {@link SlashCommandPacket} según la configuración.<br>
	 * [EN] Builds a {@link CommandPacket} or a {@link SlashCommandPacket} according to the configuration.
	 * @return [ES] un Packet de tipo {@link CommandPacket} o {@link SlashCommandPacket}.<br>
	 * [EN] a Packet as {@link CommandPacket} o {@link SlashCommandPacket}.
	 */
	@SuppressWarnings("unchecked")
	public P build() {
		if(this.event instanceof SlashCommandEvent)
			try {
				return (P) new SlashCommandPacket(this.bot,(SlashCommandEvent) this.event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		try {
			return (P) new CommandPacket(this.bot,this.options,(MessageReceivedEvent)this.event,cmdPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * [ES] Devuelve el bot de este CommandPacketBuilder.<br>
	 * [EN] Returns the bot of this CommandPacketBuilder.
	 * @return [ES] el bot asociado al Packet. <br>[EN] Packet associated bot.
	 */
	public Bot getBot() {
		return bot;
	}
	/**
	 * [ES] Configura el bot de este CommandPacketBuilder.<br>
	 * [EN] Sets the bot of this CommandPacketBuilder.
	 * @param bot <br>[ES] bot que será asociado al Packet. [EN] bot which will be associated to the Packet.
	 */
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	/**
	 * [ES] Devuelve los {@link OptionInput}s de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link OptionInput}s of this CommandPacketBuilder.
	 * @return [ES] opciones ingresadas por el usuario.<br>
	 * [EN] options entered by the user.
	 */
	public OptionInput[] getOptionInputs() {
		return options;
	}
	/**
	 * [ES] Configura los {@link OptionInput}s de este CommandPacketBuilder.<br>
	 * [EN] Sets the {@link OptionInput}s of this CommandPacketBuilder.
	 * @param options <br>[ES] arreglo de opciones. [EN] array of options.
	 */
	public void setOptionInputs(OptionInput[] options) {
		this.options = options;
	}
	/**
	 * [ES] Devuelve el evento de este CommandPacketBuilder.<br>
	 * [EN] Returns the event of this CommandPacketBuilder.
	 * @return [ES] el evento asociado.<br>[EN] the associated event.
	 */
	public E getMessageReceivedEvent() {
		return event;
	}
	/**
	 * [ES] Configura el evento de este CommandPacketBuilder.<br>
	 * [EN] Sets the event of this CommandPacketBuilder.
	 * @param event <br>[ES] el evento asociado. [EN] the associated event.
	 */
	public void setMessageReceivedEvent(E event) {
		this.event = event;
		if(event instanceof SlashCommandEvent) {
			this.channel=((SlashCommandEvent)event).getChannel();
		}else if(event instanceof MessageReceivedEvent) {
			this.channel=((MessageReceivedEvent)event).getChannel();
		}
	}
	/**
	 * [ES] Devuelve el {@link GuildConfig} de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link GuildConfig} of this CommandPacketBuilder.
	 * @return [ES] la configuración asociada.<br>
	 * [EN] the associated configuration.
	 */
	public GuildConfig getGuild() {
		return config;
	}
	/**
	 * [ES] Configura el {@link GuildConfig} de este CommandPacketBuilder.<br>
	 * [EN] Sets the {@link GuildConfig} of this CommandPacketBuilder.
	 * @param config [ES] la configuración asociada. [EN] the associated configuration.
	 */
	public void setGuild(GuildConfig config) {
		this.config = config;
	}
	/**
	 * [ES] Devuelve el {@link MessageChannel} de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link MessageChannel} of this CommandPacketBuilder.
	 * @return [ES] {@link MessageChannel} asociado.<br>[EN] {@link MessageChannel} associated.
	 */
	public MessageChannel getChannel() {
		return channel;
	}
	/**
	 * [ES] Configura el {@link MessageChannel} de este CommandPacketBuilder.<br>
	 * [EN] Sets the {@link MessageChannel} of this CommandPacketBuilder.
	 * @param channel <br>[ES] {@link MessageChannel} asociado. [EN] {@link MessageChannel} associated.
	 */
	public void setChannel(MessageChannel channel) {
		if(event!=null)
			return;
		this.channel = channel;
	}
	/**
	 * [ES] Devuelve el id del autor asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the author id asociated to this CommandPacketBuilder.
	 * @return [ES] identificador del autor del evento.<br>[EN] identifier of author of the event.
	 */
	public String getAuthorId() {
		return authorId;
	}
	/**
	 * [ES] Configura el id del autor asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the author id asociated to this CommandPacketBuilder.
	 * @param authorId <br>[ES] identificador del autor del evento. [EN] identifier of author of the event.
	 */
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
}
