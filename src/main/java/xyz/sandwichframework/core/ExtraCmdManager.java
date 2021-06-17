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
import xyz.sandwichframework.models.ModelExtraCommand;
/**
 * Manejador de Comandos extra.
 * Manager of Extra commands.
 * @author Juancho
 * @version 1.0
 */
public class ExtraCmdManager {
	private static final String wildcard = "\\*/";
	private static final String string_wildcard = wildcard +"{s}";
	private static final String number_wildcard = wildcard + "{n}";
	
	public static final String[] WILDCARD = {wildcard};
	public static final String[] STRING_WILDCARD = {string_wildcard};
	public static final String[] NUMBER_WILDCARD = {number_wildcard};
	private static Map<MessageChannel, List<ExtraCmdObj>> threads = (Map<MessageChannel, List<ExtraCmdObj>>) Collections.synchronizedMap(new HashMap<MessageChannel, List<ExtraCmdObj>>());
	private static ExtraCmdManager _instance = new ExtraCmdManager();
	
	private ExtraCmdManager() {
		
	}
	
	public static ExtraCmdManager getManager() {
		if(_instance!=null) {
			return _instance;
		}
		return _instance = new ExtraCmdManager();
	}
	
	public ExtraCmdObj registerExtraCmd(String extraCmdName, Message message, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		return registerExtraCmd(extraCmdName, message.getChannel(),message.getAuthor().getId(),spectedValues,maxSeg,maxMsg,args);
	}
	public ExtraCmdObj registerExtraCmd(String extraCmdName, MessageChannel channel, String authorId, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ModelExtraCommand m = pickExtraCommand(extraCmdName);
		ExtraCmdObj o = new ExtraCmdObj(m, channel, authorId,spectedValues, maxSeg, maxMsg, args);
		List<ExtraCmdObj> l = threads.get(channel);
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ExtraCmdObj>());
			l.add(o);
			threads.put(channel, l);
		}else {
			threads.get(channel).add(o);
		}
		new Thread(o).start();
		return o;
	}
	
	public void CheckExtras(Message message) {
		if(threads.size()<=0)
			return;
		if(message.getAuthor().getId().equals(BotRunner._self.jda.getSelfUser().getId()))
			return;
		List<ExtraCmdObj> l = threads.get(message.getChannel());
		if(l == null) {
			return;
		}
		for(ExtraCmdObj o : l) {
			o.PutMessage(message.getContentRaw(),message.getAuthor().getId());
		}
	}
	
	public class ExtraCmdObj implements Runnable{

		Lock lock = new ReentrantLock();
		public MessageChannel channel;
		public String authorId = null; // user
		public String[] spectedValues = null;
		public int maxMsg = 5;
		public int maxSeg = 60; // 30
		public boolean authorOnly = false;
		private String cmd = null;
		private ModelExtraCommand action;
		private int msgs = 0;
		private Object[] args = null;
		private Object[] eachArgs = null;
		private Object[] afterArgs = null;
		private Object[] noArgs = null;
		private Object[] finallyArgs = null;
		
		protected ExtraCmdObj() {}
		protected ExtraCmdObj(ModelExtraCommand action, MessageChannel channel, String authorId, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
			this.channel=channel;
			this.authorId=authorId;
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
			this.args=args;
		}
		
		protected ModelExtraCommand getAction() {
			return action;
		}
		protected ExtraCmdObj setAction(ModelExtraCommand action) {
			this.action = action;
			return this;
		}
		@Override
		public void run(){
			float s = 0f;
			msgs = 0;
			boolean b = true;
			while(maxSeg > s && maxMsg > msgs && b) {
				action.eachRun(channel, eachArgs);
				if(Compare(cmd)) {
					lock.lock();
					action.Run(cmd, channel, authorId,args);
					lock.unlock();
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
				action.afterRun(channel, afterArgs);
			}else {
				action.NoRun(channel, noArgs);
			}
			action.finallyRun(channel, finallyArgs);
			threads.get(channel).remove(this);
		}
		protected void PutMessage(String message, String authorId) {
			cmd = message;
			if(this.authorId==null)
				return;
			if(this.isAuthorOnly() && !this.authorId.equals(authorId))
				return;
			msgs++;
		}
		private boolean Compare(String message) {
			if(message == null) {
				return false;
			}
			if(spectedValues[0].startsWith(wildcard)) {
				switch(spectedValues[0]) {
				case wildcard:
					return true;
				case number_wildcard:
					return message.matches("[0-9]{1,999}");
				case string_wildcard:
					return !message.matches("[0-9]{1,999}");
				}
			}
			for(String a : spectedValues) {
				if(a.equalsIgnoreCase(message)){
					return true;
				}
			}
			cmd = null;
			return false;
		}
		public ExtraCmdObj setEachArrgs(Object...args) {
			this.eachArgs = args;
			return this;
		}
		public ExtraCmdObj setAfterArrgs(Object...args) {
			this.afterArgs = args;
			return this;
		}
		public ExtraCmdObj setNoExecutedArrgs(Object...args) {
			this.noArgs = args;
			return this;
		}
		public ExtraCmdObj setFinallyArrgs(Object...args) {
			this.finallyArgs = args;
			return this;
		}
		public ExtraCmdObj setAuthorOnly(boolean b) {
			this.authorOnly=b;
			return this;
		}
		public ExtraCmdObj asAuthorOnly() {
			this.authorOnly=true;
			return this;
		}
		public boolean isAuthorOnly() {
			return this.authorOnly;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Arrays.deepHashCode(afterArgs);
			result = prime * result + Arrays.deepHashCode(args);
			result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
			result = prime * result + ((channel == null) ? 0 : channel.getId().hashCode());
			result = prime * result + Arrays.deepHashCode(eachArgs);
			result = prime * result + Arrays.deepHashCode(finallyArgs);
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
			ExtraCmdObj other = (ExtraCmdObj) obj;
			if (!Arrays.deepEquals(afterArgs, other.afterArgs))
				return false;
			if (!Arrays.deepEquals(args, other.args))
				return false;
			if (authorId == null) {
				if (other.authorId != null)
					return false;
			} else if (!authorId.equals(other.authorId))
				return false;
			if (channel == null) {
				if (other.channel != null)
					return false;
			} else if (!channel.getId().equals(other.channel.getId()))
				return false;
			if (!Arrays.deepEquals(eachArgs, other.eachArgs))
				return false;
			if (!Arrays.deepEquals(finallyArgs, other.finallyArgs))
				return false;
			if (maxMsg != other.maxMsg)
				return false;
			if (maxSeg != other.maxSeg)
				return false;
			if (!Arrays.equals(spectedValues, other.spectedValues))
				return false;
			return true;
		}
		private ExtraCmdManager getEnclosingInstance() {
			return ExtraCmdManager.this;
		}
	}
	private ModelExtraCommand pickExtraCommand(String name) {
		return ModelExtraCommand.findByName(name);
	}
}
