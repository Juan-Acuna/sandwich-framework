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

package com.jaxsandwich.sandwichcord.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.sandwichcord.models.ResponseCommandPacket;
import com.jaxsandwich.sandwichcord.models.ResponseCommandObject;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Manejador de Comandos extra.<br>
 * [ES] Manager of Extra commands.
 * @author Juancho
 * @version 1.3
 */
public class ResponseCommandManager {
	/**
	 * [ES] Constante privada de clase.<br>
	 * [EN] Private constant of class.
	 */
	private static final String wildcard = "\\*/";
	/**
	 * [ES] Constante privada de clase.<br>
	 * [EN] Private constant of class.
	 */
	private static final String string_wildcard = wildcard +"{s}";
	/**
	 * [ES] Constante privada de clase.<br>
	 * [EN] Private constant of class.
	 */
	private static final String number_wildcard = wildcard + "{n}";
	/**
	 * [ES] Constante de clase. Representa un comodín.<br>
	 * [EN] Constant of class. Represents a wildcard.
	 */
	public static final String[] WILDCARD = {wildcard};
	/**
	 * [ES] Constante de clase. Representa un comodín solo para texto sin números.<br>
	 * [EN] Constant of class. Represents a wildcard. Only for text without numbers.
	 */
	public static final String[] STRING_WILDCARD = {string_wildcard};
	/**
	 * [ES] Constante de clase. Representa un comodín solo para números.<br>
	 * [EN] Constant of class. Represents a wildcard. Only for numbers.
	 */
	public static final String[] NUMBER_WILDCARD = {number_wildcard};
	/**
	 * [ES] Contenedor de {@link ResponseListener}.<br>
	 * [EN] Container of {@link ResponseListener}.
	 */
	private static Map<MessageChannel, List<ResponseListener>> threads = Collections.synchronizedMap(new HashMap<MessageChannel, List<ResponseListener>>());
	/**
	 * [ES] {@link Bot} asociado a este gestor.<br>
	 * [EN] {@link Bot} associated to this manager.
	 */
	private Bot bot;
	/**
	 * [ES] Constructor de ResponseCommandManager.<br>
	 * [EN] Constructor of ResponseCommandManager.
	 */
	private ResponseCommandManager(Bot bot) {
		this.bot = bot;
	}
	/**
	 * [ES] Inicializa un gestor de comandos extra.<br>
	 * [EN] Initializes an extra commands manager.
	 */
	protected static ResponseCommandManager startService(Bot bot) {
		return new ResponseCommandManager(bot);
	}
	/**
	 * [ES] Espera por la ejecución de un comando extra.<br>
	 * [EN] Waits for the execution of an extra command.
	 */
	public ResponseListener waitForResponse(String responseCmdName, MessageReceivedEvent event, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ResponseCommandObject m = ResponseCommandObject.find(responseCmdName);
		GuildConfig g = null;
		if(event.isFromGuild()) {
			g = bot.getGuildsManager().getConfig(event.getGuild().getIdLong());
		}
		CommandPacketBuilder cpb = new CommandPacketBuilder(bot, g, event);
		cpb.setArgs(args);
		ResponseListener o = new ResponseListener(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ResponseListener> l = threads.get(event.getChannel());
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ResponseListener>());
			l.add(o);
			threads.put(event.getChannel(), l);
		}else {
			threads.get(event.getChannel()).add(o);
		}
		new Thread(o).start();
		return o;
	}
	/**
	 * [ES] Revisa si se llama a algun comando extra.<br>
	 * [EN] Checks if an extra command is called.
	 */
	public void CheckExtras(MessageReceivedEvent event) {
		if(threads.size()<=0)
			return;
		if(event.getAuthor().getId().equals(bot.getSelfUser().getId()))
			return;
		List<ResponseListener> l = threads.get(event.getChannel());
		if(l == null) {
			return;
		}
		for(ResponseListener o : l) {
			o.PutMessage(event);
		}
	}
	/**
	 * [ES] Objeto que se encarga de escuchar y ejecutar llamadas a un comando extra.<br>
	 * [EN] Object wich listen an executes calls for an extra command.
	 */
	public class ResponseListener implements Runnable{
		/**
		 * [ES] Arreglo con todas las posibles entradas a las que el ResponseListener debe responder.<br>
		 * [EN] Array with all the inputs which the ResponseListener have to response.
		 */
		public String[] spectedValues = null;
		/**
		 * [ES] Cantidad máxima de mensajes recibidos en espera por la llamada al comando extra antes de abortar.<br>
		 * [EN] Max count of received messages while is waiting for the call for the extra command before abort.
		 */
		public int maxMsg = 5;
		/**
		 * [ES] Tiempo de espera(en segundos) para la llamada del comando extra.<br>
		 * [EN] Time of wait(in seconds) for a call for the extra command.
		 */
		public int maxSeg = 60;
		/**
		 * [ES] Representa el {@link MessageReceivedEvent} asociado al comando extra.<br>
		 * [EN] Represents the {@link MessageReceivedEvent} associated to the extra command.
		 */
		private MessageReceivedEvent event = null;
		/**
		 * [ES] Representa al objeto del comando extra.<br>
		 * [EN] Represents the extra command object.
		 */
		private ResponseCommandObject action;
		/**
		 * [ES] Contador de mensajes.<br>
		 * [EN] Count of messages.
		 */
		private int msgs = 0;
		/**
		 * [ES] Constructor de {@link ResponseCommandPacket} associado al comando extra.<br>
		 * [EN] Builder of {@link ResponseCommandPacket} associated to the extra command.
		 */
		private CommandPacketBuilder builder;
		/**
		 * [ES] Constructor del {@link ResponseListener}<br>
		 * [EN] Constructor of the {@link ResponseListener}
		 */
		protected ResponseListener(ResponseCommandObject action, ResponseCommandPacket packet,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
		}
		/**
		 * [ES] Constructor del {@link ResponseListener}<br>
		 * [EN] Constructor of the {@link ResponseListener}
		 */
		protected ResponseListener(ResponseCommandObject action, CommandPacketBuilder builder,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
			this.builder=builder;
		}
		/**
		 * [ES] Devuelve el objto que representa al comando extra.<br>
		 * [EN] Returns the object that represents the extra command.
		 */
		protected ResponseCommandObject getAction() {
			return action;
		}
		/**
		 * [ES] Configura el objto que representa al comando extra.<br>
		 * [EN] Sets the object that represents the extra command.
		 */
		protected ResponseListener setAction(ResponseCommandObject action) {
			this.action = action;
			return this;
		}
		@Override
		public void run(){
			float s = 0f;
			msgs = 0;
			boolean b = true;
			ResponseCommandPacket packet = builder.buildExtraPacket();
			while(maxSeg > s && maxMsg > msgs && b) {
				action.eachRun(packet);
				if(Compare(event)) {
					builder.setMessageReceivedEvent(event);
					packet = builder.buildExtraPacket();
					action.Run(packet);
					b = false;
				}
				s += 0.5f;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!b) {
				action.afterRun(packet);
			}else {
				action.NoRun(packet);
			}
			action.finallyRun(packet);
			threads.get(packet.getChannel()).remove(this);
		}
		/**
		 * [ES] Metodo que captura el evento y lo inserta en el listener.<br>
		 * [EN] Method wich catches the event and puts it into the listener.
		 */
		protected void PutMessage(MessageReceivedEvent event) {
			this.event=event;
			if(this.builder.getAuthorId()==null)
				return;
			if(this.isAuthorOnly() && !this.builder.getAuthorId().equals(event.getAuthor().getId()))
				return;
			msgs++;
		}
		/**
		 * [ES] Compara la entrada con las entradas esperadas.<br>
		 * [EN] Compares the input with the spected inputs.
		 */
		private boolean Compare(MessageReceivedEvent event) {
			if(event == null) {
				return false;
			}
			if(spectedValues[0].startsWith(wildcard)) {
				switch(spectedValues[0]) {
				case wildcard:
					return true;
				case number_wildcard:
					return event.getMessage().getContentRaw().matches("[0-9]{1,999}");
				case string_wildcard:
					return !event.getMessage().getContentRaw().matches("[0-9]{1,999}");
				}
			}
			for(String a : spectedValues) {
				if(a.equalsIgnoreCase(event.getMessage().getContentRaw())){
					return true;
				}
			}
			event = null;
			return false;
		}
		/**
		 * [ES] Permite agregar argumentos 'Each'.<br>
		 * [EN] Allows to put 'Each' arguments.
		 */
		public ResponseListener setEachArrgs(Object...args) {
			this.builder.setEachArgs(args);
			return this;
		}
		/**
		 * [ES] Permite agregar argumentos 'After'.<br>
		 * [EN] Allows to put 'After' arguments.
		 */
		public ResponseListener setAfterArrgs(Object...args) {
			this.builder.setAfterArgs(args);
			return this;
		}
		/**
		 * [ES] Permite agregar argumentos 'NoExecuted'.<br>
		 * [EN] Allows to put 'NoExecuted' arguments.
		 */
		public ResponseListener setNoExecutedArrgs(Object...args) {
			this.builder.setNoArgs(args);
			return this;
		}
		/**
		 * [ES] Permite agregar argumentos 'Finally'.<br>
		 * [EN] Allows to put 'Finally' arguments.
		 */
		public ResponseListener setFinallyArrgs(Object...args) {
			this.builder.setFinallyArgs(args);
			return this;
		}
		/**
		 * [ES] Configura si el comando extra solo responde al autor.<br>
		 * [EN] Sets if the extra command only listens to the author.
		 */
		public ResponseListener setAuthorOnly(boolean b) {
			this.builder.setAuthorOnly(b);
			return this;
		}
		/**
		 * [ES] Devuelve verdadero si el comando extra solo responde al autor.<br>
		 * [EN] Returns true if the extra command only listens to the author.
		 */
		public boolean isAuthorOnly() {
			return this.builder.isAuthorOnly();
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.deepHashCode(builder.getArgs());
			result = prime * result + ((builder.getAuthorId() == null) ? 0 : builder.getAuthorId().hashCode());
			result = prime * result + ((builder.getChannel() == null) ? 0 : builder.getChannel().getId().hashCode());
			result = prime * result + maxMsg;
			result = prime * result + maxSeg;
			result = prime * result + Arrays.hashCode(spectedValues);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResponseListener other = (ResponseListener) obj;
			if (!Arrays.deepEquals(builder.getArgs(), other.builder.getArgs()))
				return false;
			if (builder.getAuthorId() == null) {
				if (other.builder.getAuthorId() != null)
					return false;
			} else if (!builder.getAuthorId().equals(other.builder.getAuthorId()))
				return false;
			if (builder.getChannel() == null) {
				if (other.builder.getChannel() != null)
					return false;
			} else if (!builder.getChannel().getId().equals(other.builder.getChannel().getId()))
				return false;
			if (maxMsg != other.maxMsg)
				return false;
			if (maxSeg != other.maxSeg)
				return false;
			if (!Arrays.equals(spectedValues, other.spectedValues))
				return false;
			return true;
		}
	}
}
