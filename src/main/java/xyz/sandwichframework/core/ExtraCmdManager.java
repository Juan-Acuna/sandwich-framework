package xyz.sandwichframework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.models.ExtraCmdPacket;
import xyz.sandwichframework.models.ModelExtraCommand;
import xyz.sandwichframework.models.discord.ModelGuild;
/**
 * Manejador de Comandos extra.
 * Manager of Extra commands.
 * @author Juancho
 * @version 1.1
 */
public class ExtraCmdManager {
	/**
	 * Constante privada de clase.
	 * Private constant of class.
	 */
	private static final String wildcard = "\\*/";
	/**
	 * Constante privada de clase.
	 * Private constant of class.
	 */
	private static final String string_wildcard = wildcard +"{s}";
	/**
	 * Constante privada de clase.
	 * Private constant of class.
	 */
	private static final String number_wildcard = wildcard + "{n}";
	/**
	 * Constante de clase. Representa un comodín.
	 * Constant of class. Represents a wildcard.
	 */
	public static final String[] WILDCARD = {wildcard};
	/**
	 * Constante de clase. Representa un comodín solo para texto sin números.
	 * Constant of class. Represents a wildcard. Only for text without numbers.
	 */
	public static final String[] STRING_WILDCARD = {string_wildcard};
	/**
	 * Constante de clase. Representa un comodín solo para números.
	 * Constant of class. Represents a wildcard. Only for numbers.
	 */
	public static final String[] NUMBER_WILDCARD = {number_wildcard};
	/**
	 * Contenedor de {@link ExtraCmdListener}.
	 * Container of {@link ExtraCmdListener}.
	 */
	private static Map<MessageChannel, List<ExtraCmdListener>> threads = (Map<MessageChannel, List<ExtraCmdListener>>) Collections.synchronizedMap(new HashMap<MessageChannel, List<ExtraCmdListener>>());
	/**
	 * {@link Bot} asociado a este gestor.
	 * {@link Bot} associated to this manager.
	 */
	private Bot bot;
	/**
	 * Constructor de ExtraCmdManager.
	 * Constructor of ExtraCmdManager.
	 */
	private ExtraCmdManager(Bot bot) {
		this.bot = bot;
	}
	/**
	 * Inicializa un gestor de comandos extra.
	 * Initializes an extra commands manager.
	 */
	protected static ExtraCmdManager startService(Bot bot) {
		return new ExtraCmdManager(bot);
	}
	/**
	 * Espera por la ejecución de un comando extra.
	 * Waits for the execution of an extra command.
	 */
	public ExtraCmdListener waitForExtraCmd(String extraCmdName, Message message, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		return waitForExtraCmd(extraCmdName, message.getChannel(),message.getAuthor().getId(),spectedValues,maxSeg,maxMsg,args);
	}
	/**
	 * Espera por la ejecución de un comando extra.
	 * Waits for the execution of an extra command.
	 */
	public ExtraCmdListener waitForExtraCmd(String extraCmdName, MessageChannel channel, String authorId, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ModelExtraCommand m = ModelExtraCommand.find(extraCmdName);
		ModelGuild g = null;
		if(channel.getType()!=ChannelType.PRIVATE) {
			g = bot.getGuildsManager().getGuild(((TextChannel)channel).getGuild().getIdLong());
		}
		CommandPacketBuilder cpb = new CommandPacketBuilder(bot, g, channel, authorId);
		cpb.setArgs(args);
		ExtraCmdListener o = new ExtraCmdListener(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ExtraCmdListener> l = threads.get(channel);
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ExtraCmdListener>());
			l.add(o);
			threads.put(channel, l);
		}else {
			threads.get(channel).add(o);
		}
		new Thread(o).start();
		return o;
	}
	/**
	 * Revisa si se llama a algun comando extra.
	 * Checks if an extra command is called.
	 */
	public void CheckExtras(MessageReceivedEvent event) {
		if(threads.size()<=0)
			return;
		if(event.getAuthor().getId().equals(bot.getSelfUser().getId()))
			return;
		List<ExtraCmdListener> l = threads.get(event.getChannel());
		if(l == null) {
			return;
		}
		for(ExtraCmdListener o : l) {
			o.PutMessage(event);
		}
	}
	/**
	 * Objeto que se encarga de escuchar y ejecutar llamadas a un comando extra.
	 * Object wich listen an executes calls for an extra command.
	 */
	public class ExtraCmdListener implements Runnable{
		/**
		 * Arreglo con todas las posibles entradas a las que el ExtraCmdListener debe responder.
		 * Array with all the inputs which the ExtraCmdListener have to response.
		 */
		public String[] spectedValues = null;
		/**
		 * Cantidad máxima de mensajes recibidos en espera por la llamada al comando extra antes de abortar.
		 * Max count of received messages while is waiting for the call for the extra command before abort.
		 */
		public int maxMsg = 5;
		/**
		 * Tiempo de espera(en segundos) para la llamada del comando extra.
		 * Time of wait(in seconds) for a call for the extra command.
		 */
		public int maxSeg = 60;
		/**
		 * Representa el {@link MessageReceivedEvent} asociado al comando extra.
		 * Represents the {@link MessageReceivedEvent} associated to the extra command.
		 */
		private MessageReceivedEvent event = null;
		/**
		 * Representa al objeto del comando extra.
		 * Represents the extra command object.
		 */
		private ModelExtraCommand action;
		/**
		 * Contador de mensajes.
		 * Count of messages.
		 */
		private int msgs = 0;
		/**
		 * Constructor de {@link ExtraCmdPacket} associado al comando extra.
		 * Builder of {@link ExtraCmdPacket} associated to the extra command.
		 */
		private CommandPacketBuilder builder;
		/**
		 * Constructor del {@link ExtraCmdListener}
		 * Constructor of the {@link ExtraCmdListener}
		 */
		protected ExtraCmdListener(ModelExtraCommand action, ExtraCmdPacket packet,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
		}
		/**
		 * Constructor del {@link ExtraCmdListener}
		 * Constructor of the {@link ExtraCmdListener}
		 */
		protected ExtraCmdListener(ModelExtraCommand action, CommandPacketBuilder builder,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
			this.builder=builder;
		}
		protected ModelExtraCommand getAction() {
			return action;
		}
		protected ExtraCmdListener setAction(ModelExtraCommand action) {
			this.action = action;
			return this;
		}
		@Override
		public void run(){
			float s = 0f;
			msgs = 0;
			boolean b = true;
			ExtraCmdPacket packet = builder.buildExtraPacket();
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
		protected void PutMessage(MessageReceivedEvent event) {
			this.event=event;
			if(this.builder.getAuthorId()==null)
				return;
			if(this.isAuthorOnly() && !this.builder.getAuthorId().equals(event.getAuthor().getId()))
				return;
			msgs++;
		}
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
		public ExtraCmdListener setEachArrgs(Object...args) {
			this.builder.setEachArgs(args);
			return this;
		}
		public ExtraCmdListener setAfterArrgs(Object...args) {
			this.builder.setAfterArgs(args);
			return this;
		}
		public ExtraCmdListener setNoExecutedArrgs(Object...args) {
			this.builder.setNoArgs(args);
			return this;
		}
		public ExtraCmdListener setFinallyArrgs(Object...args) {
			this.builder.setFinallyArgs(args);
			return this;
		}
		public ExtraCmdListener setAuthorOnly(boolean b) {
			this.builder.setAuthorOnly(b);
			return this;
		}
		public ExtraCmdListener asAuthorOnly() {
			this.builder.setAuthorOnly(true);
			return this;
		}
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
			ExtraCmdListener other = (ExtraCmdListener) obj;
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
