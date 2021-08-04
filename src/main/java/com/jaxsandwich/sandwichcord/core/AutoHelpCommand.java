package com.jaxsandwich.sandwichcord.core;

import java.util.List;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.*;
import com.jaxsandwich.sandwichcord.models.InputParameter.InputParamType;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Representa el comando de ayuda automático del bot.<br>
 * [EN] Represents the bot automatic help command.
 * @author Juan Acuña
 * @version 1.2
 */
public class AutoHelpCommand extends CommandBase{
	/**
	 * [ES] Bot al cual este AutoHelpCommand esta asociado.<br>
	 * [EN] Bot wich this AutoHelpCommand is associated.
	 */
	protected Bot bot;
	/**
	 * [ES] Constante que indica el identificador de este comando.<br>
	 * [EN] Constant wich Indicates this command identifier.
	 */
	public static final String AUTO_HELP_KEY = "Help";
	/**
	 * [ES] Inidica si los comandos y categorías etiquetadas como 'NSFW' deben acultarse al usar el comando de ayuda automático.<br>
	 * [EN] Indicates if the commands an categories tagged as 'NSFW' have to been hidden when using the automatic help command.
	 */
	protected boolean hide_nsfw_category = false;
	/**
	 * [ES] Constructor de la clase AutoHelpCommand.<br>
	 * [EN] Constructor of the AutoHelpCommand class.
	 */
	protected AutoHelpCommand(Bot bot) {
		super(AUTO_HELP_KEY);
		this.hide_nsfw_category=bot.isHideNSFWCategory();
		this.setName(Language.EN, "Help");
		this.setName(Language.ES, "Ayuda");
		String[] a_en = {"h","hlp","?"};
		String[] a_es = {"a","ayda","?"};
		this.setAlias(Language.EN, a_en);
		this.setAlias(Language.ES, a_es);
		this.bot=bot;
	}
	/**
	 * [ES] Metodo que ejecuta el comando de ayuda.<br>
	 * [EN] Method that executes the help command.
	 */
	public void help(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		Language actualLang = Language.ES;
		GuildConfig actualGuild = null;
		String searchQuery = null;
		EmbedBuilder eb = null;
		boolean cmdPass = false;
		boolean catPass = false;
		if(packet.isFromGuild()) {
			actualGuild = packet.getGuildsManager().getConfig(e.getGuild().getIdLong());
			if(actualGuild!=null)
				actualLang = actualGuild.getLanguage();
		}
		for(InputParameter ip : packet.getParameters()) {
			if(ip.getType()==InputParamType.NO_STANDAR) {
				searchQuery = ip.getValueAsString();
			}
		}
		if(searchQuery!=null) {
			ModelCommand sCmd = null;
			ModelCategory sCat = null;
			boolean iscmd = searchQuery.startsWith("$");
			for(ModelCommand mc : ModelCommand.getAsList()) {
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
				eb = setForCommand(eb,sCmd,actualLang,!cmdPass);
			}else {
				for(ModelCategory mc : ModelCategory.getAsList()) {
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
					eb = setForCategory(eb,sCat,actualLang,true,!catPass,actualGuild);
				}else if(sCat!=null & sCmd==null) {
					eb = setForCategory(eb,sCat,actualLang,false,!catPass,actualGuild);
				}else {
					eb = setForCommand(eb,sCmd,actualLang,!cmdPass);
				}
			}
		}else {
			eb = new EmbedBuilder();
			eb.setTitle(Values.value("xyz-sndwch-def-hlp-title", actualLang));
			eb.setDescription(Values.value("xyz-sndwch-def-hlp-desc", actualLang));
			eb.addField("", Values.value("xyz-sndwch-def-hlp-cats", actualLang), false);
			for(ModelCategory category : ModelCategory.getAsList()) {
				if(this.hide_nsfw_category && category.isNsfw() && !packet.getTextChannel().isNSFW()) {
					continue;
				}
				if(!category.isVisible()) {
					continue;
				}
				eb.addField(category.getName(actualLang),category.getDesc(actualLang), false);
				String cmds="";
				for(ModelCommand command : category.getCommands()) {
					if(!command.isVisible() || command.isNsfw()) {
						continue;
					}
					cmds += "`"+command.getName(actualLang)+(command.isEnabled()?"` ":"("+Values.value("xyz-sndwch-def-t-na", actualLang)+")` ");
				}
				eb.addField(Values.formatedValue("xyz-sndwch-def-hlp-catcmd", actualLang, cmds),Values.formatedValue("xyz-sndwch-def-hlp-cathint", actualLang,packet.getBot().getPrefix(),category.getName(actualLang)),false);
			}
		}
		if(eb==null) {
			eb=new EmbedBuilder();
			eb.setTitle(Values.value("xyz-sndwch-def-hlp-cat-nf", actualLang));
		}
		e.getChannel().sendMessageEmbeds(eb.build()).queue();
	}
	/**
	 * [ES] Metodo auxiliar para imprimir la ayuda de un comando.<br>
	 * [EN] Auxiliary method for printing the help of a command.
	 */
	private EmbedBuilder setForCommand(EmbedBuilder eb, ModelCommand cmd, Language lang, boolean pass) {
		if(!pass || cmd.isNsfw())
			return null;
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
						als2 += ", " + bot.getOptionsPrefix() + a;
					}
					als2=" _`[Alias: " + als2.substring(1) + "]`_";
				}
				eb.addField("> " + (option.isEnabled()?"":"*("+Values.value("xyz-sndwch-def-t-na", lang)+")* ~") + bot.getOptionsPrefix() + option.getName(lang) + als2  + (option.isEnabled()?"":"~"), ">>> " + option.getDesc(lang), false);
			}
		}
		return eb;
	}
	/**
	 * [ES] Metodo auxiliar para imprimir la ayuda de una categoría.<br>
	 * [EN] Auxiliary method for printing the help of a category.
	 */
	private EmbedBuilder setForCategory(EmbedBuilder eb, ModelCategory cat, Language lang, boolean duplicated,boolean catPass, GuildConfig guild) {
		if(!catPass) {
			return null;
		}
		eb = new EmbedBuilder();
		eb.setTitle(Values.formatedValue("xyz-sndwch-def-hlp-cattitle", lang, cat.getName(lang)));
		eb.setDescription((duplicated?Values.formatedValue("xyz-sndwch-def-hlp-dup", lang, cat.getName(lang))+"\n":"")+cat.getDesc(lang));
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
			eb.addField("> " + (command.isEnabled()?"":"*("+Values.value("xyz-sndwch-def-t-na", lang)+")* ") + command.getName(lang) + als, ">>> " + command.getDesc(lang) + (command.isEnabled()?Values.formatedValue("xyz-sndwch-def-hlp-cmdhint", lang, bot.getPrefix(), command.getName(lang).toLowerCase()):""),false);
		}
	return eb;
	}
	/**
	 * [ES] Devuelve verdadero si el la protección de contenido NSFW esta activada en este comando.<br>
	 * [EN] Returns true if the NSFW protection is enabled in this command.
	 */
	public boolean isHideNSFWCategory() {
		return hide_nsfw_category;
	}
	/**
	 * [ES] Configura si el la protección de contenido NSFW esta activada en este comando.<br>
	 * [EN] Sets if the NSFW protection is enabled in this command.
	 */
	public void setHideNSFWCategory(boolean hide) {
		this.hide_nsfw_category = hide;
	}
}
