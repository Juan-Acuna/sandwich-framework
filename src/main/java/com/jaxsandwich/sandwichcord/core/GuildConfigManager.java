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
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.entities.Guild;
/**
 * [ES] Clase que manejar todos los servidores en los que el bot ha sido invitado.(ex BotGuildsManager)<br>
 * [EN] Class which manage all the guilds that the bot has been invited.(ex BotGuildsManager)
 * @author Juancho
 * @version 2.0
 */
public class GuildConfigManager {
	/**
	 * [ES] Bot asociado a este GuildConfigManager.<br>
	 * [EN] Bot associated to this GuildConfigManager.
	 */
	private Bot bot;
	/**
	 * [ES] Contenedor de {@link GuildConfig}(No confundir con {@link Servidor}).<br>
	 * [EN] Container of {@link GuildConfig}(Do not confuse with {@link Servidor}).
	 */
	private Map<Long, GuildConfig> guilds;
	/**
	 * [ES] Indica si el gestor de servidores actua en modo monoServidor.<br>
	 * [EN] Indicates if the guilds manager works in singleGuild mode.
	 */
	private boolean singleGuildMode = false;
	/**
	 * [ES] Configuración por defecto para todos los servidores en modo monoServidor.<br>
	 * [EN] Default configuration for all guilds in singleGuild mode.
	 */
	private GuildConfig defaultConfig = null;
	/**
	 * [ES] Constructor privado de GuildConfigManager.<br>
	 * [EN] Private constructor of GuildConfigManager.
	 */
	private GuildConfigManager(Bot bot) {
		this.guilds = Collections.synchronizedMap(new HashMap<Long, GuildConfig>());
		this.bot=bot;
		if(this.bot.isSingleGuildMode()) {
			this.singleGuildMode=true;
			this.defaultConfig = new GuildConfig(this.bot);
			if(this.bot.isHideNSFWCategory())
				this.configNSFWProtection();
		}
	}
	/**
	 * [ES] Inicia una nueva instancia de esta clase.<br>
	 * [EN] Starts a new instance of this class.
	 */
	public static GuildConfigManager startService(Bot bot) {
		return new GuildConfigManager(bot);
	}
	/**
	 * [ES] Agrega un servidor al contenedor.<br>
	 * [EN] Adds a guildConfig to the container.
	 * @throws Exception 
	 */
	public boolean registerGuild(GuildConfig guild) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guilds in single-guildConfig mode!");
		if(guilds.get(guild.getId())==null) {
			guilds.put(guild.getId(),guild);
			return true;
		}
		return false;
	}
	/**
	 * [ES] Agrega un servidor al contenedor.<br>
	 * [EN] Adds a guildConfig to the container.
	 * @throws Exception 
	 */
	public GuildConfig registerGuild(Guild guild, Language lang) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guilds in single-guildConfig mode!");
		GuildConfig g = new GuildConfig(guild, lang, this.bot);
		if(guilds.get(guild.getIdLong())==null) {
			guilds.put(guild.getIdLong(), g);
			return g;
		}else {
			return guilds.get(guild.getIdLong());
		}
	}
	/**
	 * [ES] Agrega un servidor al contenedor.<br>
	 * [EN] Adds a guildConfig to the container.
	 * @throws Exception 
	 */
	public GuildConfig registerGuild(long id, Language lang) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guilds in single-guildConfig mode!");
		GuildConfig g = new GuildConfig(bot.getJDA().getGuildById(id), lang, this.bot);
		if(guilds.get(id)==null) {
			guilds.put(id, g);
			return g;
		}else {
			return guilds.get(id);
		}
	}
	/**
	 * [ES] Obtiene la configuración para el servidor según el valor de {@link Guild#getIdLong()}.<br>
	 * [EN] Gets the configuration for the guild by it's {@link Guild#getIdLong()} value.
	 */
	public GuildConfig getConfig(long id) {
		if(this.singleGuildMode)
			return this.defaultConfig;
		return guilds.get(id);
	}
	/**
	 * [ES] Obtiene la configuración para el servidor({@link Guild}).<br>
	 * [EN] Gets the configuration for the guild({@link Guild}).
	 */
	public GuildConfig getConfig(Guild g) {
		if(this.singleGuildMode)
			return this.defaultConfig;
		return guilds.get(g.getIdLong());
	}
	/**
	 * [ES] Registra todas las configuraciones contenidas en un List.<br>
	 * [EN] Registers all the configurations contained in the List.
	 */
	public void loadData(List<? extends GuildConfig> data) throws Exception {
		for(GuildConfig g : data) {
			registerGuild(g);
		}
	}
	/**
	 * [ES] Registra todas las configuraciones contenidas en un arreglo.<br>
	 * [EN] Registers all the configurations contained in the array.
	 */
	public void loadData(GuildConfig[] data) throws Exception {
		for(GuildConfig g : data) {
			registerGuild(g);
		}
	}
	/**
	 * [ES] Devuelve el numero de servidores registrados.<br>
	 * [EN] Returns the count of registered guilds.
	 */
	public int registeredGuildsCount() throws Exception {
		if(this.singleGuildMode)
			throw new Exception("There is no registered guilds in single-guildConfig mode!");
		return guilds.size();
	}
	/**
	 * [ES] Devuelve el numero de servidores registrados con los que el bot aun esta conectado.<br>
	 * [EN] Returns the count of registered guilds wich the bot is connected yet.
	 */
	public int joinedGuildsCount() throws Exception {
		if(this.singleGuildMode)
			throw new Exception("There is no registered guilds in single-guildConfig mode!");
		int i=0;
		for(long id : guilds.keySet()) {
			if(guilds.get(id).isJoined()) {
				i++;
			}
		}
		return i;
	}
	/**
	 * [ES] Configura la proteccion NSFW. Solo disponible en modo monoServidor.<br>
	 * [EN] Configures the NSFW protection. Only available in singleGuild mode.
	 */
	private void configNSFWProtection() {
		for(CategoryObject c : CategoryObject.getAsList()) {
			if(c.isNsfw()) {
				this.defaultConfig.setAllowedCategory(c.getName(defaultConfig.getLanguage()), false);
			}else {
				for(CommandObject mc : c.getCommands()) {
					if(mc.isNsfw()) {
						this.defaultConfig.setAllowedCommand(mc.getName(defaultConfig.getLanguage()), false);
					}
				}
			}
		}
	}
}
