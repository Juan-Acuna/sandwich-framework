package xyz.sandwichframework.models;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.core.Bot;
import xyz.sandwichframework.models.discord.ModelGuild;

public class ExtraCmdPacket extends CommandPacket{
	private boolean authorOnly = false;
	private Object[] args;
	private Object[] eachArgs = null;
	private Object[] afterArgs = null;
	private Object[] noArgs = null;
	private Object[] finallyArgs = null;
	
	public ExtraCmdPacket(Bot bot, ModelGuild guild, MessageReceivedEvent event, boolean isAuthorOnly, Object...args) {
		super(bot, guild, event);
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
