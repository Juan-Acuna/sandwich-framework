package com.jaxsandwich.sandwichcord.models;

import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.development.*;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Paquete con todo el contenido necesario para un comando extra.<br>
 * [EN] Packet with all the required content for an extra command.
 * @author Juan Acuña
 * @version 1.1
 */
public class ExtraCmdPacket extends CommandPacket{
	/**
	 * [ES] Indica que el comando responde exclusivamente al autor que lo llamó<br>
	 * [EN] Indicates that the command only responses to the author who called it.
	 */
	private boolean authorOnly = false;
	/**
	 * [ES] <br>
	 * [EN] 
	 */
	@NotDocumented
	private Object[] args;
	/**
	 * [ES] <br>
	 * [EN] 
	 */
	@NotDocumented
	private Object[] eachArgs = null;
	/**
	 * [ES] <br>
	 * [EN] 
	 */
	@NotDocumented
	private Object[] afterArgs = null;
	/**
	 * [ES] <br>
	 * [EN] 
	 */
	@NotDocumented
	private Object[] noArgs = null;
	/**
	 * [ES] <br>
	 * [EN] 
	 */
	@NotDocumented
	private Object[] finallyArgs = null;
	/**
	 * [ES] Constructor de ExtraCmdPacket.<br>
	 * [EN] Constructor of ExtraCmdPacket.
	 */
	public ExtraCmdPacket(Bot bot, GuildConfig config, MessageReceivedEvent event, boolean isAuthorOnly, Object...args) {
		super(bot, config, event);
		this.args=args;
		this.authorOnly=isAuthorOnly;
	}
	/**
	 * [ES] Devuelve verdadero si el comando solo responde al usuario que lo llamó.<br>
	 * [EN] Returns true if the command only responses to the user who called it.
	 */
	public boolean isAuthorOnly() {
		return authorOnly;
	}
	/**
	 * [ES] Devuelve los argumentos pasados al momento de llamar este comando.<br>
	 * [EN] ...?
	 */
	@HalfDocumented
	public Object[] getArgs() {
		return args;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion del ciclo de espera.<br>
	 * [EN] Returns the arguments for the waiting cycle execution.
	 */
	public Object[] getEachArgs() {
		return eachArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecución cuando el comando termino su ejecución exitosamente.<br>
	 * [EN] Returns the arguments for the execution when the command ended its execution succesfully.
	 */
	public Object[] getAfterArgs() {
		return afterArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion cuando el comando no fue ejecutado exitosamente.<br>
	 * [EN] Returns the arguments for the execution when the command did not end its execution succesfully.
	 */
	public Object[] getNoArgs() {
		return noArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion cuando el ciclo de espera termina.<br>
	 * [EN] Returns the arguments for the execution when the waiting cycle ends.
	 */
	public Object[] getFinallyArgs() {
		return finallyArgs;
	}
}
