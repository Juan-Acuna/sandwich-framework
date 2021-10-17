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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CategoryObject;
import com.jaxsandwich.sandwichcord.models.CommandObject;
import com.jaxsandwich.sandwichcord.models.OptionObject;
import com.jaxsandwich.sandwichcord.models.SlashCommandObject;
import com.jaxsandwich.sandwichcord.models.SlashSubCommand;
import com.jaxsandwich.sandwichcord.models.SlashSubGroup;
import com.jaxsandwich.sandwichcord.models.actionable.IActionable;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

/**
 * [ES] Getiona los registros de comandos slash en Discord. También permite la compatibilidad entre comandos.<br>
 * [EN] Manages the slash command registers of Discord. Also allows compatibility between commands.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public class CommandManager {
	/**
	 * [EN] Contenedor de comandos slash.<br>
	 * [ES] Container of slash commands.
	 */
	private static Map<String, IActionable<?>> scommands = Collections.synchronizedMap(new HashMap<String, IActionable<?>>());
	/**
	 * [EN] Registra un comando slash.<br>
	 * [ES] Registers a slash command.
	 * @param command <br>[ES] commando a registrar.[EN] command to register.
	 */
	public static void registerSlashCommand(SlashCommandObject command) {
		scommands.put(command.getId(), command);
	}
	/**
	 * [EN] Registra un comando como comando slash.<br>
	 * [ES] Registers a command as slash command.
	 * @param command <br>[ES] commando a registrar.[EN] command to register.
	 */
	public static void registerSlashCommand(CommandObject command) {
		scommands.put(command.getId(), command);
	}
	/**
	 * [EN] Busca en el contenedor de comandos slash el comando correspondiente.<br>
	 * [ES] Search in the slash commands container for the corresponding command.
	 * @param id <br>[ES] identificador del comando.[EN] identifier of the command.
	 * @return [ES] comando como IActionable(si se encuentra alguno).<br>
	 * [EN] command as IActionable(if any is found).
	 */
	public static IActionable<?> findSlashCommand(String id) {
		return scommands.get(id);
	}
	/**
	 * [EN] Sincroniza los registros de comandos slash de Discord con el contenedor de comandos slash.<br>
	 * [ES] Synchronizes the command slash register of Discord with the slash commands container.
	 * @param bot <br>[ES] bot asociado
	 * @param forceGlobalReset <br>[ES] true para reescribir los comandos slash globales. 
	 * [EN] true for reset the global slash commands.
	 * @param forceGuildsReset <br>[ES] true para reescribir los comandos slash de servidores. 
	 * [EN] true for reset the guild slash commands.
	 */
	public static void synchronize(Bot bot, boolean forceGlobalReset, boolean forceGuildsReset) {
		Language defLang = bot.getDefaultLanguage();
		if(forceGlobalReset) {
			List<Command> cs = bot.getJDA().retrieveCommands().complete();
			for(Command c : cs) {
				c.delete().complete();
			}
		}
		if(forceGuildsReset) {
			for(Guild g : bot.getJDA().getGuilds()) {
				List<Command> cs = g.retrieveCommands().complete();
				for(Command c : cs) {
					c.delete().complete();
				}
			}
		}
		HashMap<String, CommandData> cats = new HashMap<String, CommandData>();
		HashMap<String, CategoryObject> catsforguilds = new HashMap<String, CategoryObject>();
		for(String c : scommands.keySet()) {
			IActionable<?> cmd = scommands.get(c);
			if(cmd instanceof CommandObject) {
				if(cats.get(((CommandObject) cmd).getCategory().getId())==null) {
					catsforguilds.put(((CommandObject) cmd).getCategory().getId(), ((CommandObject) cmd).getCategory());
					cats.put(((CommandObject) cmd).getCategory().getId(),new CommandData(((CommandObject) cmd).getCategory().getId(), ((CommandObject) cmd).getCategory().getDesc(defLang)));
				}
				CommandData cd = cats.get(((CommandObject) cmd).getCategory().getId());
				SubcommandData s = new SubcommandData(((CommandObject) cmd).getId(), ((CommandObject) cmd).getDesc(defLang));
				for(OptionObject o : ((CommandObject) cmd).getOptions()) {
					s.addOption(o.getType(), o.getId(), o.getDesc(defLang),o.isRequired());
				}
				cd.addSubcommands(s);
			}else if(cmd instanceof SlashCommandObject) {
				if(((SlashCommandObject) cmd).getGuilds()!=null && ((SlashCommandObject) cmd).getGuilds().length>0) {
					for(String g : ((SlashCommandObject) cmd).getGuilds()) {
						Guild guild = bot.getJDA().getGuildById(g);
						if(guild!=null)
							guild.upsertCommand(computeCommand((SlashCommandObject)cmd,defLang)).queue();
					}
				}else {
					bot.getJDA().upsertCommand(computeCommand((SlashCommandObject)cmd,defLang)).queue();
				}
			}
		}
		if(cats.size()>0) {
			for(String id : cats.keySet()) {
				CategoryObject c = catsforguilds.get(id);
				if(c.getGuilds()!=null && c.getGuilds().length>0) {
					for(String g : c.getGuilds()) {
						Guild guild = bot.getJDA().getGuildById(g);
						if(guild!=null)
							guild.upsertCommand(cats.get(id)).queue();
					}
				}else {
					bot.getJDA().upsertCommand(cats.get(id)).queue();
				}
			}
		}
	}
	/**
	 * [EN] Transforma un comando slash en un CommandData, formato usado por JDA.<br>
	 * [ES] Turns a slash command into a CommandData object, format used by JDA.
	 * @param cmd <br>[ES] comando a computar. [EN] command to compute.
	 * @param defLang <br>[ES] idioma con el que se computará el comando. 
	 * [EN] language which the command will be computed.
	 * @return [ES] comando computado a formato compatible con JDA. <br>
	 * [EN] computed command in JDA compatible format.
	 */
	protected static CommandData computeCommand(SlashCommandObject cmd, Language defLang) {
		SubcommandData[] subcommands = null;
		SubcommandGroupData[] subgroups = null;
		if(cmd.hasSubGroups()) {
			int subg = cmd.getSubGroups().length;
			subgroups = new SubcommandGroupData[subg];
			for(int i=0;i<subg;i++) {
				SlashSubGroup sg = cmd.getSubGroups()[i];
				subgroups[i] = new SubcommandGroupData(sg.getName().toLowerCase(),sg.getDescription().toLowerCase());
				if(sg.getSubCommands()!=null && sg.getSubCommands().length>0) {
					int subc = sg.getSubCommands().length;
					subcommands = new SubcommandData[subc];
					for(int j=0;j<subc;j++) {
						SlashSubCommand sc = sg.getSubCommands()[j];
						subcommands[j] = new SubcommandData(sc.getName().toLowerCase(),sc.getDescription().toLowerCase());
						for(OptionObject o : sc.getOptions()) {
							subcommands[j].addOption(o.getType(),o.getId(), o.getDesc(defLang).toLowerCase(), o.isRequired());
						}
					}
					subgroups[i].addSubcommands(subcommands);
				}
			}
			return new CommandData(cmd.getId(),cmd.getDesc().toLowerCase()).addSubcommandGroups(subgroups);
		}else if(cmd.hasSubCommands()){
			int subc = cmd.getSubCommands().length;
			subcommands = new SubcommandData[subc];
			for(int i=0;i<subc;i++) {
				SlashSubCommand sc = cmd.getSubCommands()[i];
				subcommands[i] = new SubcommandData(sc.getName().toLowerCase(),sc.getDescription().toLowerCase());
				for(OptionObject o : sc.getOptions()) {
					subcommands[i].addOption(o.getType(),o.getId(), o.getDesc(defLang).toLowerCase(), o.isRequired());
				}
			}
			return new CommandData(cmd.getId(),cmd.getDesc().toLowerCase()).addSubcommands(subcommands);
		}else {
			return new CommandData(cmd.getId(),cmd.getDesc().toLowerCase());
		}
	}
}
