package xyz.sandwichframework.core;

import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.*;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.discord.ModelGuild;

/**
 * Representa el comando de ayuda automático del bot.
 * Represents the bot automatic help command.
 * @author Juan Acuña
 * @version 0.6
 */
public class AutoHelpCommand {
	public static final String AUTO_HELP_KEY = "help";
	public static String[] getHelpOptions(Language lang) {
		switch(LanguageHandler.getLanguageParent(lang)) {
			case ES:
				String[] s = {"ayuda","ay"};
				return s;
			case EN:
				String[] s1 = {"help","h"};
				return s1;
			default:
				String[] s2 = {};
				return s2;
		}
	}
	public static void help(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		ModelGuild actualGuild = BotGuildsManager.getManager().getGuild(e.getGuild().getId());
		String categoryQuery = null;
		for(InputParameter ip : parametros) {
			if(ip.getType()==InputParamType.Custom) {
				categoryQuery = ip.getValueAsString();
			}
		}
		String extra = LanguageHandler.helpHint(actualGuild.getLanguage());
		EmbedBuilder eb = new EmbedBuilder();
		BotRunner runner = BotRunner._self;
		if(categoryQuery!=null) {
			for(ModelCategory category : runner.categories) {
				if(category.getName(actualGuild.getLanguage()).equalsIgnoreCase(categoryQuery)) {
					eb.setTitle(LanguageHandler.specialWords(actualGuild.getLanguage(), "category") + ": "+category.getName(actualGuild.getLanguage()));
					eb.setDescription(category.getDesc(actualGuild.getLanguage()));
					eb.addField("","*"+ LanguageHandler.specialWords(actualGuild.getLanguage(), "commands") + "*",false);
					for(ModelCommand command : category.getCommands()) {
						if(!command.isVisible()) {
							continue;
						}
						String als = "";
						if(command.getAlias(actualGuild.getLanguage()).length>0) {
							for(String a : command.getAlias(actualGuild.getLanguage())) {
								als += ", " + a;
							}
							als=" _`[Alias: " + als.substring(1) + "]`_";
						}
						
						eb.addField("> " + (command.isEnabled()?"":"*("+LanguageHandler.notAvailable(actualGuild.getLanguage())+")* ") + command.getName(actualGuild.getLanguage()) + als, ">>> " + command.getDesc(actualGuild.getLanguage()) + (command.isEnabled()?String.format(extra,runner.commandsPrefix, command.getName(actualGuild.getLanguage()).toLowerCase(),runner.optionsPrefix):""),false);
					}
					break;
				}
			}
		}else {
			eb.setTitle(runner.help_title);
			eb.setDescription(runner.help_description);
			eb.addField("", LanguageHandler.specialWords(actualGuild.getLanguage(), "categories"), false);
			for(ModelCategory category : runner.categories) {
				if(runner.hide_nsfw_category && category.isNsfw() && !e.getTextChannel().isNSFW()) {
					continue;
				}
				if(!category.isVisible()) {
					continue;
				}
				eb.addField(category.getName(actualGuild.getLanguage()),category.getDesc(actualGuild.getLanguage()), false);
				eb.addField("> N° " + LanguageHandler.specialWords(actualGuild.getLanguage(), "commands"),"> " + category.getCommands().size(),false);
			}
		}
		e.getChannel().sendMessage(eb.build()).queue();
	}
	
	public static void sendHelp(MessageChannel channel, String command) {
		ModelGuild actualGuild = BotGuildsManager.getManager().getGuild(((TextChannel)channel).getGuild().getId());
		EmbedBuilder eb = new EmbedBuilder();
		BotRunner runner = BotRunner._self;
		for(ModelCommand cmd : runner.commands) {
			if(cmd.getName(actualGuild.getLanguage()).toLowerCase().equalsIgnoreCase(command.toLowerCase())) {
				String als = "";
				if(cmd.getAlias(actualGuild.getLanguage()).length>0) {
					for(String a : cmd.getAlias(actualGuild.getLanguage())) {
						als += ", " + a;
					}
					als=" _`[Alias: " + als.substring(1) + "]`_";
				}
				eb.setTitle(cmd.getName(actualGuild.getLanguage()) + als + " | "+LanguageHandler.specialWords(actualGuild.getLanguage(), "category")+": " + cmd.getCategory().getName(actualGuild.getLanguage()));
				eb.setDescription(cmd.getDesc(actualGuild.getLanguage()));
				eb.addField("",LanguageHandler.specialWordsUppercased(actualGuild.getLanguage(), "parameters")+"/"+LanguageHandler.specialWordsUppercased(actualGuild.getLanguage(), "options")+":",false);
				if(cmd.getParameter(actualGuild.getLanguage())!=null) {
					eb.addField("> "+LanguageHandler.specialWords(actualGuild.getLanguage(), "parameter")+": " + cmd.getParameter(actualGuild.getLanguage()),">>> " + cmd.getParameterDesc(actualGuild.getLanguage()), false);
				}
				if(cmd.getOptions().size()>0) {
					for(ModelOption option : cmd.getOptions()) {
						if(!option.isVisible()) {
							continue;
						}
						String als2 = "";
						
						if(option.getAlias(actualGuild.getLanguage()).length>0) {
							for(String a : option.getAlias(actualGuild.getLanguage())) {
								als2 += ", " + runner.optionsPrefix + a;
							}
							als2=" _`[Alias: " + als2.substring(1) + "]`_";
						}
						eb.addField("> " + (option.isEnabled()?"":"*("+LanguageHandler.notAvailable(actualGuild.getLanguage())+")* ~") + runner.optionsPrefix + option.getName(actualGuild.getLanguage()) + als2  + (option.isEnabled()?"":"~"), ">>> " + option.getDesc(actualGuild.getLanguage()), false);
					}
				}
				break;
			}
		}
		channel.sendMessage(eb.build()).queue();
	}
}
