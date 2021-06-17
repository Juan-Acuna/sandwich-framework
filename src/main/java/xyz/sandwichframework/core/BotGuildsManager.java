package xyz.sandwichframework.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.api.entities.Guild;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.discord.ModelGuild;
/**
 * Clase cuyo trabajo es manejar todos los servidores en los que el bot ha sido invitado.
 * Class whic job is manage all the guilds that the bot has been invited.
 * @author Juancho
 * @version 0.9
 */
public class BotGuildsManager {
	
	private static BotGuildsManager _instancia = new BotGuildsManager();
	private Map<Long, ModelGuild> guilds;
	
	private BotGuildsManager() {
		guilds = Collections.synchronizedMap(new HashMap<Long, ModelGuild>());
	}
	public static BotGuildsManager getManager() {
		if(_instancia!=null) {
			return _instancia;
		}
		return _instancia = new BotGuildsManager();
	}
	public boolean registerGuild(ModelGuild guild) {
		if(guilds.get(guild.getId())==null) {
			guilds.put(guild.getId(),guild);
			System.out.println("G:"+guild.getId()+"|"+guild.getLastKnownName()+"|"+guild.getLanguage());
			return true;
		}
		return false;
	}
	public ModelGuild registerGuild(Guild guild, Language lang) {
		ModelGuild g = new ModelGuild(guild.getIdLong(),guild.getName(), lang);
		if(guilds.get(guild.getIdLong())==null) {
			guilds.put(guild.getIdLong(), g);
			return g;
		}else {
			return guilds.get(guild.getIdLong());
		}
	}
	public ModelGuild registerGuild(long id, String lastKnownName, Language lang) {
		ModelGuild g = new ModelGuild(id,lastKnownName,lang);
		if(guilds.get(id)==null) {
			guilds.put(id, g);
			return g;
		}else {
			return guilds.get(id);
		}
	}
	public ModelGuild getGuild(long id) {
		return guilds.get(id);
	}
	public void loadData(List<? extends ModelGuild> data) {
		for(ModelGuild g : data) {
			registerGuild(g);
		}
	}
	public void loadData(ModelGuild[] data) {
		for(ModelGuild g : data) {
			registerGuild(g);
		}
	}
	public int registeredGuildsCount() {
		return guilds.size();
	}
	public int joinedGuildsCount() {
		int i=0;
		for(long id : guilds.keySet()) {
			if(guilds.get(id).isJoined()) {
				i++;
			}
		}
		return i;
	}
}
