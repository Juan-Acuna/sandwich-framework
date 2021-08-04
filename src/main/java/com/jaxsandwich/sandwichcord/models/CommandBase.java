package com.jaxsandwich.sandwichcord.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.core.util.LanguageHandler;
/**
 * [ES] Clase plantilla para comandos.<br>
 * [EN] Template class for commands.
 * @author Juan Acuña
 * @version 1.0
 */
public abstract class CommandBase {
	/**
	 * [ES] Identificador del comando.<br>
	 * [EN] Command Identifier.
	 */
	protected String id = null;
	/**
	 * [ES] Contenedor de nombres.<br>
	 * [EN] Container of names.
	 */
	protected HashMap<Language, String> name = null;
	/**
	 * [ES] Contenedor de alias.<br>
	 * [EN] Container of alias.
	 */
	protected HashMap<Language, String[]> alias = null;
	/**
	 * [ES] Indica que el comando esta activado para su uso.<br>
	 * [EN] Indicates that the command is active for use.
	 */
	protected boolean enabled;
	/**
	 * [ES] Indica que el comando es visible.<br>
	 * [EN] Indicates that the command is visible.
	 */
	protected boolean visible;
	/**
	 * [ES] Indica si es un comando NSFW.<br>
	 * [EN] Indicates if it is a NSFW command.
	 */
	protected boolean nsfw=false;
	/**
	 * [ES] Indica si es un comando de ayuda.<br>
	 * [EN] Indicates if it is a help command.
	 */
	protected boolean helpCommand=false;
	/**
	 * [ES] Contenedor de opciones.<br>
	 * [EN] Container of options.
	 */
	protected ArrayList<ModelOption> options = null;
	/**
	 * [ES] Metodo que ejecuta el comando.<br>
	 * [EN] Method that the command executes.
	 */
	protected Method action = null;
	/**
	 * [ES] Constructor de CommandBase.<br>
	 * [EN] Constructor of CommandBase.
	 */
	CommandBase(){ }
	/**
	 * [ES] Constructor de CommandBase.<br>
	 * [EN] Constructor of CommandBase.
	 */
	public CommandBase(String id) {
		this.id=id;
		name = new HashMap<Language, String>();
		alias = new HashMap<Language, String[]>();
		options = new ArrayList<ModelOption>();
	}
	/**
	 * [ES] Devuelve el identificador del comando.<br>
	 * [EN] Returns the command identifier.
	 */
	public String getId() {
		return id;
	}
	/***/
	/**
	 * [ES] Devuelve verdadero si el comando esta activado.<br>
	 * [EN] Returns true if the command is enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/***/
	/**
	 * [ES] Configura si el comando esta activado.<br>
	 * [EN] Sets if the command is enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/***/
	/**
	 * [ES] Devuelve el nombre del comando en el idioma especificado.<br>
	 * [EN] Returns the name of the command for the specified language.
	 */
	public String getName(Language lang) {
		if(name.containsKey(lang)) {
			return name.get(lang);
		}
		if(name.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return name.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[name.size()];
		name.keySet().toArray(langs);
		return name.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	/***/
	/**
	 * [ES] Configura el nombre del comando en el idioma especificado.<br>
	 * [EN] Sets the name of the command for the specified language.
	 */
	public void setName(Language lang, String name) {
		this.name.put(lang, name);
	}
	/***/
	/**
	 * [ES] Devuelve los alias del comando en el idioma especificado.<br>
	 * [EN] Returns the aliases of the command for the specified language.
	 */
	public String[] getAlias(Language lang) {
		if(alias.containsKey(lang)) {
			return alias.get(lang);
		}
		if(alias.containsKey(LanguageHandler.getLanguageParent(lang))) {
			return alias.get(LanguageHandler.getLanguageParent(lang));
		}
		Language[] langs = new Language[alias.size()];
		alias.keySet().toArray(langs);
		return alias.get(LanguageHandler.findBestLanguage(lang, langs));
	}
	/***/
	/**
	 * [ES] Configura los alias del comando en el idioma especificado.<br>
	 * [EN] Sets the aliases of the command for the specified language.
	 */
	public void setAlias(Language lang, String[] alias) {
		this.alias.put(lang, alias);
	}
	/***/
	/**
	 * [ES] Devuelve verdadero si el comando es visible.<br>
	 * [EN] Returns true if the command is visible.
	 */
	public boolean isVisible() {
		return visible;
	}
	/***/
	/**
	 * [ES] Configura si el comando es visible.<br>
	 * [EN] Sets if the command is visible.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	/***/
	/**
	 * [ES] Devuelve una lista con las opciones disponibles para el comando.<br>
	 * [EN] Returns a list of available options of the command.
	 */
	public ArrayList<ModelOption> getOptions() {
		return options;
	}
	/***/
	/**
	 * [ES] Agrega una opción al comando.<br>
	 * [EN] Returns a list of available options of the command.
	 */
	public void addOption(ModelOption option) {
		this.options.add(option);
	}
	/***/
	/**
	 * [ES] Devuelve el metodo que ejecuta el comando cuando es llamado.<br>
	 * [EN] Returns the method which the command runs when it is called.
	 */
	public Method getAction() {
		return action;
	}
	/***/
	/**
	 * [ES] Configura el metodo que ejecuta el comando cuando es llamado.<br>
	 * [EN] Sets the method which the command runs when it is called.
	 */
	public void setAction(Method source) {
		this.action = source;
	}
	/***/
	/**
	 * [ES] Devuelve verdadero si el comando es NSFW (No seguro para el trabajo, siglas en inglés).<br>
	 * [EN] Returns true if it is a NSFW(Not Safe For Work) command.
	 */
	public boolean isNsfw() {
		return nsfw;
	}
	/***/
	/**
	 * [ES] Configura si el comando es NSFW(No seguro para el trabajo, siglas en inglés).<br>
	 * [EN] Sets if it is a NSFW(Not Safe For Work) command.
	 */
	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}
	/***/
	/**
	 * [ES] Devuelve verdadero si el comando es un comando de ayuda.<br>
	 * [EN] Returns true if it is a help command.
	 */
	public boolean isHelpCommand() {
		return helpCommand;
	}
	/***/
	/**
	 * [ES] Configura si el comando es un comando de ayuda.<br>
	 * [EN] Sets if it is a help command.
	 */
	public void setHelpCommand(boolean helpCommand) {
		this.helpCommand = helpCommand;
	}
}

