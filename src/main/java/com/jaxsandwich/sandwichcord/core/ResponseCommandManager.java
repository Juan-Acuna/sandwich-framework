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

import com.jaxsandwich.sandwichcord.core.builders.ResponsePacketBuilder;
import com.jaxsandwich.sandwichcord.models.ResponseCommandObject;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Manejador de comandos de respuesta.<br>
 * [ES] Manager of response commands.
 * @author Juancho
 * @since 0.3.0
 * @version 1.4
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
	private static Map<MessageChannel, List<ResponseListener<?>>> threads = Collections.synchronizedMap(new HashMap<MessageChannel, List<ResponseListener<?>>>());
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
	 * [ES] Inicializa un gestor de comandos de respuesta.<br>
	 * [EN] Initializes an response commands manager
	 * @param bot <br>[ES] bot que se asociará este ResponseCommandManager. 
	 * [EN] bot who will be associated with this ResponseCommandManager.
	 * @return [ES] instancia de ResponseCommandManager. [EN] instance of ResponseCommandManager.
	 */
	protected static ResponseCommandManager startService(Bot bot) {
		return new ResponseCommandManager(bot);
	}
	/**
	 * [ES] Espera por la llamada de un comando de respuesta.<br>
	 * [EN] Waits for the call of an response command.
	 * @param responseCmdName <br>[ES] nombre del comando de respuesta. [EN] name of th response command.
	 * @param event <br>[ES] evento asociado. [EN] associated event.
	 * @param specteValues <br>[ES] valores que activan el comando de respuesta. [EN] values that activate the response command.
	 * @param maxSeg <br>[ES] número máximo de segundos de tiempo de espera del comando de respuesta.
	 * [EN] maximum number of seconds for response command timeout.
	 * @param maxMsg <br>[ES] número máximo de mensajes recibidos antes de abortar la espera.
	 * [EN] maximum number of received messages before abort waiting.
	 * @param args <br>[ES] argumentos extra que necesite el comando. [EN] extra arguments the command needs.
	 * @return [ES] instancia del ResponseListener.<br>
	 * [EN] instance of the ResponseListener.
	 */
	public ResponseListener<MessageReceivedEvent> waitForResponse(String responseCmdName, MessageReceivedEvent event, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ResponseCommandObject m = ResponseCommandObject.find(responseCmdName);
		GuildConfig g = null;
		if(event.isFromGuild()) {
			g = bot.getGuildConfigManager().getConfig(event.getGuild().getIdLong());
		}
		ResponsePacketBuilder<MessageReceivedEvent> cpb = new ResponsePacketBuilder<MessageReceivedEvent>(bot, g, event, m);
		cpb.setArgs(args);
		ResponseListener<MessageReceivedEvent> o = new ResponseListener<MessageReceivedEvent>(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ResponseListener<?>> l = threads.get(event.getChannel());
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ResponseListener<?>>());
			l.add(o);
			threads.put(event.getChannel(), l);
		}else {
			threads.get(event.getChannel()).add(o);
		}
		new Thread(o).start();
		return o;
	}
	/**
	 * [ES] Espera por la llamada de un comando de respuesta.<br>
	 * [EN] Waits for the call of an response command.
	 * @param responseCmdName <br>[ES] nombre del comando de respuesta. [EN] name of th response command.
	 * @param event <br>[ES] evento asociado. [EN] associated event.
	 * @param specteValues <br>[ES] valores que activan el comando de respuesta. [EN] values that activate the response command.
	 * @param maxSeg <br>[ES] número máximo de segundos de tiempo de espera del comando de respuesta.
	 * [EN] maximum number of seconds for response command timeout.
	 * @param maxMsg <br>[ES] número máximo de mensajes recibidos antes de abortar la espera.
	 * [EN] maximum number of received messages before abort waiting.
	 * @param args <br>[ES] argumentos extra que necesite el comando. [EN] extra arguments the command needs.
	 * @return [ES] instancia del ResponseListener.<br>
	 * [EN] instance of the ResponseListener.
	 */
	public ResponseListener<SlashCommandEvent> waitForResponse(String responseCmdName, SlashCommandEvent event, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ResponseCommandObject m = ResponseCommandObject.find(responseCmdName);
		GuildConfig g = null;
		if(event.isFromGuild()) {
			g = bot.getGuildConfigManager().getConfig(event.getGuild().getIdLong());
		}
		ResponsePacketBuilder<SlashCommandEvent> cpb = new ResponsePacketBuilder<SlashCommandEvent>(bot, g, event, m);
		cpb.setArgs(args);
		ResponseListener<SlashCommandEvent> o = new ResponseListener<SlashCommandEvent>(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ResponseListener<?>> l = threads.get(event.getChannel());
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ResponseListener<?>>());
			l.add(o);
			threads.put(event.getChannel(), l);
		}else {
			threads.get(event.getChannel()).add(o);
		}
		new Thread(o).start();
		return o;
	}
	/**
	 * [ES] Espera por la llamada de un comando de respuesta.<br>
	 * [EN] Waits for the call of an response command.
	 * @param responseCmdName <br>[ES] nombre del comando de respuesta. [EN] name of th response command.
	 * @param event <br>[ES] evento asociado. [EN] associated event.
	 * @param specteValues <br>[ES] valores que activan el comando de respuesta. [EN] values that activate the response command.
	 * @param maxSeg <br>[ES] número máximo de segundos de tiempo de espera del comando de respuesta.
	 * [EN] maximum number of seconds for response command timeout.
	 * @param maxMsg <br>[ES] número máximo de mensajes recibidos antes de abortar la espera.
	 * [EN] maximum number of received messages before abort waiting.
	 * @param args <br>[ES] argumentos extra que necesite el comando. [EN] extra arguments the command needs.
	 * @return [ES] instancia del ResponseListener.<br>
	 * [EN] instance of the ResponseListener.
	 */
	public ResponseListener<ButtonClickEvent> waitForResponse(String responseCmdName, ButtonClickEvent event, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ResponseCommandObject m = ResponseCommandObject.find(responseCmdName);
		GuildConfig g = null;
		if(event.isFromGuild()) {
			g = bot.getGuildConfigManager().getConfig(event.getGuild().getIdLong());
		}
		ResponsePacketBuilder<ButtonClickEvent> cpb = new ResponsePacketBuilder<ButtonClickEvent>(bot, g, event, m);
		cpb.setArgs(args);
		ResponseListener<ButtonClickEvent> o = new ResponseListener<ButtonClickEvent>(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ResponseListener<?>> l = threads.get(event.getChannel());
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ResponseListener<?>>());
			l.add(o);
			threads.put(event.getChannel(), l);
		}else {
			threads.get(event.getChannel()).add(o);
		}
		new Thread(o).start();
		return o;
	}
	/**
	 * [ES] Espera por la llamada de un comando de respuesta.<br>
	 * [EN] Waits for the call of an response command.
	 * @param responseCmdName <br>[ES] nombre del comando de respuesta. [EN] name of th response command.
	 * @param event <br>[ES] evento asociado. [EN] associated event.
	 * @param specteValues <br>[ES] valores que activan el comando de respuesta. [EN] values that activate the response command.
	 * @param maxSeg <br>[ES] número máximo de segundos de tiempo de espera del comando de respuesta.
	 * [EN] maximum number of seconds for response command timeout.
	 * @param maxMsg <br>[ES] número máximo de mensajes recibidos antes de abortar la espera.
	 * [EN] maximum number of received messages before abort waiting.
	 * @param args <br>[ES] argumentos extra que necesite el comando. [EN] extra arguments the command needs.
	 * @return [ES] instancia del ResponseListener.<br>
	 * [EN] instance of the ResponseListener.
	 */
	public ResponseListener<GuildJoinEvent> waitForResponse(String responseCmdName, GuildJoinEvent event, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ResponseCommandObject m = ResponseCommandObject.find(responseCmdName);
		ResponsePacketBuilder<GuildJoinEvent> cpb = new ResponsePacketBuilder<GuildJoinEvent>(bot, null, event, m);
		cpb.setArgs(args);
		ResponseListener<GuildJoinEvent> o = new ResponseListener<GuildJoinEvent>(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ResponseListener<?>> l = threads.get(event.getGuild().getDefaultChannel());
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ResponseListener<?>>());
			l.add(o);
			threads.put(event.getGuild().getDefaultChannel(), l);
		}else {
			threads.get(event.getGuild().getDefaultChannel()).add(o);
		}
		new Thread(o).start();
		return o;
	}
	/**
	 * [ES] Revisa si se llama a algún comando de respuesta.<br>
	 * [EN] Checks if any response command is called.
	 */
	public void CheckExtras(MessageReceivedEvent event) {
		if(threads.size()<=0)
			return;
		if(event.getAuthor().getId().equals(bot.getSelfUser().getId()))
			return;
		List<ResponseListener<?>> l = threads.get(event.getChannel());
		if(l == null) {
			return;
		}
		for(ResponseListener<?> o : l) {
			o.PutMessage(event);
		}
	}
	/**
	 * [ES] Objeto que se encarga de escuchar y ejecutar llamadas a un comando de respuesta.<br>
	 * [EN] Object wich listen an executes calls for a response command.
	 */
	public class ResponseListener<E extends GenericEvent> implements Runnable{
		/**
		 * [ES] Arreglo con todas las posibles entradas a las que el ResponseListener debe responder.<br>
		 * [EN] Array with all the inputs which the ResponseListener have to response.
		 */
		public String[] spectedValues = null;
		/**
		 * [ES] Cantidad máxima de mensajes recibidos en espera por la llamada al comando de respuesta antes de abortar.<br>
		 * [EN] Max count of received messages while is waiting for the call for the response command before abort.
		 */
		public int maxMsg = 5;
		/**
		 * [ES] Tiempo de espera(en segundos) para la llamada del comando de respuesta.<br>
		 * [EN] Time of wait(in seconds) for a call for the response command.
		 */
		public int maxSeg = 60;
		/**
		 * [ES] Representa el {@link MessageReceivedEvent} asociado al comando de respuesta.<br>
		 * [EN] Represents the {@link MessageReceivedEvent} associated to the response command.
		 */
		private MessageReceivedEvent event = null;
		/**
		 * [ES] Representa al objeto del comando de respuesta.<br>
		 * [EN] Represents the response command object.
		 */
		private ResponseCommandObject action;
		/**
		 * [ES] Contador de mensajes.<br>
		 * [EN] Count of messages.
		 */
		private int msgs = 0;
		/**
		 * [ES] Constructor de {@link ResponseCommandPacket} associado al comando de respuesta.<br>
		 * [EN] Builder of {@link ResponseCommandPacket} associated to the response command.
		 */
		private ResponsePacketBuilder<E> builder;
		/**
		 * [ES] Constructor del {@link ResponseListener}<br>
		 * [EN] Constructor of the {@link ResponseListener}
		 * @param action <br>[ES] ResponseCommandObject asociado. [EN] associated ResponseCommandObject.
		 * @param builder <br>[ES] builder previamente configurado. [EN] preset builder.
		 * @param specteValues <br>[ES] valores que activan el comando de respuesta. [EN] values that activate the response command.
		 * @param maxSeg <br>[ES] número máximo de segundos de tiempo de espera del comando de respuesta.
		 * [EN] maximum number of seconds for response command timeout.
		 * @param maxMsg <br>[ES] número máximo de mensajes recibidos antes de abortar la espera.
		 * [EN] maximum number of received messages before abort waiting. 
		 */
		protected ResponseListener(ResponseCommandObject action,  ResponsePacketBuilder<E> builder,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
			this.builder=builder;
		}
		/**
		 * [ES] Devuelve el objto que representa al comando de respuesta.<br>
		 * [EN] Returns the object that represents the response command.
		 * @return [ES] instancia de ResponseCommandObject.<br>[EN] instance of ResponseCommandObject.
		 */
		protected ResponseCommandObject getAction() {
			return action;
		}
		/**
		 * [ES] Configura el objto que representa al comando extra.<br>
		 * [EN] Sets the object that represents the extra command.
		 * @param action <br>[ES] ResponseCommandObject a ser asignado. [EN] ResponseCommandObject that will be set.
		 * @return [ES] instancia de este ResponseListener.<br>[EN] instance of this ResponseListener.
		 */
		protected ResponseListener<E> setAction(ResponseCommandObject action) {
			this.action = action;
			return this;
		}
		/**
		 * [ES] Implentación de metodo run.<br>
		 * [EN]Implementation of run method.
		 */
		@Override
		public void run(){
			float s = 0f;
			msgs = 0;
			boolean b = true;
			ResponseCommandPacket packet = builder.build();
			while(maxSeg > s && maxMsg > msgs && b) {
				action.runWaitingExecution(packet);
				if(Compare(event)) {
					builder.setMessageReceivedEvent(event);
					packet = builder.build();
					try {
						action.execute(packet);
						b = false;
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				s += 0.5f;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!b) {
				action.runSuccessExecution(packet);
			}else {
				action.runFailedExecution(packet);
			}
			action.runFinallyExecution(packet);
			threads.get(packet.getChannel()).remove(this);
		}
		/**
		 * [ES] Metodo que captura el evento y lo inserta en el listener.<br>
		 * [EN] Method wich catches the event and puts it into the listener.
		 * @param event <br>[ES] evento analizado. [EN] analyzed event.
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
		 * @param event <br>[ES] evento que contiene el mensaje. [EN] event which contains the message.
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
		 * [ES] Permite agregar argumentos al metodo de espera de un comando de sepuesta.<br>
		 * [EN] Allows to put arguments for the execution of the waiting method of a response command.
		 * @param args <br>[ES] argumentos extras para la ejecución. [EN] extra arguments for execution.
		 */
		public ResponseListener<E> setWaitingExecutionArgs(Object...args) {
			this.builder.setWaitingExecutionArgs(args);
			return this;
		}
		/**
		 * [ES] Permite agregar argumentos al metodo de ejecución exitosa de un comando de respuesta.<br>
		 * [EN] Allows to put arguments to the success execution method of a response command.
		 * @param args <br>[ES] argumentos extras para la ejecución. [EN] extra arguments for execution.
		 */
		public ResponseListener<E> setSuccessExecutionArgs(Object...args) {
			this.builder.setSuccessExecutionArgs(args);
			return this;
		}
		/**
		 * [ES] Permite agregar argumentos al metodo de ejecución fallida de un comando de respuesta.<br>
		 * [EN] Allows to put arguments to the failed execution method of a response command.
		 * @param args <br>[ES] argumentos extras para la ejecución. [EN] extra arguments for execution.
		 */
		public ResponseListener<E> setFailedExecutionArgs(Object...args) {
			this.builder.setFailedExecutionArgs(args);
			return this;
		}
		/**
		 * [ES] Permite agregar argumentos al metodo de fin del ciclo de un comando de respuesta.<br>
		 * [EN] Allows to put arguments to the end of cycle method of a response command.
		 * @param args <br>[ES] argumentos extras para la ejecución. [EN] extra arguments for execution.
		 */
		public ResponseListener<E> setFinallyExecutionArgs(Object...args) {
			this.builder.setFinallyExecutionArgs(args);
			return this;
		}
		/**
		 * [ES] Configura si el comando de respuesta solo responde al autor.<br>
		 * [EN] Sets if the response command only listens to the author.
		 * @param authorOnly <br>[ES] true para que el comando solo responda al autor. 
		 * [EN] true for command only responses to the author.
		 */
		public ResponseListener<E> setAuthorOnly(boolean authorOnly) {
			this.builder.setAuthorOnly(authorOnly);
			return this;
		}
		/**
		 * [ES] Devuelve verdadero si el comando de respuesta solo responde al autor.<br>
		 * [EN] Returns true if the response command only listens to the author.
		 * @return [ES] verdadero si el comando solo responde al author.<br>
		 * [EN] true if the command only responses to author.
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
			@SuppressWarnings("unchecked")
			ResponseListener<E> other = (ResponseListener<E>) obj;
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
