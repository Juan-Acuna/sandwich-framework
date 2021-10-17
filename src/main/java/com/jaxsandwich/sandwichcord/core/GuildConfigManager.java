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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CategoryObject;
import com.jaxsandwich.sandwichcord.models.CommandObject;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;

import net.dv8tion.jda.api.entities.Guild;
/**
 * [ES] Clase que maneja todas las configuraciones de servidores.(ex BotGuildsManager)<br>
 * [EN] Class which manage all the guild configurations.(ex BotGuildsManager)
 * @author Juancho
 * @version 2.1
 * @since 0.3.0
 */
public class GuildConfigManager {
	/**
	 * [ES] Bot asociado a este GuildConfigManager.<br>
	 * [EN] Bot associated to this GuildConfigManager.
	 */
	private Bot bot;
	/**
	 * [ES] Contenedor de {@link GuildConfig}(No confundir con {@link Guild}).<br>
	 * [EN] Container of {@link GuildConfig}(Do not confuse with {@link Guild}).
	 */
	private Map<Long, GuildConfig> guilds;
	/**
	 * [ES] Indica si el gestor de servidores actua en modo mono-servidor.<br>
	 * [EN] Indicates if the guilds manager works in single-guild mode.
	 */
	private boolean singleGuildMode = false;
	/**
	 * [ES] Configuración por defecto para todos los servidores en modo mono-servidor.<br>
	 * [EN] Default configuration for all guilds in single-guild mode.
	 */
	private GuildConfig defaultConfig = null;
	/**
	 * [ES] Constructor privado de GuildConfigManager.<br>
	 * [EN] Private constructor of GuildConfigManager.
	 * @param bot <br>[ES] bot que se asociará con este GuildManager. 
	 * [EN] bot who will be associated with this GuildManager.
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
	 * @param bot <br>[ES] bot que se asociará con este GuildManager. 
	 * [EN] bot who will be associated with this GuildManager.
	 * @return [ES] la instancia del GuildConfigManager. <br>[EN] the instance of GuildConfigManager.
	 */
	public static GuildConfigManager startService(Bot bot) {
		return new GuildConfigManager(bot);
	}
	/**
	 * [ES] Agrega una configuración de servidor al contenedor.<br>
	 * [EN] Adds a guild config to the container.
	 * @param config <br>[ES] configuración a agregar. [EN] configuration to add.
	 * @throws Exception [ES] no se puede registrar configuraciones en modo mono-servidor.<br>
	 * [EN] can't register configurations in single-guild mode.
	 * @return [ES] verdadero si se registró la configuración. 
	 * <br>[EN] true if the configuration was registered.
	 */
	public boolean registerGuild(GuildConfig config) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guild configurations in single-guild mode!");
		if(guilds.get(config.getId())==null) {
			guilds.put(config.getId(),config);
			return true;
		}
		return false;
	}
	/**
	 * [ES] Agrega una configuración de servidor al contenedor.<br>
	 * [EN] Adds a guild config to the container.
	 * @param guild <br>[ES] servidor a configurar. [EN] guild to configure.
	 * @param lang <br>[ES] idioma que se aplicará a la configuración.
	 * [EN] language to apply to the configuration.
	 * @throws Exception [ES] no se puede registrar configuraciones en modo mono-servidor.<br>
	 * [EN] can't register configurations in single-guild mode.
	 * @return [ES] la instancia de la configuración registrada.<br>
	 * [EN] the instance of the configuration registered.
	 */
	public GuildConfig registerGuild(Guild guild, Language lang) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guild configurations in single-guild mode!");
		GuildConfig g = new GuildConfig(guild, lang, this.bot);
		if(guilds.get(guild.getIdLong())==null) {
			guilds.put(guild.getIdLong(), g);
			return g;
		}else {
			return guilds.get(guild.getIdLong());
		}
	}
	/**
	 * [ES] Agrega una configuración de servidor al contenedor.<br>
	 * [EN] Adds a guild config to the container.
	 * @param id <br>[ES] ID del servidor a configurar. [EN] ID of the guild to configure.
	 * @param lang <br>[ES] idioma que se aplicará a la configuración.
	 * @throws Exception [ES] no se puede registrar configuraciones en modo mono-servidor.<br>
	 * [EN] can't register configurations in single-guild mode.
	 * @return [ES] la instancia de la configuración registrada.<br>
	 * [EN] the instance of the configuration registered.
	 */
	public GuildConfig registerGuild(long id, Language lang) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guild configurations in single-guild mode!");
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
	 * @param id <br>[ES] identificador del servidor. [EN] guild identifier.
	 * @return [ES] la configuración solicitada. Nulo si no encuentra.<br>
	 * [EN] the requested configuration. Null if not found.
	 */
	public GuildConfig getConfig(long id) {
		if(this.singleGuildMode)
			return this.defaultConfig;
		return guilds.get(id);
	}
	/**
	 * [ES] Obtiene la configuración para el servidor({@link Guild}).<br>
	 * [EN] Gets the configuration for the guild({@link Guild}).
	 * @param guild <br>[ES] servidor dueño de la configuración. [EN] guild that owns the configuration.
	 * @return [ES] la configuración solicitada. Nulo si no encuentra o guild es nulo.<br>
	 * [EN] the requested configuration. Null if not found or guild is null.
	 */
	public GuildConfig getConfig(Guild guild) {
		if(this.singleGuildMode)
			return this.defaultConfig;
		if(guild==null)
			return null;
		return guilds.get(guild.getIdLong());
	}
	/**
	 * [ES] Registra todas las configuraciones contenidas en una colección.<br>
	 * [EN] Registers all the configurations contained in a collection
	 * @param data <br>[ES] colección de configuraciones de servidor. 
	 * [EN] collection of guild configurations.
	 * @throws Exception [ES] no se puede registrar configuraciones en modo mono-servidor.<br>
	 * [EN] can't register configurations in single-guild mode.
	 */
	public void loadData(Collection<? extends GuildConfig> data) throws Exception {
		for(GuildConfig g : data) {
			registerGuild(g);
		}
	}
	/**
	 * [ES] Registra todas las configuraciones contenidas en un arreglo.<br>
	 * [EN] Registers all the configurations contained in the array.
	 * @param data <br>[ES] arreglo de configuraciones de servidor. 
	 * [EN] array of guild configurations.
	 * @throws Exception [ES] no se puede registrar configuraciones en modo mono-servidor.<br>
	 * [EN] can't register configurations in single-guild mode.
	 */
	public void loadData(GuildConfig[] data) throws Exception {
		for(GuildConfig g : data) {
			registerGuild(g);
		}
	}
	/**
	 * [ES] Devuelve el numero de servidores registrados.<br>
	 * [EN] Returns the count of registered guilds.
	 * @return [ES] cantidad de configuraciones registradas, -1 si el modo mono-servidor esta activado.<br>
	 * [EN] count of registered configurations, -1 if the single-guild mode is enabled.
	 */
	public int registeredGuildsCount() {
		if(this.singleGuildMode)
			return -1;
		return guilds.size();
	}
	/**
	 * [ES] Devuelve el numero de servidores registrados con los que el bot aun esta conectado.<br>
	 * [EN] Returns the count of registered guilds wich the bot is connected yet.
	 * @return [ES] cantidad de servidores, -1 si el modo mono-servidor esta activado.<br>
	 * [EN] count of guilds, -1 if the single-guild mode is enabled.
	 */
	public int joinedGuildsCount() {
		if(this.singleGuildMode)
			return -1;
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
