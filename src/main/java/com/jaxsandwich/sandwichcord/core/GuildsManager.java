package com.jaxsandwich.sandwichcord.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.ModelCategory;
import com.jaxsandwich.sandwichcord.models.ModelCommand;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.entities.Guild;
/**
 * [ES] Clase que manejar todos los servidores en los que el bot ha sido invitado.(ex BotGuildsManager)<br>
 * [EN] Class which manage all the guilds that the bot has been invited.(ex BotGuildsManager)
 * @author Juancho
 * @version 1.3
 */
public class GuildsManager {
	/**
	 * [ES] Bot asociado a este GuildsManager.<br>
	 * [EN] Bot associated to this GuildsManager.
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
	 * [ES] Constructor privado de GuildsManager.<br>
	 * [EN] Private constructor of GuildsManager.
	 */
	private GuildsManager(Bot bot) {
		this.guilds = Collections.synchronizedMap(new HashMap<Long, GuildConfig>());
		this.bot=bot;
		if(this.bot.isSingleGuildMode()) {
			this.singleGuildMode=true;
			this.defaultConfig = new GuildConfig();
			if(this.bot.isHideNSFWCategory())
				this.configNSFWProtection();
		}
	}
	/**
	 * [ES] Inicia una nueva instancia de esta clase.<br>
	 * [EN] Starts a new instance of this class.
	 */
	public static GuildsManager startSercive(Bot bot) {
		return new GuildsManager(bot);
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
		GuildConfig g = new GuildConfig(guild, lang);
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
		GuildConfig g = new GuildConfig(bot.getJDA().getGuildById(id), lang);
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
		if(this.singleGuildMode){
			try {
				this.defaultConfig.setReferencedGuild(bot.getJDA().getGuildById(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.defaultConfig;
		}
		return guilds.get(id);
	}
	
	/**
	 * [ES] Obtiene la configuración para el servidor({@link Guild}).<br>
	 * [EN] Gets the configuration for the guild({@link Guild}).
	 */
	public GuildConfig getConfig(Guild g) {
		if(this.singleGuildMode){
			try {
				this.defaultConfig.setReferencedGuild(g);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.defaultConfig;
		}
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
		for(ModelCategory c : ModelCategory.getAsList()) {
			if(c.isNsfw()) {
				this.defaultConfig.setAllowedCategory(c.getName(defaultConfig.getLanguage()), false);
			}else {
				for(ModelCommand mc : c.getCommands()) {
					if(mc.isNsfw()) {
						this.defaultConfig.setAllowedCommand(mc.getName(defaultConfig.getLanguage()), false);
					}
				}
			}
		}
	}
}
