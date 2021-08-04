package com.jaxsandwich.sandwichcord.core;

import java.util.ArrayList;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CommandBase;
import com.jaxsandwich.sandwichcord.models.CommandPacket;
import com.jaxsandwich.sandwichcord.models.ExtraCmdPacket;
import com.jaxsandwich.sandwichcord.models.InputParameter;
import com.jaxsandwich.sandwichcord.models.ModelOption;
import com.jaxsandwich.sandwichcord.models.InputParameter.InputParamType;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Herramienta para la creacion de paquetes para comandos.<br>
 * [EN] Tool for the creation of packets for commands.
 * @author Juan Acuña.
 * @version 1.1
 */
public class CommandPacketBuilder {
	/**
	 * [ES] {@link Bot} asociado al packete objetivo.<br>
	 * [EN] Associated {@link Bot} for the target packet.
	 */
	private Bot bot;
	/**
	 * [ES] Indica que el paquete a crear es dependiente del autor. Solo disponible para {@link ExtraCmdPacket}.<br>
	 * [EN] Indicates that the packet wich will be created depends of the autor. Only available for {@link ExtraCmdPacket}.
	 */
	private boolean authorOnly = false;
	/**
	 * [ES] Parametros para el paquete que se creará.<br>
	 * [EN] Parameters for the packet wich will be created.
	 */
	private InputParameter[] parameters = null;
	/**
	 * [ES] {@link MessageReceivedEvent} asociado al paquete.<br>
	 * [EN] {@link MessageReceivedEvent} associated to the packet.
	 */
	private MessageReceivedEvent messageReceivedEvent;
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
	/**
	 * [ES] Argumentos a usar en un {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand}. Solo disponible para {@link ExtraCmdPacket}.<br>
	 * [EN] Arguments used for a {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand}. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] args;
	/**
	 * [ES] Argumentos a usar en un {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} mientras esta en espera. Solo disponible para {@link ExtraCmdPacket}.<br>
	 * [EN] Arguments used for a {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} while it's waiting. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] eachArgs = null;
	/**
	 * [ES] Argumentos a usar en un {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} después de la ejecucion exitosa. Solo disponible para {@link ExtraCmdPacket}.<br>
	 * [EN] Arguments used for a {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} after a successful execution. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] afterArgs = null;
	/**
	 * [ES] Argumentos a usar en un {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} cuando no fue jecutado. Solo disponible para {@link ExtraCmdPacket}.<br>
	 * [EN] Arguments used for a {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} when it's not executed. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] noArgs = null;
	/**
	 * [ES] Argumentos a usar en un {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} se haya ejecutado o no. Solo disponible para {@link ExtraCmdPacket}.<br>
	 * [EN] Arguments used for a {@link  com.jaxsandwich.sandwichcord.models.ModelExtraCommand} if it's been executed or not. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] finallyArgs = null;
	/**
	 * [ES] Constructor vacío de CommandPacketBuilder.<br>
	 * [EN] Empty constructor of CommandPacketBuilder.
	 */
	public CommandPacketBuilder() { }
	/**
	 * [ES] Constructor de CommandPacketBuilder.<br>
	 * [EN] Constructor of CommandPacketBuilder.
	 */
	public CommandPacketBuilder(Bot bot, GuildConfig guild, MessageChannel channel, String authorId) {
		this.bot=bot;
		this.guild=guild;
		this.channel=channel;
		this.authorId=authorId;
	}
	/**
	 * [ES] Constructor de CommandPacketBuilder.<br>
	 * [EN] Constructor of CommandPacketBuilder.
	 */
	public CommandPacketBuilder(Bot bot, MessageReceivedEvent event, Language actualLang, CommandBase cmd, String oprxt) {
		this.bot=bot;
		this.messageReceivedEvent=event;
		this.channel=event.getChannel();
		this.parameters = this.findParameters(actualLang, event.getMessage().getContentRaw(), cmd, oprxt);
	}
	/**
	 * [ES] Construye el {@link CommandPacket} configurado en este CommandPacketBuilder.<br>
	 * [EN] Builds the {@link CommandPacket} with the configuration in this CommandPacketBuilder.
	 */
	public CommandPacket build() {
		return new CommandPacket(this.bot,this.parameters,this.messageReceivedEvent);
	}
	/**
	 * [ES] Construye el {@link ExtraCmdPacket} configurado en este CommandPacketBuilder.<br>
	 * [EN] Builds the {@link ExtraCmdPacket} with the configuration in this CommandPacketBuilder.
	 */
	public ExtraCmdPacket buildExtraPacket() {
		return new ExtraCmdPacket(this.bot, this.guild, this.messageReceivedEvent,this.authorOnly, this.args);
	}
	/**
	 * [ES] Devuelve el bot de este CommandPacketBuilder.<br>
	 * [EN] Returns the bot of this CommandPacketBuilder.
	 */
	public Bot getBot() {
		return bot;
	}
	/**
	 * [ES] Configura el bot de este CommandPacketBuilder.<br>
	 * [EN] Sets the bot of this CommandPacketBuilder.
	 */
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	/**
	 * [ES] Devuelve los parametros({@link InputParameter}) de este CommandPacketBuilder.<br>
	 * [EN] Returns the parameters({@link InputParameter}) of this CommandPacketBuilder.
	 */
	public InputParameter[] getParameters() {
		return parameters;
	}
	/**
	 * [ES] Configura los parametros({@link InputParameter}) de este CommandPacketBuilder.<br>
	 * [EN] Sets the parameters({@link InputParameter}) of this CommandPacketBuilder.
	 */
	public void setParameters(InputParameter[] parameters) {
		this.parameters = parameters;
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
	 * [ES] Devuelve los argumentos 'Each' asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the arguments 'Each' asociated to this CommandPacketBuilder.
	 */
	public Object[] getEachArgs() {
		return eachArgs;
	}
	/**
	 * [ES] Configura los argumentos 'Each' asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the arguments 'Each' asociated to this CommandPacketBuilder.
	 */
	public void setEachArgs(Object[] eachArgs) {
		this.eachArgs = eachArgs;
	}
	/**
	 * [ES] Devuelve los argumentos 'After' asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the arguments 'After' asociated to this CommandPacketBuilder.
	 */
	public Object[] getAfterArgs() {
		return afterArgs;
	}
	/**
	 * [ES] Configura los argumentos 'After' asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the arguments 'After' asociated to this CommandPacketBuilder.
	 */
	public void setAfterArgs(Object[] afterArgs) {
		this.afterArgs = afterArgs;
	}
	/**
	 * [ES] Devuelve los argumentos 'No' asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the arguments 'No' asociated to this CommandPacketBuilder.
	 */
	public Object[] getNoArgs() {
		return noArgs;
	}
	/**
	 * [ES] Configura los argumentos 'No' asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the arguments 'No' asociated to this CommandPacketBuilder.
	 */
	public void setNoArgs(Object[] noArgs) {
		this.noArgs = noArgs;
	}
	/**
	 * [ES] Devuelve los argumentos 'Finally' asociado a este CommandPacketBuilder.<br>
	 * [EN] Returns the arguments 'Finally' asociated to this CommandPacketBuilder.
	 */
	public Object[] getFinallyArgs() {
		return finallyArgs;
	}
	/**
	 * [ES] Configura los argumentos 'Finally' asociado a este CommandPacketBuilder.<br>
	 * [EN] Sets the arguments 'Finally' asociated to this CommandPacketBuilder.
	 */
	public void setFinallyArgs(Object[] finallyArgs) {
		this.finallyArgs = finallyArgs;
	}
	/**
	 * [ES] Devuelve verdadero si el {@link ExtraCmdPacket} asociado a este CommandPacketBuilder depende del autor.<br>
	 * [EN] Returns true if the {@link ExtraCmdPacket} asociated to this CommandPacketBuilder depends on the author.
	 */
	public boolean isAuthorOnly() {
		return authorOnly;
	}
	/**
	 * [ES] Configura si el {@link ExtraCmdPacket} asociado a este CommandPacketBuilder depende del autor.<br>
	 * [EN] Sets if the {@link ExtraCmdPacket} asociated to this CommandPacketBuilder depends on the author.
	 */
	public void setAuthorOnly(boolean authorOnly) {
		this.authorOnly = authorOnly;
	}
	/**
	 * [ES] Analiza la entrada y devuelve los parametros del comando.<br>
	 * [EN] Analyzes the input and returns the paramters of the command.
	 */
	private InputParameter[] findParameters(Language lang, String input,CommandBase command, String oprx){
		String[] s = input.split(" ");
		ArrayList<InputParameter> lista = new ArrayList<InputParameter>();
		InputParameter p = new InputParameter();
		for(int i=1;i<s.length;i++) {
			if((s[i]).startsWith(oprx)) {
				p=null;
				p = new InputParameter();
				for(ModelOption mo : command.getOptions()) {
					if(s[i].toLowerCase().equalsIgnoreCase(oprx + mo.getName(lang))) {
						p.setKey(mo.getName(lang));
						p.setType(InputParamType.STANDAR);
						break;
					}else {
						for(String a : mo.getAlias(lang)) {
							if(s[i].toLowerCase().equalsIgnoreCase(oprx+a)) {
								p.setKey(mo.getName(lang));
								p.setType(InputParamType.STANDAR);
								break;
							}
						}
					}
				}
				if(p.getType() == InputParamType.NO_STANDAR) {
					p.setType(InputParamType.CUSTOM);
					p.setKey(s[i]);
				}
			}else if(i==1) {
				p.setType(InputParamType.NO_STANDAR);
				p.setKey("nostandar");
				p.setValue(s[i]);
			}else if(p.getValueAsString()!=null){
				p.setValue(p.getValueAsString()+" "+s[i]);
			}else {
				p.setValue(s[i]);
			}
			if(lista.size()>0) {
				if(lista.lastIndexOf(p) == -1) {
					lista.add(p);
				}
			}else {
				lista.add(p);
			}
		}
		InputParameter[] res = new InputParameter[lista.size()];
		return lista.toArray(res);
	}
}
