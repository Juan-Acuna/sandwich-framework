package xyz.sandwichframework.models.discord;
/**
 * Representa un Rol de Discord.
 * Represents a Discord's Role.
 * @author Juancho
 * @version 0.3
 */
public class ModelRole {
	private String id;
	private String lastKnownName;
	
	public ModelRole() {
		super();
	}
	public ModelRole(String id, String lastKnownName) {
		super();
		this.id = id;
		this.lastKnownName = lastKnownName;
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
		ModelRole other = (ModelRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
