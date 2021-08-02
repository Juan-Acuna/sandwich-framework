package com.jaxsandwich.framework.models;

import com.jaxsandwich.framework.core.Bot;
import com.jaxsandwich.framework.models.discord.GuildConfig;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ExtraCmdPacket extends CommandPacket{
	private boolean authorOnly = false;
	private Object[] args;
	private Object[] eachArgs = null;
	private Object[] afterArgs = null;
	private Object[] noArgs = null;
	private Object[] finallyArgs = null;
	
	public ExtraCmdPacket(Bot bot, GuildConfig config, MessageReceivedEvent event, boolean isAuthorOnly, Object...args) {
		super(bot, config, event);
		this.args=args;
		this.authorOnly=isAuthorOnly;
	}
	public boolean isAuthorOnly() {
		return authorOnly;
	}
	public Object[] getArgs() {
		return args;
	}
	public Object getArgAt(int i) {
		return this.args[i];
	}
	public int getArgsLength() {
		return this.args.length;
	}
	public Object[] getEachArgs() {
		return eachArgs;
	}
	public Object[] getAfterArgs() {
		return afterArgs;
	}
	public Object[] getNoArgs() {
		return noArgs;
	}
	public Object[] getFinallyArgs() {
		return finallyArgs;
	}
}
