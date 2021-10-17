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
import com.jaxsandwich.sandwichcord.development.*;
import com.jaxsandwich.sandwichcord.models.OptionInput;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Paquete con todo el contenido necesario para un comando de respuesta.<br>
 * [EN] Packet with all the required content for a response command.
 * @author Juan Acuña
 * @version 1.1
 * @since 0.6.0
 */
public class ResponseCommandPacket extends CommandPacket{
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
	 * [ES] Constructor de ResponseCommandPacket.<br>
	 * [EN] Constructor of ResponseCommandPacket.
	 */
	public ResponseCommandPacket(Bot bot, OptionInput[] options, MessageReceivedEvent event, boolean isAuthorOnly, String commandPath, Object...args) {
		super(bot, options, event, commandPath);
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
	 * [EN] *NOT TRANSLATED YET*?
	 */
	@HalfDocumented
	public Object[] getArgs() {
		return args;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion del ciclo de espera.<br>
	 * [EN] Returns the arguments for the waiting cycle execution.
	 */
	public Object[] getWaitinExecutionArgs() {
		return eachArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecución cuando el comando termino su ejecución exitosamente.<br>
	 * [EN] Returns the arguments for the execution when the command ended its execution succesfully.
	 */
	public Object[] getSuccessExecutionArgs() {
		return afterArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion cuando el comando no fue ejecutado exitosamente.<br>
	 * [EN] Returns the arguments for the execution when the command did not end its execution succesfully.
	 */
	public Object[] getFailedExecutionArgs() {
		return noArgs;
	}
	/**
	 * [ES] Devuelve los argumentos para la ejecucion cuando el ciclo de espera termina.<br>
	 * [EN] Returns the arguments for the execution when the waiting cycle ends.
	 */
	public Object[] getFinallyExecutionArgs() {
		return finallyArgs;
	}
}
