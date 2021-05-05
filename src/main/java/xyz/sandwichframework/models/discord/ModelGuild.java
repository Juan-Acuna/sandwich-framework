package xyz.sandwichframework.models.discord;

import xyz.sandwichframework.core.util.Language;
/**
 * Representa un Servidor de Discord.
 * Represents a Discord's Guild.
 * @author Juancho
 * @version 0.9
 */
public class ModelGuild {
	private String id;
	private String lastKnownName;
	private Language language;
	private boolean joined;
	// OTRAS CONFIGURACIONES POR SERVIDOR
	public ModelGuild() {
		
	}
	public ModelGuild(String id) {
		this.id = id;
	}
	public ModelGuild(String id, String lastKnownName, Language language) {
		this.id = id;
		this.lastKnownName = lastKnownName;
		this.language = language;
	}
	public String getId() {
		return id;
	}
	public String getLastKnownName() {
		return lastKnownName;
	}
	public void setLastKnownName(String lastKnownName) {
		this.lastKnownName = lastKnownName;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public boolean isJoined() {
		return joined;
	}
	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelGuild other = (ModelGuild) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
