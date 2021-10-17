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
import com.jaxsandwich.sandwichcord.development.HalfDocumented;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.OptionInput;
import com.jaxsandwich.sandwichcord.models.ResponseCommandObject;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.Interaction;
/**
 * [ES] Herramienta para la construccion de {@link ResponseCommandPacket}s.<br>
 * [EN] Tool for building of {@link ResponseCommandPacket}s.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
@HalfDocumented
public class ResponsePacketBuilder<E extends GenericEvent> extends ReplyablePacketBuilder<ResponseCommandPacket>{
	/**
	 * [ES] {@link Bot} asociado al packete objetivo.<br>
	 * [EN] Associated {@link Bot} for the target packet.
	 */
	private Bot bot;
	/**
	 * [ES] Indica que el paquete a crear es dependiente del autor. Solo disponible para {@link ResponseCommandPacket}.<br>
	 * [EN] Indicates that the packet wich will be created depends of the autor. Only available for {@link ResponseCommandPacket}.
	 */
	private boolean authorOnly = false;
	/**
	 * [ES] Opciones para el paquete que se creará.<br>
	 * [EN] Options for the packet which will be created.
	 */
	private OptionInput[] options = null;
	/**
	 * [ES] {@link MessageReceivedEvent} asociado al paquete.<br>
	 * [EN] {@link MessageReceivedEvent} associated to the packet.
	 */
	private MessageReceivedEvent messageReceivedEvent;
	/**
	 * [ES] evento invocador asociado al paquete.<br>
	 * [EN] summoner event associated to the packet.
	 */
	private E callerEvent;
	/**
	 * [ES] {@link GuildConfig} asociado al paquete si el {@link MessageReceivedEvent} proviene de un servidor.<br>
	 * [EN] {@link GuildConfig} associated to the packet if the {@link MessageReceivedEvent} is from a guildConfig.
	 */
	private GuildConfig guild = null;
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
	@NotDocumented
	private Object[] args;
	@NotDocumented
	private Object[] eachArgs = null;
	@NotDocumented
	private Object[] afterArgs = null;
	@NotDocumented
	private Object[] noArgs = null;
	@NotDocumented
	private Object[] finallyArgs = null;
	@NotDocumented
	private String commandPath = null;
	/**
	 * [ES] Constructor de ResponsePacketBuilder.<br>
	 * [EN] Constructor of ResponsePacketBuilder.
	 * @param bot <br>[ES] bot asociado al Packet. [EN] Packet associated bot.
	 * @param config <br>[ES] configuracion del servidor. [EN] guild configuration.
	 * @param event <br>[ES] evento asociado. [EN] associated event.
	 * @param command <br>[ES] comando de respuesta invocado. [EN] summoned response command.
	 */
	public ResponsePacketBuilder(Bot bot, GuildConfig config, E event, ResponseCommandObject command) {
		this.bot=bot;
		this.guild=config;
		if(event instanceof MessageReceivedEvent) {
			this.channel=((MessageReceivedEvent)event).getChannel();
			this.authorId=((MessageReceivedEvent)event).getAuthor().getId();
		}else if(event instanceof SlashCommandEvent) {
			this.channel=((SlashCommandEvent)event).getChannel();
			this.authorId=((SlashCommandEvent)event).getUser().getId();
		}else if(event instanceof ButtonClickEvent) {
			this.channel=((ButtonClickEvent)event).getChannel();
			this.authorId=((ButtonClickEvent)event).getUser().getId();
		}else if(event instanceof Interaction) {
			this.channel=(MessageChannel) ((Interaction)event).getChannel();
			this.authorId=((Interaction)event).getUser().getId();
		}
		this.callerEvent=event;
		this.commandPath=command.getCommandPath();
	}
	/**
	 * [ES] Construye el ResponseCommandPacket.
	 * [EN] Builds the ResponseCommandPacket.
	 * @return [ES] Packet del tipo indicado.<br>[EN] Packet of indicated type.
	 */
	@Override
	public ResponseCommandPacket build() {
		return new ResponseCommandPacket(bot, options, messageReceivedEvent, authorOnly, null, args);
	}
	/**
	 * [ES] Devuelve el bot de este CommandPacketBuilder.<br>
	 * [EN] Returns the bot of this CommandPacketBuilder.
	 * @return [ES] bot asociado.<br>[EN] associated bot.
	 */
	public Bot getBot() {
		return bot;
	}
	/**
	 * [ES] Configura el bot de este CommandPacketBuilder.<br>
	 * [EN] Sets the bot of this CommandPacketBuilder.
	 * @param bot <br>[ES] bot asociado. [EN] associated bot.
	 */
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	/**
	 * [ES] Devuelve las {@link OptionInput}s de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link OptionInput}s of this CommandPacketBuilder.
	 * @return [ES] las opciones asociadas.<br>[EN] the associated options.
	 */
	public OptionInput[] getParameters() {
		return options;
	}
	/**
	 * [ES] Configura los parametros({@link OptionInput}) de este CommandPacketBuilder.<br>
	 * [EN] Sets the parameters({@link OptionInput}) of this CommandPacketBuilder.
	 */
	public void setParameters(OptionInput[] parameters) {
		this.options = parameters;
	}
	/**
	 * [ES] Devuelve el {@link MessageReceivedEvent} de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link MessageReceivedEvent} of this CommandPacketBuilder.
	 */
	public MessageReceivedEvent getMessageReceivedEvent() {
		return messageReceivedEvent;
	}
	/**
	 * [ES] Configura el {@link MessageReceivedEvent} de este CommandPacketBuilder.<br>
	 * [EN] Sets the {@link MessageReceivedEvent} of this CommandPacketBuilder.
	 */
	public void setMessageReceivedEvent(MessageReceivedEvent messageReceived) {
		this.messageReceivedEvent = messageReceived;
		this.channel=messageReceived.getChannel();
	}
	/**
	 * [ES] Devuelve el {@link GuildConfig} de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link GuildConfig} of this CommandPacketBuilder.
	 */
	public GuildConfig getGuild() {
		return guild;
	}
	/**
	 * [ES] Configura el {@link GuildConfig} de este CommandPacketBuilder.<br>
	 * [EN] Sets the {@link GuildConfig} of this CommandPacketBuilder.
	 */
	public void setGuild(GuildConfig guild) {
		this.guild = guild;
	}
	/**
	 * [ES] Devuelve el {@link MessageChannel} de este CommandPacketBuilder.<br>
	 * [EN] Returns the {@link MessageChannel} of this CommandPacketBuilder.
	 */
	public MessageChannel getChannel() {
		return channel;
	}
	/**
	 * [ES] Configura el {@link MessageChannel} de este CommandPacketBuilder.<br>
	 * [EN] Sets the {@link MessageChannel} of this CommandPacketBuilder.
	 */
	public void setChannel(MessageChannel channel) {
		if(messageReceivedEvent!=null)
			return;
		this.channel = channel;
	}
	/**
	 * [ES] Devuelve el id del autor asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the author id asociated to this CommandPacketBuilder.
	 */
	public String getAuthorId() {
		return authorId;
	}
	/**
	 * [ES] Configura el id del autor asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the author id asociated to this CommandPacketBuilder.
	 */
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	/**
	 * [ES] Devuelve los argumentos asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the arguments asociated to this CommandPacketBuilder.
	 */
	public Object[] getArgs() {
		return args;
	}
	/**
	 * [ES] Configura los argumentos asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the arguments asociated to this CommandPacketBuilder.
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion del ciclo de espera.<br>
	 * [EN] Returns the arguments for the waiting cycle execution.
	 */
	public Object[] getWaitingExecutionArgs() {
		return eachArgs;
	}
	@NotDocumented
	public void setWaitingExecutionArgs(Object[] eachArgs) {
		this.eachArgs = eachArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecución cuando el comando termino su ejecución exitosamente.<br>
	 * [EN] Returns the arguments for the execution when the command ended its execution succesfully.
	 */
	public Object[] getSuccessExecutionArgs() {
		return afterArgs;
	}
	@NotDocumented
	public void setSuccessExecutionArgs(Object[] afterArgs) {
		this.afterArgs = afterArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion cuando el comando no fue ejecutado exitosamente.<br>
	 * [EN] Returns the arguments for the execution when the command did not end its execution succesfully.
	 */
	public Object[] getFailedExecutionArgs() {
		return noArgs;
	}
	@NotDocumented
	public void setFailedExecutionArgs(Object[] noArgs) {
		this.noArgs = noArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion cuando el ciclo de espera termina.<br>
	 * [EN] Returns the arguments for the execution when the waiting cycle ends.
	 */
	public Object[] getFinallyExecutionArgs() {
		return finallyArgs;
	}
	@NotDocumented
	public void setFinallyExecutionArgs(Object[] finallyArgs) {
		this.finallyArgs = finallyArgs;
	}
	/**
	 * [ES] Devuelve verdadero si el {@link ResponseCommandPacket} asociado a este CommandPacketBuilder depende del autor.<br>
	 * [EN] Returns true if the {@link ResponseCommandPacket} asociated to this CommandPacketBuilder depends on the author.
	 */
	public boolean isAuthorOnly() {
		return authorOnly;
	}
	/**
	 * [ES] Configura si el {@link ResponseCommandPacket} asociado a este CommandPacketBuilder depende del autor.<br>
	 * [EN] Sets if the {@link ResponseCommandPacket} asociated to this CommandPacketBuilder depends on the author.
	 */
	public void setAuthorOnly(boolean authorOnly) {
		this.authorOnly = authorOnly;
	}
	@NotDocumented
	public E getCallerEvent() {
		return callerEvent;
	}
}
