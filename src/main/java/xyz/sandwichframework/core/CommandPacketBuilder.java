package xyz.sandwichframework.core;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.CommandBase;
import xyz.sandwichframework.models.CommandPacket;
import xyz.sandwichframework.models.ExtraCmdPacket;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.ModelOption;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.discord.ModelGuild;
/**
 * Herramienta para la creacion de paquetes para comandos.
 * Tool for the creation of packets for commands.
 * @author Juan Acuña.
 * @version 1.0
 */
public class CommandPacketBuilder {
	/**
	 * {@link Bot} asociado al packete objetivo.
	 * Associated {@link Bot} for the target packet.
	 */
	private Bot bot;
	/**
	 * Indica que el paquete a crear es dependiente del autor. Solo disponible para {@link ExtraCmdPacket}.
	 * Indicates that the packet wich will be created depends of the autor. Only available for {@link ExtraCmdPacket}.
	 */
	private boolean authorOnly = false;
	/**
	 * Parametros para el paquete que se creará.
	 * Parameters for the packet wich will be created.
	 */
	private ArrayList<InputParameter> parameters = null;
	/**
	 * {@link MessageReceivedEvent} asociado al paquete.
	 * {@link MessageReceivedEvent} associated to the packet.
	 */
	private MessageReceivedEvent messageReceivedEvent;
	/**
	 * {@link ModelGuild} asociado al paquete si el {@link MessageReceivedEvent} proviene de un servidor.
	 * {@link ModelGuild} associated to the packet if the {@link MessageReceivedEvent} is from a guild.
	 */
	private ModelGuild guild = null;
	/**
	 * {@link MessageChannel} donde fue enviado el comando.
	 * {@link MessageChannel} where the command was send.
	 */
	private MessageChannel channel = null;
	/**
	 * Id del autor del comando({@link User#getId()} o {@link Member#getId()}).
	 * Id of the author of the command({@link User#getId()} or {@link Member#getId()}).
	 */
	private String authorId;
	/**
	 * Argumentos a usar en un {@link  xyz.sandwichframework.models.ModelExtraCommand}. Solo disponible para {@link ExtraCmdPacket}.
	 * Arguments used for a {@link  xyz.sandwichframework.models.ModelExtraCommand}. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] args;
	/**
	 * Argumentos a usar en un {@link  xyz.sandwichframework.models.ModelExtraCommand} mientras esta en espera. Solo disponible para {@link ExtraCmdPacket}.
	 * Arguments used for a {@link  xyz.sandwichframework.models.ModelExtraCommand} while it's waiting. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] eachArgs = null;
	/**
	 * Argumentos a usar en un {@link  xyz.sandwichframework.models.ModelExtraCommand} después de la ejecucion exitosa. Solo disponible para {@link ExtraCmdPacket}.
	 * Arguments used for a {@link  xyz.sandwichframework.models.ModelExtraCommand} after a successful execution. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] afterArgs = null;
	/**
	 * Argumentos a usar en un {@link  xyz.sandwichframework.models.ModelExtraCommand} cuando no fue jecutado. Solo disponible para {@link ExtraCmdPacket}.
	 * Arguments used for a {@link  xyz.sandwichframework.models.ModelExtraCommand} when it's not executed. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] noArgs = null;
	/**
	 * Argumentos a usar en un {@link  xyz.sandwichframework.models.ModelExtraCommand} se haya ejecutado o no. Solo disponible para {@link ExtraCmdPacket}.
	 * Arguments used for a {@link  xyz.sandwichframework.models.ModelExtraCommand} if it's been executed or not. Only available for {@link ExtraCmdPacket}.
	 */
	private Object[] finallyArgs = null;
	/**
	 * Constructor vacío de CommandPacketBuilder.
	 * Empty constructor of CommandPacketBuilder.
	 */
	public CommandPacketBuilder() { }
	/**
	 * Constructor de CommandPacketBuilder.
	 * Constructor of CommandPacketBuilder.
	 */
	public CommandPacketBuilder(Bot bot, ModelGuild guild, MessageChannel channel, String authorId) {
		this.bot=bot;
		this.guild=guild;
		this.channel=channel;
		this.authorId=authorId;
	}
	/**
	 * Constructor de CommandPacketBuilder.
	 * Constructor of CommandPacketBuilder.
	 */
	public CommandPacketBuilder(Bot bot, MessageReceivedEvent event, Language actualLang, CommandBase cmd, String oprxt) {
		this.bot=bot;
		this.messageReceivedEvent=event;
		this.channel=event.getChannel();
		this.parameters = this.findParameters(actualLang, event.getMessage().getContentRaw(), cmd, oprxt);
	}
	/**
	 * Construye el {@link CommandPacket} configurado en este CommandPacketBuilder.
	 * Builds the {@link CommandPacket} with the configuration in this CommandPacketBuilder.
	 */
	public CommandPacket build() {
		return new CommandPacket(this.bot,this.parameters,this.messageReceivedEvent);
	}
	/**
	 * Construye el {@link ExtraCmdPacket} configurado en este CommandPacketBuilder.
	 * Builds the {@link ExtraCmdPacket} with the configuration in this CommandPacketBuilder.
	 */
	public ExtraCmdPacket buildExtraPacket() {
		return new ExtraCmdPacket(this.bot, this.guild, this.messageReceivedEvent,this.authorOnly, this.args);
	}
	/**
	 * Devuelve el bot de este CommandPacketBuilder.
	 * Returns the bot of this CommandPacketBuilder.
	 */
	public Bot getBot() {
		return bot;
	}
	/**
	 * Configura el bot de este CommandPacketBuilder.
	 * Sets the bot of this CommandPacketBuilder.
	 */
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	/**
	 * Devuelve los parametros({@link InputParameter}) de este CommandPacketBuilder.
	 * Returns the parameters({@link InputParameter}) of this CommandPacketBuilder.
	 */
	public ArrayList<InputParameter> getParameters() {
		return parameters;
	}
	/**
	 * Configura los parametros({@link InputParameter}) de este CommandPacketBuilder.
	 * Sets the parameters({@link InputParameter}) of this CommandPacketBuilder.
	 */
	public void setParameters(ArrayList<InputParameter> parameters) {
		this.parameters = parameters;
	}
	/**
	 * Devuelve el {@link MessageReceivedEvent} de este CommandPacketBuilder.
	 * Returns the {@link MessageReceivedEvent} of this CommandPacketBuilder.
	 */
	public MessageReceivedEvent getMessageReceivedEvent() {
		return messageReceivedEvent;
	}
	/**
	 * Configura el {@link MessageReceivedEvent} de este CommandPacketBuilder.
	 * Sets the {@link MessageReceivedEvent} of this CommandPacketBuilder.
	 */
	public void setMessageReceivedEvent(MessageReceivedEvent messageReceived) {
		this.messageReceivedEvent = messageReceived;
		this.channel=messageReceived.getChannel();
	}
	/**
	 * Devuelve el {@link ModelGuild} de este CommandPacketBuilder.
	 * Returns the {@link ModelGuild} of this CommandPacketBuilder.
	 */
	public ModelGuild getGuild() {
		return guild;
	}
	/**
	 * Configura el {@link ModelGuild} de este CommandPacketBuilder.
	 * Sets the {@link ModelGuild} of this CommandPacketBuilder.
	 */
	public void setGuild(ModelGuild guild) {
		this.guild = guild;
	}
	/**
	 * Devuelve el {@link MessageChannel} de este CommandPacketBuilder.
	 * Returns the {@link MessageChannel} of this CommandPacketBuilder.
	 */
	public MessageChannel getChannel() {
		return channel;
	}
	/**
	 * Configura el {@link MessageChannel} de este CommandPacketBuilder.
	 * Sets the {@link MessageChannel} of this CommandPacketBuilder.
	 */
	public void setChannel(MessageChannel channel) {
		if(messageReceivedEvent!=null)
			return;
		this.channel = channel;
	}
	/**
	 * Devuelve el id del autor asociado a este CommandPacketBuilder.
	 * Returns the author id asociated to this CommandPacketBuilder.
	 */
	public String getAuthorId() {
		return authorId;
	}
	/**
	 * Configura el id del autor asociado a este CommandPacketBuilder.
	 * Sets the author id asociated to this CommandPacketBuilder.
	 */
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	/**
	 * Devuelve los argumentos asociado a este CommandPacketBuilder.
	 * Returns the arguments asociated to this CommandPacketBuilder.
	 */
	public Object[] getArgs() {
		return args;
	}
	/**
	 * Configura los argumentos asociado a este CommandPacketBuilder.
	 * Sets the arguments asociated to this CommandPacketBuilder.
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}
	/**
	 * Devuelve los argumentos 'Each' asociado a este CommandPacketBuilder.
	 * Returns the arguments 'Each' asociated to this CommandPacketBuilder.
	 */
	public Object[] getEachArgs() {
		return eachArgs;
	}
	/**
	 * Configura los argumentos 'Each' asociado a este CommandPacketBuilder.
	 * Sets the arguments 'Each' asociated to this CommandPacketBuilder.
	 */
	public void setEachArgs(Object[] eachArgs) {
		this.eachArgs = eachArgs;
	}
	/**
	 * Devuelve los argumentos 'After' asociado a este CommandPacketBuilder.
	 * Returns the arguments 'After' asociated to this CommandPacketBuilder.
	 */
	public Object[] getAfterArgs() {
		return afterArgs;
	}
	/**
	 * Configura los argumentos 'After' asociado a este CommandPacketBuilder.
	 * Sets the arguments 'After' asociated to this CommandPacketBuilder.
	 */
	public void setAfterArgs(Object[] afterArgs) {
		this.afterArgs = afterArgs;
	}
	/**
	 * Devuelve los argumentos 'No' asociado a este CommandPacketBuilder.
	 * Returns the arguments 'No' asociated to this CommandPacketBuilder.
	 */
	public Object[] getNoArgs() {
		return noArgs;
	}
	/**
	 * Configura los argumentos 'No' asociado a este CommandPacketBuilder.
	 * Sets the arguments 'No' asociated to this CommandPacketBuilder.
	 */
	public void setNoArgs(Object[] noArgs) {
		this.noArgs = noArgs;
	}
	/**
	 * Devuelve los argumentos 'Finally' asociado a este CommandPacketBuilder.
	 * Returns the arguments 'Finally' asociated to this CommandPacketBuilder.
	 */
	public Object[] getFinallyArgs() {
		return finallyArgs;
	}
	/**
	 * Configura los argumentos 'Finally' asociado a este CommandPacketBuilder.
	 * Sets the arguments 'Finally' asociated to this CommandPacketBuilder.
	 */
	public void setFinallyArgs(Object[] finallyArgs) {
		this.finallyArgs = finallyArgs;
	}
	/**
	 * Devuelve verdadero si el {@link ExtraCmdPacket} asociado a este CommandPacketBuilder depende del autor.
	 * Returns true if the {@link ExtraCmdPacket} asociated to this CommandPacketBuilder depends on the author.
	 */
	public boolean isAuthorOnly() {
		return authorOnly;
	}
	/**
	 * Configura si el {@link ExtraCmdPacket} asociado a este CommandPacketBuilder depende del autor.
	 * Sets if the {@link ExtraCmdPacket} asociated to this CommandPacketBuilder depends on the author.
	 */
	public void setAuthorOnly(boolean authorOnly) {
		this.authorOnly = authorOnly;
	}
	/**
	 * Analiza la entrada y devuelve los parametros del comando.
	 * Analyzes the input and returns the paramters of the command.
	 */
	private ArrayList<InputParameter> findParameters(Language lang, String input,CommandBase command, String oprx){
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
						p.setType(InputParamType.Standar);
						break;
					}else {
						for(String a : mo.getAlias(lang)) {
							if(s[i].toLowerCase().equalsIgnoreCase(oprx+a)) {
								p.setKey(mo.getName(lang));
								p.setType(InputParamType.Standar);
								break;
							}
						}
					}
				}
				if(p.getType() == InputParamType.Custom) {
					/*for(String hs : AutoHelpCommand.getHelpOptions(lang)) {
						if(s[i].equalsIgnoreCase(oprx+hs)) {
							p.setKey(AutoHelpCommand.AUTO_HELP_KEY);
							p.setType(InputParamType.Standar);
							break;
						}
					}*/
					if(p.getType() == InputParamType.Custom) {
						p.setType(InputParamType.Invalid);
						p.setKey(s[i]);
					}
				}
			}else if(i==1) {
				p.setType(InputParamType.Custom);
				p.setKey("custom");
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
		return lista;
	}
}
