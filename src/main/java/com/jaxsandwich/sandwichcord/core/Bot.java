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

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.CommandPacket;
import com.jaxsandwich.sandwichcord.models.components.ButtonActionObject;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
/**
 * [ES] Representa al Bot de Discord. Contiene lo básico para la construcción de un bot.<br>
 * [EN] Represents the Discord Bot. Contains the basics for build a bot.
 * @author Juancho
 * @version 1.1
 */
public abstract class Bot extends ListenerAdapter{
	/**
	 * [ES] Identificador del Bot (útil en el caso del modo MultiBot). Se genera a partir de su Token de Discord.<br>
	 * [EN] Bot identifier (useful in MultiBot mode). It's generated from its Discord Token.
	 */
	protected int tokenHash;
	/**
	 * [ES] Indica si el bot se encuentra registrado en el contenedor de bots dentro de la clase BotRunner.<br>
	 * [EN] Indicates if the bot is already registered in the bots container inside the BotRunner class.
	 */
	private boolean registeredOnRunner = false;
	/**
	 * [ES] Instancia de {@link JDA} (Librería en la cual se construye este framework).<br>
	 * [EN] Instance of {@link JDA}.
	 */
	protected JDA jda = null;
	/**
	 * [ES] Objeto para configurar y construir el {@link JDA} del bot.<br>
	 * [EN] Object to configure and build the {@link JDA} of the bot.
	 */
	protected JDABuilder builder;
	/**
	 * [ES] Prefijo del bot. Se usa por defecto en todos los servidores({@link com.jaxsandwich.sandwichcord.models.discord.GuildConfig}) en los cuales no se ha especificado un prefijo.<br>
	 * [EN] Bot prefix. Is used as defaultin all the guilds({@link com.jaxsandwich.sandwichcord.models.discord.GuildConfig}) which doesn't have a specific prefix).
	 */
	protected String prefix = ">";
	/**
	 * [ES] Prefijo de opción del bot. Se usa por defecto en todos los servidores({@link com.jaxsandwich.sandwichcord.models.discord.GuildConfig}) en los cuales no se ha especificado un prefijo de opción.<br>
	 * [EN] Bot option prefix. Is used as default in all the guilds({@link com.jaxsandwich.sandwichcord.models.discord.GuildConfig}) which doesn't have a specific option prefix).
	 */
	protected String opt_prefix = "-";
	/**
	 * [ES] Indica si el bot esta encendido o apagado (enendido = escuchando comandos). Las categorías 'especiales' ({@link com.jaxsandwich.sandwichcord.annotations.Category}(special=true)) pueden ser escuchadas sin importar el estado de este atributo.<br>
	 * [EN] Indicates if the bot is on or off (on = listenning to commands). The 'special' categories ({@link com.jaxsandwich.sandwichcord.annotations.Category}(special=true)) can be executed no matter the value of this attribute.
	 * */
	protected boolean on = false;
	/**
	 * [ES] Idioma nativo del bot. Por defecto inglés [EN].<br>
	 * [EN] Native language of the bot. Default english [EN].
	 */
	protected Language def_lang = Language.EN;
	/**
	 * [ES] Activa un comando de ayuda automatico para el bot. Puede ser reemplazado por uno propio.<br>
	 * [EN] Enables a automatic help command for the bot. Can be replaced by a custom command.
	 */
	protected boolean autoHelpEnabled = false;
	/**
	 * [ES] Indica si el bot escucha commandos(mensajes) enviados por este mismo.<br>
	 * [EN] Indicates if the bot listen to commands(messages) sent by itself.
	 * */
	protected boolean ignoreSelfCommands = false;
	/**
	 * [ES] Indica si el bot escucha commandos(mensajes) enviados por otros bots.<br>
	 * [EN] Indicates if the bot listen to commands(messages) sent by other bots.
	 * */
	protected boolean ignoreBotsCommands = false;
	/**
	 * [ES] Activa la simulación de typeo('Bot esta escribiendo...' en el cliente oficial de Discord) cada vez que se reciva un comando.<br>
	 * [EN] Enables the typing simulation('Bot is typing...' in the Discord oficial client) every time the bot get a command.
	 * */
	protected boolean typingOnCommand = false;
	/**
	 * [ES] Indica si el bot escucha commandos(mensajes) enviados desde un WebHook.<br>
	 * [EN] Indicates if the bot listen to commands(messages) sent through a WebHook.
	 * */
	protected boolean ignoreWebHook = true;
	/**
	 * [ES] Inidica si los comandos y categorías etiquetadas como 'NSFW' deben acultarse al usar el comando de ayuda automático.<br>
	 * [EN] Indicates if the commands an categories tagged as 'NSFW' have to been hidden when using the automatic help command.
	 */
	protected boolean hide_nsfw_category = false;
	/**
	 * [ES] 
	 * [EN] 
	 */
	@NotDocumented
	protected boolean singleGuildMode = false;
	/**
	 * [ES] Gestor de configuración de servidores({@link com.jaxsandwich.sandwichcord.models.discord.GuildConfig}) del bot.<br>
	 * [EN] Manager of guilds config({@link com.jaxsandwich.sandwichcord.models.discord.GuildConfig}) of the bot.
	 */
	protected GuildConfigManager guildsManager;
	/**
	 * [ES] Gestor de comandos extra({@link com.jaxsandwich.sandwichcord.models.ResponseCommandObject}) del bot.<br>
	 * [EN] Manager of extra command({@link com.jaxsandwich.sandwichcord.models.ResponseCommandObject}) of the bot.
	 */
	protected ResponseCommandManager responseCommandManager;
	/**
	 * [ES] Representa el comando de ayuda automatico({@link AutoHelpCommand}) del bot.<br>
	 * [EN] Represents the automatic help command({@link AutoHelpCommand}) of the bot.
	 */
	protected AutoHelpCommand autoHelpCommand;
	/**
	 * [ES] Construye un bot indicando el Token de Discord y el idioma por defecto del bot.<br>
	 * [EN] Builds a bot using the given Discord Token and the default bot language.
	 */
	protected Bot(String token, Language defaultLang) {
		this.tokenHash = token.hashCode();
		this.def_lang=defaultLang;
		builder = JDABuilder.createDefault(token);
	}
	/**
	 * [ES] Inicia la ejecución del bot.<br>
	 * [EN] Starts the bot execution.
	 */
	public final void runBot() throws Exception {
		if(!registeredOnRunner)
			throw new Exception("Unregistered Bot, can't start running a non-registered bot!");
		this.jda = builder.build();
		this.builder=null;
		this.on=true;
	}
	/**
	 * [ES] Metodo que permite al BotRunner({@link BotRunner}) indicar al bot que fue registrado.<br>
	 * [EN] Method that allows the BotRunner({@link BotRunner}) to indicate the bot was registered[*]
	 */
	final void setRegistered() {
		this.registeredOnRunner=true;
	}
	/**
	 * [ES] Devuelve el gestor de servidores({@link GuildConfigManager}) de este bot.<br>
	 * [EN] Returns the guilds manager({@link GuildConfigManager}) of this bot.
	 */
	public GuildConfigManager getGuildsManager() {
		return guildsManager;
	}
	/**
	 * [ES] Devuelve el gestor de comandos extra({@link ResponseCommandManager}) de este bot.<br>
	 * [EN] Returns the extra command manager({@link ResponseCommandManager}) of this bot.
	 */
	public ResponseCommandManager getExtraCmdManager() {
		return responseCommandManager;
	}
	/**
	 * [ES] Devuelve el idioma({@link Language}) por defecto de este bot.<br>
	 * [EN] Returns the default language({@link Language}) of this bot.
	 */
	public Language getDefaultLanguage() {
		return def_lang;
	}
	/**
	 * [ES] Configura el idioma({@link Language}) por defecto de este bot.<br>
	 * [EN] Sets the default language({@link Language}) of this bot.
	 */
	public void setDefaultLanguage(Language def_lang) {
		this.def_lang = def_lang;
	}
	/**
	 * [ES] Devuelve el prefijo de comandos de este bot.<br>
	 * [EN] Returns the commands prefix of this bot.
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * [ES] Configura el prefijo de comandos de este bot.<br>
	 * [EN] Sets the commands prefix of this bot.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * [ES] Devuelve el prefijo de opciones de este bot.<br>
	 * [EN] Returns the options prefix of this bot.
	 */
	public String getOptionsPrefix() {
		return opt_prefix;
	}
	/**
	 * [ES] Configura el prefijo de opciones de este bot.<br>
	 * [EN] Sets the options prefix of this bot.
	 */
	public void setOptionsPrefix(String opt_prefix) {
		this.opt_prefix = opt_prefix;
	}
	/**
	 * [ES] Devuelve verdadero si el comando de ayuda automatico({@link AutoHelpCommand}) esta activado en este bot.<br>
	 * [EN] Returns true if the automatic help command({@link AutoHelpCommand}) is enabled in this bot.
	 */
	public final boolean isAutoHelpCommandEnabled() {
		return autoHelpEnabled;
	}
	/**
	 * [ES] Configura si el comando de ayuda automatico({@link AutoHelpCommand}) esta activado en este bot.<br>
	 * [EN] Sets if the automatic help command({@link AutoHelpCommand}) is enabled in this bot.
	 */
	public final void setAutoHelpCommandEnabled(boolean auto) {
		this.autoHelpEnabled = auto;
	}
	/**
	 * [ES] Devuelve verdadero si el la protección de contenido NSFW esta activada en este bot.<br>
	 * [EN] Returns true if the NSFW protection is enabled in this bot.
	 */
	public boolean isHideNSFWCategory() {
		return this.hide_nsfw_category;
	}
	/**
	 * [ES] Configura si el la protección de contenido NSFW esta activada en este bot.<br>
	 * [EN] Sets if the NSFW protection is enabled in this bot.
	 */
	public void setHideNSFWCategory(boolean hide) {
		this.hide_nsfw_category = hide;
	}
	/**
	 * [ES] Devuelve verdadero si el bot esta encendido.<br>
	 * [EN] Returns true if the bot is on.
	 */
	public final boolean isOn() {
		return this.on;
	}
	/**
	 * [ES] Configura si el bot esta encendido.<br>
	 * [EN] Sets if the bot is on.
	 */
	public final void setOn(boolean on) {
		this.on=on;
	}
	/**
	 * [ES] Devuelve el {@link JDABuilder} asociado a este bot. Una vez que el bot ha iniciado su ejecución este metodo siempre retornará nulo.<br>
	 * [EN] Returns the {@link JDABuilder} associated to this bot. Once the bot started, this method always returns null.
	 */
	public final JDABuilder getBuilder() {
		return builder;
	}
	/**
	 * [ES] Devuelve el {@link JDABuilder} asociado a este bot.<br>
	 * [EN] Returns the {@link JDABuilder} associated to this bot.
	 */
	public final JDA getJDA() {
		return jda;
	}
	/**
	 * [ES] Devuelve el {@link User} asociado a este bot.<br>
	 * [EN] Returns the {@link User} associated to this bot.
	 */
	public final User getSelfUser() {
		return this.jda.getSelfUser();
	}
	/**
	 * [ES] Configura la {@link Activity} de este bot.<br>
	 * [EN] Sets the {@link Activity} of this bot.
	 */
	public final void setActivity(String activity) {
		this.jda.getPresence().setActivity(Activity.playing(activity));
	}
	/**
	 * [ES] Configura la {@link Activity} de este bot.<br>
	 * [EN] Sets the {@link Activity} of this bot.
	 */
	public final void setActivity(Activity activity) {
		this.jda.getPresence().setActivity(activity);
	}
	/**
	 * [ES] Devuelve la {@link Activity} de este bot.<br>
	 * [EN] Returns the {@link Activity} of this bot.
	 */
	public final Activity getActivity() {
		return this.jda.getPresence().getActivity();
	}
	/**
	 * [ES] Configura el {@link OnlineStatus} de este bot.<br>
	 * [EN] Sets the {@link OnlineStatus} of this bot.
	 */
	public final void setStatus(OnlineStatus status) {
		this.jda.getPresence().setStatus(status);
	}
	/**
	 * [ES] Devuelve el {@link OnlineStatus} de este bot.<br>
	 * [EN] Returns the {@link OnlineStatus} of this bot.
	 */
	public final OnlineStatus getStatus() {
		return this.jda.getPresence().getStatus();
	}
	/**
	 * [ES] Ejecuta el comando automatico de ayuda({@link AutoHelpCommand}) de este bot.<br>
	 * [EN] Runs the automatic help command({@link AutoHelpCommand}) of this bot.
	 */
	public final void runAutoHelpCommand(CommandPacket packet) {
		this.autoHelpCommand.help(packet);
	}
	/**
	 * [ES] Devuelve verdadero si el bot ignora sus propios comandos.<br>
	 * [EN] Retuns true if the bot ignores commands sent by itself.
	 */
	public final boolean isIgnoreSelfCommands() {
		return ignoreSelfCommands;
	}
	/**
	 * [ES] Configura si el bot ignora sus propios comandos.<br>
	 * [EN] Sets if the bot ignores commands sent by itself.
	 */
	public final void setIgnoreSelfCommands(boolean ignore) {
		this.ignoreSelfCommands = ignore;
	}
	/**
	 * [ES] Devuelve verdadero si el bot ignora comandos enviados por otros bots.<br>
	 * [EN] Retuns true if the bot ignores commands sent by others bots.
	 */
	public final boolean isIgnoreBotsCommands() {
		return ignoreBotsCommands;
	}
	/**
	 * [ES] Configura si el bot ignora comandos enviados por otros bots.<br>
	 * [EN] Sets if the bot ignores commands sent by others bots.
	 */
	public final void setIgnoreBotsCommands(boolean ignore) {
		this.ignoreBotsCommands = ignore;
	}
	/**
	 * [ES] Devuelve verdadero si el bot debe simular escribir cada vez que recibe un comando.<br>
	 * [EN] Retuns true if the bot has to simulate typing for every time it receive a command.
	 */
	public final boolean isTypingOnCommand() {
		return typingOnCommand;
	}
	/**
	 * [ES] Configura si el bot debe simular escribir cada vez que recibe un comando.<br>
	 * [EN] Sets if the bot has to simulate typing for every time it receive a command.
	 */
	public final void setTypingOnCommand(boolean enable) {
		this.typingOnCommand = enable;
	}
	/**
	 * [ES] Devuelve verdadero si el bot ignora comandos enviados a traves de WebHooks.<br>
	 * [EN] Retuns true if the bot ignores commands sent through WebHooks.
	 */
	public final boolean isIgnoreWebHook() {
		return ignoreWebHook;
	}
	/**
	 * [ES] Configura si el bot ignora comandos enviados a traves de WebHooks.<br>
	 * [EN] Sets if the bot ignores commands sent through WebHooks.
	 */
	public final void setIgnoreWebHook(boolean ignore) {
		this.ignoreWebHook = ignore;
	}
	/**
	 * [ES] Devuelve verdadero si el bot no tiene configuraciones especiales por cada servidor.<br>
	 * [EN] Returns true if the bot does not have custom configurations per guildConfig.
	 */
	public boolean isSingleGuildMode() {
		return singleGuildMode;
	}
	/**
	 * [ES] Configura si el bot no tiene configuraciones especiales por cada servidor.<br>
	 * [EN] Sets if the bot does not have custom configurations per guildConfig.
	 */
	public void setSingleGuildMode(boolean singleGuildMode) {
		this.singleGuildMode = singleGuildMode;
	}
	/**
	 * [ES] Devuelve el tokenHash de este bot.<br>
	 * [EN] Retuns hashToken of this bot.
	 */
	public final int getTokenHash() {
		return this.tokenHash;
	}
	/**
	 * [ES] Analiza el evento({@link MessageReceivedEvent}) y ejecuta el comando({@link com.jaxsandwich.sandwichcord.models.CommandObject}) si esta presente.<br>
	 * [EN] Analyzes the event({@link MessageReceivedEvent}) and executes the commad({@link com.jaxsandwich.sandwichcord.models.CommandObject}) if it's present.
	 */
	protected final void runCommand(MessageReceivedEvent event) throws Exception {
		if((this.ignoreBotsCommands && event.getAuthor().isBot()) && (this.ignoreSelfCommands && getSelfUser().equals(event.getAuthor())))
			return;
		BotRunner.run(event, this);
	}
	@Override
	/**
	 * [ES] Metodo que gestiona el evento {@link MessageReceivedEvent}. Dentro de este metodo se debe ejecutar '{@link Bot#runCommand(MessageReceivedEvent)}'.<br>
	 * [EN] Method wich Manages the event {@link MessageReceivedEvent}. Inside this method you have to execute '{@link Bot#runCommand(MessageReceivedEvent)}'.
	 */
	public abstract void onMessageReceived(MessageReceivedEvent e);
	@Override
	/**
	 * [ES] Metodo que gestiona los eventos {@link ButtonClickEvent}. No usar este metodo manualmente.<br>
	 * [EN] Method which manages the {@link ButtonClickEvent} events. Do not use this method manually.
	 */
	public final void onButtonClick(ButtonClickEvent event) {
		ButtonActionObject b = ButtonActionObject.find(event.getComponentId());
		if(b!=null)
			b.executeAction(event);
	}
}
