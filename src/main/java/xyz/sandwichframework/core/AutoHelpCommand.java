package xyz.sandwichframework.core;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.LanguageHandler;
import xyz.sandwichframework.models.*;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.discord.ModelGuild;
/**
 * Representa el comando de ayuda automático del bot.
 * Represents the bot automatic help command.
 * @author Juan Acuña
 * @version 1.0
 */
public class AutoHelpCommand {
	public static final String AUTO_HELP_KEY = "help";
	private static BotRunner runner = BotRunner._self;
	
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
		Language actualLang = Language.ES;
		ModelGuild actualGuild = null;
		String searchQuery = null;
		EmbedBuilder eb = null;
		boolean cmdPass = false;
		boolean catPass = false;
		if(e.isFromGuild()) {
			actualGuild = BotGuildsManager.getManager().getGuild(e.getGuild().getIdLong());
			if(actualGuild!=null)
				actualLang = actualGuild.getLanguage();
		}
		for(InputParameter ip : parametros) {
			if(ip.getType()==InputParamType.Custom) {
				searchQuery = ip.getValueAsString();
			}
		}
		if(searchQuery!=null) {
			ModelCommand sCmd = null;
			ModelCategory sCat = null;
			boolean iscmd = searchQuery.startsWith("$");
			for(ModelCommand mc : runner.commands) {
				if(mc.getName(actualLang).equalsIgnoreCase(searchQuery) || searchQuery.equalsIgnoreCase("$"+mc.getName(actualLang))) {
					sCmd=mc;
					if(actualGuild!=null) {
						cmdPass = !actualGuild.isCommandAllowed(mc.getId())
								|| !actualGuild.isCategoryAllowed(mc.getCategory().getId())
								|| !actualGuild.isMemberAllowed(e.getMember().getId())
								|| !actualGuild.isChannelAllowed(e.getChannel().getId());
						List<Role> lr = e.getMember().getRoles();
						if(lr.size()>0) {
							cmdPass = cmdPass || !actualGuild.isRoleAllowed(e.getMember().getRoles().get(0).getId());
						}
					}
					break;
				}
				for(String al : mc.getAlias(actualLang)) {
					if(al.equalsIgnoreCase(searchQuery) || searchQuery.equalsIgnoreCase("$"+al)) {
						sCmd=mc;
						if(actualGuild!=null) {
							cmdPass = !actualGuild.isCommandAllowed(mc.getId())
									|| !actualGuild.isCategoryAllowed(mc.getCategory().getId())
									|| !actualGuild.isMemberAllowed(e.getMember().getId())
									|| !actualGuild.isChannelAllowed(e.getChannel().getId());
							List<Role> lr = e.getMember().getRoles();
							if(lr.size()>0) {
								cmdPass = cmdPass || !actualGuild.isRoleAllowed(e.getMember().getRoles().get(0).getId());
							}
						}
						break;
					}
				}
			}
			if(iscmd) {
				setForCommand(eb,sCmd,actualLang,!cmdPass);
			}else {
				for(ModelCategory mc : runner.categories) {
					if(mc.getName(actualLang).equalsIgnoreCase(searchQuery)) {
						sCat=mc;
						if(actualGuild!=null) {
							catPass = !actualGuild.isCategoryAllowed(mc.getId())
									|| !actualGuild.isMemberAllowed(e.getMember().getId())
									|| !actualGuild.isChannelAllowed(e.getChannel().getId());
							List<Role> lr = e.getMember().getRoles();
							if(lr.size()>0) {
								catPass = catPass || !actualGuild.isRoleAllowed(e.getMember().getRoles().get(0).getId());
							}
						}
						break;
					}
				}
				if(sCat!=null && sCmd!=null) {
					setForCategory(eb,sCat,actualLang,true,!catPass,actualGuild);
				}else if(sCat!=null & sCmd==null) {
					setForCategory(eb,sCat,actualLang,false,!catPass,actualGuild);
				}else {
					setForCommand(eb,sCmd,actualLang,!cmdPass);
				}
			}
		}else {
			eb = new EmbedBuilder();
			eb.setTitle(Values.value("xyz-sndwch-def-hlp-title", actualLang));
			eb.setDescription(Values.value("xyz-sndwch-def-hlp-desc", actualLang));
			eb.addField("", Values.value("xyz-sndwch-def-hlp-cats", actualLang), false);
			for(ModelCategory category : runner.categories) {
				if(runner.hide_nsfw_category && category.isNsfw() && !e.getTextChannel().isNSFW()) {
					continue;
				}
				if(!category.isVisible()) {
					continue;
				}
				eb.addField(category.getName(actualLang),category.getDesc(actualLang), false);
				String cmds="";
				for(ModelCommand command : category.getCommands()) {
					if(!command.isVisible()) {
						continue;
					}
					cmds += "`"+command.getName(actualLang)+(command.isEnabled()?"` ":"("+Values.value("xyz-sndwch-def-t-na", actualLang)+")` ");
				}
				eb.addField(Values.formatedValue("xyz-sndwch-def-hlp-catcmd", actualLang, cmds),Values.formatedValue("xyz-sndwch-def-hlp-cathint", actualLang,runner.commandsPrefix,category.getName(actualLang)),false);
			}
		}
		if(eb==null) {
			eb=new EmbedBuilder();
			eb.setTitle("NO SE ENCONTRO LA CATEGORIA/COMANDO.");
		}
		e.getChannel().sendMessage(eb.build()).queue();
	}
	
	private static void setForCommand(EmbedBuilder eb, ModelCommand cmd, Language lang, boolean pass) {
		if(!pass) {
			return;
		}
		eb = new EmbedBuilder();
		String als = "";
		if(cmd.getAlias(lang).length>0) {
			for(String a : cmd.getAlias(lang)) {
				als += ", " + a;
			}
			als=" _`[Alias: " + als.substring(1) + "]`_";
		}
		eb.setTitle(cmd.getName(lang) + als + " | "+Values.formatedValue("xyz-sndwch-def-hlp-cattitle", lang, cmd.getCategory().getName(lang)));
		eb.setDescription(cmd.getDesc(lang));
		eb.addField("",Values.value("xyz-sndwch-def-hlp-cmd-opts", lang),false);
		if(cmd.getParameter(lang)!=null) {
			eb.addField("> "+Values.formatedValue("xyz-sndwch-def-hlp-cmd-opt", lang, cmd.getParameter(lang)),">>> " + cmd.getParameterDesc(lang), false);
		}
		if(cmd.getOptions().size()>0) {
			for(ModelOption option : cmd.getOptions()) {
				if(!option.isVisible()) {
					continue;
				}
				String als2 = "";
				
				if(option.getAlias(lang).length>0) {
					for(String a : option.getAlias(lang)) {
						als2 += ", " + runner.optionsPrefix + a;
					}
					als2=" _`[Alias: " + als2.substring(1) + "]`_";
				}
				eb.addField("> " + (option.isEnabled()?"":"*("+Values.value("xyz-sndwch-def-t-na", lang)+")* ~") + runner.optionsPrefix + option.getName(lang) + als2  + (option.isEnabled()?"":"~"), ">>> " + option.getDesc(lang), false);
			}
		}
	}
	private static void setForCategory(EmbedBuilder eb, ModelCategory cat, Language lang, boolean duplicated,boolean catPass, ModelGuild guild) {
		if(!catPass) {
			return;
		}
		eb = new EmbedBuilder();
		eb.setTitle(Values.formatedValue("xyz-sndwch-def-hlp-cattitle", lang, cat.getName(lang)));/*************************************CAMBIAR**************************************/
		eb.setDescription((duplicated?Values.formatedValue("xyz-sndwch-def-hlp-dup", lang, cat.getName(lang))+"\n":"")+cat.getDesc(lang));/******************************************/
		for(ModelCommand command : cat.getCommands()) {
			if(!command.isVisible()) {
				continue;
			}
			if(!guild.isCommandAllowed(command.getId())) {
				continue;
			}
			String als = "";
			if(command.getAlias(lang).length>0) {
				for(String a : command.getAlias(lang)) {
					als += ", " + a;
				}
				als=" _`[Alias: " + als.substring(1) + "]`_";
			}
			eb.addField("> " + (command.isEnabled()?"":"*("+Values.value("xyz-sndwch-def-t-na", lang)+")* ") + command.getName(lang) + als, ">>> " + command.getDesc(lang) + (command.isEnabled()?Values.formatedValue("xyz-sndwch-def-hlp-cmdhint", lang,runner.commandsPrefix, command.getName(lang).toLowerCase()):""),false);
		}
	}
}
