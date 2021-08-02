package com.jaxsandwich.framework.models;

import java.util.ArrayList;

import com.jaxsandwich.framework.core.Bot;
import com.jaxsandwich.framework.core.BotRunner;
import com.jaxsandwich.framework.core.ExtraCmdManager;
import com.jaxsandwich.framework.core.GuildsManager;
import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.models.discord.GuildConfig;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.StoreChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * Paquete con todo el contenido necesario para un comando.
 * Packet with all the required content for a command.
 * @author Juancho
 * @version 1.0
 */
public class CommandPacket {
	protected boolean fromGuild = false;
	protected boolean isWebhookMessage = false;
	protected Bot bot;
	protected ExtraCmdManager extraCmdManager;
	protected GuildsManager guildsManager;
	protected ArrayList<InputParameter> parameters = null;
	protected MessageReceivedEvent messageReceived;
	protected GuildConfig guildConfig = null;
	protected String messageContent = null;
	protected String authorId = null;
	protected Language prefLang = null;
	protected ChannelType channelType;
	protected MessageChannel channel = null;
	protected TextChannel textChannel = null;
	protected PrivateChannel privateChannel = null;
	protected StoreChannel storeChannel = null;
	//protected Category category = null;
	
	public CommandPacket(Bot bot, GuildConfig config, MessageReceivedEvent event) {
		this.bot=bot;
		this.extraCmdManager=bot.getExtraCmdManager();
		this.messageReceived=event;
		this.guildsManager=bot.getGuildsManager();
		this.guildConfig=config;
		if(this.guildConfig!=null) {
			this.prefLang=this.guildConfig.getLanguage();
		}else {
			this.prefLang=this.bot.getDefaultLanguage();
		}
		this.fromGuild=event.isFromGuild();
		this.isWebhookMessage=event.isWebhookMessage();
		this.channel=event.getChannel();
		this.messageContent=event.getMessage().getContentRaw();
		this.authorId=event.getAuthor().getId();
		loadChannels(channel);
	}
	public CommandPacket(Bot bot, ArrayList<InputParameter> parameters, MessageReceivedEvent event) {
		this.bot=bot;
		this.extraCmdManager=bot.getExtraCmdManager();
		this.guildsManager=bot.getGuildsManager();
		this.parameters=parameters;
		this.messageReceived=event;
		this.fromGuild=event.isFromGuild();
		this.isWebhookMessage=event.isWebhookMessage();
		this.guildConfig=guildsManager.getConfig(event.getGuild());
		if(this.guildConfig!=null) {
			this.prefLang=this.guildConfig.getLanguage();
		}else {
			this.prefLang=this.bot.getDefaultLanguage();
		}
		this.channel=event.getChannel();
		this.authorId=event.getAuthor().getId();
		this.messageContent=event.getMessage().getContentRaw();
		loadChannels(channel);
	}
	protected void loadChannels(MessageChannel channel) {
		this.channelType=channel.getType();
		switch(this.channelType) {
		case TEXT:
			this.textChannel=(TextChannel) channel;
			break;
		case PRIVATE:
			this.privateChannel=(PrivateChannel) channel;
			break;
		case STORE:
			this.storeChannel=(StoreChannel) channel;
			break;
		/*case CATEGORY:
			this.category=(Category) channel;
			break;*/
		default:
			break;
		}
	}
	public Bot getBot() {
		return bot;
	}
	public ExtraCmdManager getExtraCmdManager() {
		return extraCmdManager;
	}
	public GuildsManager getGuildsManager() {
		return guildsManager;
	}
	public ArrayList<InputParameter> getParameters() {
		return parameters;
	}
	public MessageReceivedEvent getMessageReceivedEvent() {
		return messageReceived;
	}
	public boolean isFromGuild() {
		return this.fromGuild;
	}
	public boolean isWebhookMessage() {
		return isWebhookMessage;
	}
	public GuildConfig getModelGuild() {
		return this.guildConfig;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public String getAuthorId() {
		return authorId;
	}
	public Language getPreferredLang() {
		return prefLang;
	}
	public MessageChannel getChannel() {
		return channel;
	}
	public TextChannel getTextChannel() {
		return this.textChannel;
	}
	public PrivateChannel getPrivateChannel() {
		return this.privateChannel;
	}
	public StoreChannel getStoreChannel() {
		return this.storeChannel;
	}
	/*public Category getCategoryChannel() {
		return this.category;
	}*/
	public ChannelType getChannelType() {
		return channelType;
	}
}
