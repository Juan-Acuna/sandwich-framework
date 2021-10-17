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
import com.jaxsandwich.sandwichcord.development.HasBugs;
import com.jaxsandwich.sandwichcord.development.InDevelopment;
import com.jaxsandwich.sandwichcord.models.components.ButtonActionObject;
import com.jaxsandwich.sandwichcord.models.packets.ButtonPacket;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
/**
 * [ES] Representa al Bot de Discord. Contiene lo básico para la construcción de un bot.<br>
 * [EN] Represents the Discord Bot. Contains the basics for build a bot.
 * @author Juan Acuña
 * @version 1.4
 * @since 0.4.0
 */
public abstract class Bot extends ListenerAdapter{
	/**
	 * [ES] Indica si el bot se encuentra registrado dentro de la clase BotRunner.<br>
	 * [EN] Indicates if the bot is already registered in the BotRunner class.
	 */
	private boolean registeredOnRunner = false;
	/**
	 * [ES] Instancia de {@link JDA}.<br>
	 * [EN] Instance of {@link JDA}.
	 */
	protected JDA jda = null;
	/**
	 * [ES] Objeto para configurar y construir el {@link JDA} del bot.<br>
	 * [EN] Object to configure and build the {@link JDA} of the bot.
	 */
	protected JDABuilder builder;
	/**
	 * [ES] Prefijo del bot. Se usa por defecto en todos los servidores({@link com.jaxsandwich.sandwichcord.models.guild.GuildConfig}) en los cuales no se ha especificado un prefijo.<br>
	 * [EN] Bot prefix. Is used by default in all the guilds({@link com.jaxsandwich.sandwichcord.models.guild.GuildConfig}) where a prefix was not specified.
	 */
	protected String prefix = ">";
	/**
	 * [ES] Prefijo de opción del bot. Se usa por defecto en todos los servidores({@link com.jaxsandwich.sandwichcord.models.guild.GuildConfig}) en los cuales no se ha especificado un prefijo de opción.<br>
	 * [EN] Bot option prefix. Is used by default in all the guilds({@link com.jaxsandwich.sandwichcord.models.guild.GuildConfig}) where an option prefix was not specified.
	 */
	protected String opt_prefix = "-";
	/**
	 * [ES] Indica si el bot esta encendido o apagado (enendido = escuchando comandos).<br>
	 * [EN] Indicates if the bot is on or off (on = listenning to commands).
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
	 * [ES] Indica si el bot escucha commandos(mensajes) enviados por bots.<br>
	 * [EN] Indicates if the bot listen to commands(messages) sent by bots.
	 * */
	protected boolean ignoreBotsCommands = false;
	@HasBugs
	/**
	 * [ES] Activa la simulación de typeo('Bot esta escribiendo...' en el cliente oficial de Discord) cada vez que se reciva un comando.<br>
	 * [EN] Enables the typing simulation('Bot is typing...' in the Discord oficial client) every time the bot get a command.
	 * */
	protected boolean typingOnCommand = false;
	/**
	 * [ES] Indica si el bot escucha commandos(mensajes) enviados desde un WebHook.<br>
	 * [EN] Indicates if the bot listen to commands(messages) sent from a WebHook.
	 * */
	protected boolean ignoreWebHook = true;
	/**
	 * [ES] Inidica si los comandos y categorías etiquetadas como 'NSFW' deben ocultarse al usar el comando de ayuda automático. También bloquea estos comandos de canales privados.<br>
	 * [EN] Indicates if the commands an categories tagged as 'NSFW' have to be hidden when using the automatic help command. Also block these commands for private channels.
	 */
	protected boolean hide_nsfw_category = false;
	/**
	 * [ES] Permite usar el bot sin configuraciones especificas para cada servidor.<br>
	 * [EN] Allows to use the bot without specific configuration for each guild.
	 */
	protected boolean singleGuildMode = false;
	/**
	 * [ES] Gestor de configuración de servidores({@link com.jaxsandwich.sandwichcord.models.guild.GuildConfig}) del bot.<br>
	 * [EN] Manager of guilds config({@link com.jaxsandwich.sandwichcord.models.guild.GuildConfig}) of the bot.
	 */
	protected GuildConfigManager guildConfigManager;
	/**
	 * [ES] Gestor de comandos de respuesta({@link com.jaxsandwich.sandwichcord.models.ResponseCommandObject}) del bot.<br>
	 * [EN] Manager of response command({@link com.jaxsandwich.sandwichcord.models.ResponseCommandObject}) of the bot.
	 */
	protected ResponseCommandManager responseCommandManager;
	/**
	 * [ES] Representa el comando de ayuda automatico({@link AutoHelpCommand}) del bot.<br>
	 * [EN] Represents the automatic help command({@link AutoHelpCommand}) of the bot.
	 */
	protected AutoHelpCommand autoHelpCommand;
	/**
	 * [ES] Indica si al iniciar el bot se deben sincronizar los todos los comandos globales registrados. Debido a que esta acción puede tomar tiempo y recursos, se recomienda solo usar si es requerido.<br>
	 * [EN] Indicates if when the bot starts all the global commands registered have to be synchronized. Because it can take a long time an resources, is recommended to use only if required.
	 */
	protected boolean forceGlobalReset = false;
	/**
	 * [ES] Indica si al iniciar el bot se deben sincronizar los todos los comandos de servidores registrados. Debido a que esta acción puede tomar tiempo y recursos, se recomienda solo usar si es requerido.<br>
	 * [EN] Indicates if when the bot starts all the guild commands registered have to be synchronized. Because it can take a long time an resources, is recommended to use only if required.
	 */
	protected boolean forceGuildsReset = false;
	/**
	 * [ES] Construye un bot indicando el Token de Discord y el idioma por defecto del bot.<br>
	 * [EN] Builds a bot using the given Discord Token and the default bot language.
	 * @param token <br>[ES] token de Discord. [EN] Discord token.
	 * @param defaultLang <br>[ES] idioma por defecto del bot. [EN] default bot language.
	 */
	protected Bot(String token, Language defaultLang) {
		this.def_lang=defaultLang;
		builder = JDABuilder.createDefault(token);
	}
	/**
	 * [ES] Inicia la ejecución del bot.<br>
	 * [EN] Starts the bot execution.
	 * @throws Exception
	 */
	public final void runBot() throws Exception {
		if(!registeredOnRunner)
			throw new Exception("Unregistered Bot, can't start running a non-registered bot!");
		this.jda = builder.build();
		this.builder=null;
		this.on=true;
	}
	/**
	 * [ES] Metodo que indicar al bot que fue registrado.<br>
	 * [EN] Method that indicate to the bot it was registered.
	 */
	final void setRegistered() {
		this.registeredOnRunner=true;
	}
	/**
	 * [ES] Devuelve el {@link GuildConfigManager} de este bot.<br>
	 * [EN] Returns the {@link GuildConfigManager} of this bot.
	 * @return [ES] el GuildConfigManager de este bot.<br>[EN] the GuildConfigManager of this bot.
	 */
	public GuildConfigManager getGuildConfigManager() {
		return guildConfigManager;
	}
	/**
	 * [ES] Devuelve el {@link ResponseCommandManager} de este bot.<br>
	 * [EN] Returns the {@link ResponseCommandManager} of this bot.
	 * @return [ES] el ResponseCommandManager de este bot.<br>[EN] the ResponseCommandManager of this bot.
	 */
	public ResponseCommandManager getResponseCommandManager() {
		return responseCommandManager;
	}
	/**
	 * [ES] Devuelve el {@link Language} por defecto de este bot.<br>
	 * [EN] Returns the default {@link Language} of this bot.
	 * @return [ES] el idioma por defecto de este bot.<br>[EN] the default language of this bot.
	 */
	public Language getDefaultLanguage() {
		return def_lang;
	}
	/**
	 * [ES] Configura el {@link Language} por defecto de este bot.<br>
	 * [EN] Sets the default {@link Language} of this bot.
	 * @param def_lang <br>[ES] idioma por defecto. [EN] default language.
	 */
	public void setDefaultLanguage(Language def_lang) {
		this.def_lang = def_lang;
	}
	/**
	 * [ES] Devuelve el prefijo de comandos de este bot.<br>
	 * [EN] Returns the commands prefix of this bot.
	 * @return [ES] el prefijo de comandos.<br>[EN] the commands prefix.
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * [ES] Configura el prefijo de comandos de este bot.<br>
	 * [EN] Sets the commands prefix of this bot.
	 * @param prefix <br>[ES] prefijo de comandos. [EN] commands prefix.
	 * @throws Exception [ES] No se puede usar el caracter '/' como prefijo de comandos, está reservado solo para comandos slash.<br>[EN] Can't use the character '/' as commands prefix, it's reserved for slash commands only!
	 */
	public void setPrefix(String prefix) throws Exception {if(opt_prefix.equals("/"))
		throw new Exception("Can't use the character '/' as commands prefix, it's reserved for slash commands only!");
		this.prefix = prefix;
	}
	/**
	 * [ES] Devuelve el prefijo de opciones de este bot.<br>
	 * [EN] Returns the options prefix of this bot.
	 * @return [ES] el prefijo de opciones.<br>[EN] the options prefix.
	 */
	public String getOptionsPrefix() {
		return opt_prefix;
	}
	/**
	 * [ES] Configura el prefijo de opciones de este bot.<br>
	 * [EN] Sets the options prefix of this bot.
	 * @param opt_prefix <br>[ES] prefijo de opciones. [EN] options prefix.
	 */
	public void setOptionsPrefix(String opt_prefix) {
		this.opt_prefix = opt_prefix;
	}
	/**
	 * [ES] Devuelve verdadero si el comando de ayuda automatico esta activado en este bot.<br>
	 * [EN] Returns true if the automatic help command is enabled in this bot.
	 * @return [ES] si el comando de ayuda automático está activado.<br>[EN] if the automatic help command is enabled.
	 */
	public final boolean isAutoHelpCommandEnabled() {
		return autoHelpEnabled;
	}
	/**
	 * [ES] Configura si el comando de ayuda automatico esta activado en este bot.<br>
	 * [EN] Sets if the automatic help command is enabled in this bot.
	 * @param auto <br>[ES] true para activar el comando de ayuda automático. [EN] true to enable the automatic help command.
	 */
	public final void setAutoHelpCommandEnabled(boolean auto) {
		this.autoHelpEnabled = auto;
	}
	/**
	 * [ES] Devuelve verdadero si el la protección de contenido NSFW esta activada en este bot.<br>
	 * [EN] Returns true if the NSFW protection is enabled in this bot.
	 * @return [ES] verdadero si la protección está activada.<br>[EN] true if the protection is enabled.
	 */
	public boolean isHideNSFWCategory() {
		return this.hide_nsfw_category;
	}
	/**
	 * [ES] Configura si el la protección de contenido NSFW esta activada en este bot.<br>
	 * [EN] Sets if the NSFW protection is enabled in this bot.
	 * @param hide <br>[ES] true para activar la protección. [EN] .
	 */
	public void setHideNSFWCategory(boolean hide) {
		this.hide_nsfw_category = hide;
	}
	/**
	 * [ES] Devuelve verdadero si el bot esta encendido.<br>
	 * [EN] Returns true if the bot is on.
	 * @return [ES] verdadero si el bot está encendido.<br>[EN] true if the bot is on.
	 */
	public final boolean isOn() {
		return this.on;
	}
	/**
	 * [ES] Configura si el bot esta encendido.<br>
	 * [EN] Sets if the bot is on.
	 * @param on <br>[ES] true para encender el bot. [EN] true to turn on the bot.
	 */
	public final void setOn(boolean on) {
		this.on=on;
	}
	/**
	 * [ES] Devuelve el {@link JDABuilder} asociado a este bot. Una vez que el bot ha iniciado su ejecución este metodo siempre retornará nulo.<br>
	 * [EN] Returns the {@link JDABuilder} associated to this bot. Once the bot started, this method always return null.
	 * @return [ES] el JDABuilder de este bot. Nulo si el bot ya está iniciado.<br>
	 * [EN] the JDABuilder of this bot. Null if the bot is already started.
	 */
	public final JDABuilder getBuilder() {
		return builder;
	}
	/**
	 * [ES] Devuelve el {@link JDA}.<br>
	 * [EN] Returns the {@link JDA}.
	 * @return [ES] el JDA.<br>[EN] the JDA.
	 */
	public final JDA getJDA() {
		return jda;
	}
	/**
	 * [ES] Devuelve verdadero si al iniciar el bot se deben sincronizar los todos los comandos globales registrados.<br>
	 * [EN] Returns true if when the bot starts all the global commands registered have to be synchronized.
	 * @return [ES] verdadero si la sincronización esta activada.<br>[EN] true if the synchronization is enabled.
	 */
	public boolean isForceGlobalReset() {
		return forceGlobalReset;
	}
	/**
	 * [ES] Configura si al iniciar el bot se deben sincronizar los todos los comandos globales registrados. Debido a que esta acción puede tomar tiempo y recursos, se recomienda solo usar si es requerido.<br>
	 * [EN] Sets if when the bot starts all the global commands registered have to be synchronized. Because it can take a long time an resources, is recommended to use only if required.
	 * @param forceGlobalReset <br>[ES] true para activar la sincronización. [EN] true to enable the synchronization.
	 */
	public void setForceGlobalReset(boolean forceGlobalReset) {
		this.forceGlobalReset = forceGlobalReset;
	}
	/**
	 * [ES] Devuelve verdadero si al iniciar el bot se deben sincronizar los todos los comandos de servidor registrados.<br>
	 * [EN] Returns true if when the bot starts all the guild commands registered have to be synchronized.
	 * @return [ES] verdadero si la sincronización esta activada.<br>[EN] true if the synchronization is enabled.
	 */
	public boolean isForceGuildsReset() {
		return forceGuildsReset;
	}
	/**
	 * [ES] Configura si al iniciar el bot se deben sincronizar los todos los comandos de servidor registrados. Debido a que esta acción puede tomar tiempo y recursos, se recomienda solo usar si es requerido.<br>
	 * [EN] Sets if when the bot starts all the guild commands registered have to be synchronized. Because it can take a long time an resources, is recommended to use only if required.
	 * @param forceGuildsReset <br>[ES] true para activar la sincronización. [EN] true to enable the synchronization.
	 */
	public void setForceGuildsReset(boolean forceGuildsReset) {
		this.forceGuildsReset = forceGuildsReset;
	}
	/**
	 * [ES] Devuelve el {@link User} asociado a este bot.<br>
	 * [EN] Returns the {@link User} associated to this bot.
	 * @return [ES] el usuario que representa al bot. [EN] the user who represents the bot.
	 */
	public final User getSelfUser() {
		return this.jda.getSelfUser();
	}
	/**
	 * [ES] Configura la {@link Activity} de este bot.<br>
	 * [EN] Sets the {@link Activity} of this bot.
	 * @param activity <br>[ES] texto de la actividad('Jugando a ...' en el cliente oficial de Discord).
	 * [EN] text of the activity('Playing ...' in the official Discord client).
	 */
	public final void setActivity(String activity) {
		this.jda.getPresence().setActivity(Activity.playing(activity));
	}
	/**
	 * [ES] Configura la {@link Activity} de este bot.<br>
	 * [EN] Sets the {@link Activity} of this bot.
	 * @param activity <br>[ES] actividad del bot. [EN] bot activity.
	 */
	public final void setActivity(Activity activity) {
		this.jda.getPresence().setActivity(activity);
	}
	/**
	 * [ES] Devuelve la {@link Activity} de este bot.<br>
	 * [EN] Returns the {@link Activity} of this bot.
	 * @return [ES] la actividad del bot.<br>[EN] the bot activity.
	 */
	public final Activity getActivity() {
		return this.jda.getPresence().getActivity();
	}
	/**
	 * [ES] Configura el {@link OnlineStatus} de este bot.<br>
	 * [EN] Sets the {@link OnlineStatus} of this bot.
	 * @param status [ES] estado del bot. [EN] status of the bot.
	 */
	public final void setStatus(OnlineStatus status) {
		this.jda.getPresence().setStatus(status);
	}
	/**
	 * [ES] Devuelve el {@link OnlineStatus} de este bot.<br>
	 * [EN] Returns the {@link OnlineStatus} of this bot.
	 * @return [ES] el estado del bot.<br>[EN] the status of the bot.
	 */
	public final OnlineStatus getStatus() {
		return this.jda.getPresence().getStatus();
	}
	/**
	 * [ES] Devuelve verdadero si el bot ignora sus propios comandos.<br>
	 * [EN] Retuns true if the bot ignores commands sent by itself.
	 * @return [ES] verdadero si los ignora.<br>[EN] true if it ignores itself.
	 */
	public final boolean isIgnoreSelfCommands() {
		return ignoreSelfCommands;
	}
	/**
	 * [ES] Configura si el bot ignora sus propios comandos.<br>
	 * [EN] Sets if the bot ignores commands sent by itself.
	 * @param ignore <br>[ES] true para ignorar. [EN] true to ignore.
	 */
	public final void setIgnoreSelfCommands(boolean ignore) {
		this.ignoreSelfCommands = ignore;
	}
	/**
	 * [ES] Devuelve verdadero si el bot ignora comandos enviados por otros bots.<br>
	 * [EN] Retuns true if the bot ignores commands sent by others bots.
	 * @return [ES] verdadero si los ignora.<br>[EN] true if it ignores bots.
	 */
	public final boolean isIgnoreBotsCommands() {
		return ignoreBotsCommands;
	}
	/**
	 * [ES] Configura si el bot ignora comandos enviados por otros bots.<br>
	 * [EN] Sets if the bot ignores commands sent by others bots.
	 * @param ignore <br>[ES] true para ignorar. [EN] true to ignore.
	 */
	public final void setIgnoreBotsCommands(boolean ignore) {
		this.ignoreBotsCommands = ignore;
	}
	/**
	 * [ES] Devuelve verdadero si el bot debe simular escribir cada vez que recibe un comando.<br>
	 * [EN] Retuns true if the bot has to simulate typing for every time it receive a command.
	 * @return [ES] verdadero si la simulación esta activada.<br>[EN] true if the simulation is enabled.
	 */
	public final boolean isTypingOnCommand() {
		return typingOnCommand;
	}
	/**
	 * [ES] Configura si el bot debe simular escribir cada vez que recibe un comando.<br>
	 * [EN] Sets if the bot has to simulate typing for every time it receive a command.
	 * @param enable <br>[ES] true para activar. [EN] true to enable.
	 */
	@HasBugs
	public final void setTypingOnCommand(boolean enable) {
		this.typingOnCommand = enable;
	}
	/**
	 * [ES] Devuelve verdadero si el bot ignora comandos enviados a traves de WebHooks.<br>
	 * [EN] Retuns true if the bot ignores commands sent through WebHooks.
	 * @return [ES] verdadero si los ignora.<br>[EN] true if it ignores WebHooks.
	 * 
	 */
	public final boolean isIgnoreWebHook() {
		return ignoreWebHook;
	}
	/**
	 * [ES] Configura si el bot ignora comandos enviados a traves de WebHooks.<br>
	 * [EN] Sets if the bot ignores commands sent through WebHooks.
	 * @param ignore <br>[ES] true para ignorar. [EN] true to ignore.
	 */
	public final void setIgnoreWebHook(boolean ignore) {
		this.ignoreWebHook = ignore;
	}
	/**
	 * [ES] Devuelve verdadero si el bot no tiene configuraciones especiales por cada servidor.<br>
	 * [EN] Returns true if the bot does not have custom configurations per guildConfig.
	 * @return [ES] verdadero si el modo monoServidor está activado.<br>[EN] true if the singleGuild mode is enabled.
	 */
	public boolean isSingleGuildMode() {
		return singleGuildMode;
	}
	/**
	 * [ES] Configura si el bot no tiene configuraciones especiales por cada servidor.<br>
	 * [EN] Sets if the bot does not have custom configurations per guildConfig.
	 * @param singleGuildMode <br>[ES] tru para activarlo. [EN] true to enable this mode.
	 */
	public void setSingleGuildMode(boolean singleGuildMode) {
		this.singleGuildMode = singleGuildMode;
	}
	/**
	 * [ES] Permite aplicar lógica de negocio antes de procesar un comando.<br>
	 * [EN] Allows to apply business logic before processing a command.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	public boolean beforeCommandProcess(MessageReceivedEvent event){
		return true;
	}
	/**
	 * [ES] Permite aplicar lógica de negocio antes de procesar un comando slash.<br>
	 * [EN] Allows to apply business logic before processing a slash command.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	public boolean beforeSlashCommandProcess(SlashCommandEvent event){
		return true;
	}
	/**
	 * [ES] Permite aplicar lógica de negocio antes de procesar un click de botón.<br>
	 * [EN] Allows to apply business logic before processing a button click.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	public boolean beforeButtonClickProcess(ButtonClickEvent event){
		return true;
	}
	/**
	 * [ES] Permite aplicar lógica de negocio antes de procesar una selección en un menú de selección.<br>
	 * [EN] Allows to apply business logic before processing a selection on a selection menu.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	@InDevelopment
	public boolean beforeSelectionMenu(SelectionMenuEvent event){
		return true;
	}
	/**
	 * [ES] Permite ejecutar alguna acción cuando el bot ya esta listo.<br>
	 * [EN] Allows to execute any action when the bot is ready.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	public void onBotReady(ReadyEvent event) { }
	/**
	 * [ES] Reservado para acciones del framework.<br>
	 * [EN] Reserved for framework actions.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	@Override
	public final void onReady(ReadyEvent event) {
		CommandManager.synchronize(this, forceGlobalReset, forceGuildsReset);
		onBotReady(event);
	}
	/**
	 * [ES] Reservado para acciones del framework.<br>
	 * [EN] Reserved for framework actions.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	@Override
	public final void onMessageReceived(MessageReceivedEvent event) {
		if(!beforeCommandProcess(event))
			return;
		if((this.ignoreBotsCommands && event.getAuthor().isBot()) && (this.ignoreSelfCommands && getSelfUser().equals(event.getAuthor())))
			return;
		try {
			BotRunner.run(event, this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * [ES] Reservado para acciones del framework.<br>
	 * [EN] Reserved for framework actions.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	@Override
	public final void onButtonClick(ButtonClickEvent event) {
		if(!beforeButtonClickProcess(event))
			return;
		ButtonActionObject b = ButtonActionObject.find(event.getComponentId());
		if(b!=null) {
			ButtonPacket p = new ButtonPacket(this, this.guildConfigManager.getConfig(event.getGuild()), event, event.getChannel(), event.getUser().getId());
			try {
				b.execute(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * [ES] Reservado para acciones del framework.<br>
	 * [EN] Reserved for framework actions.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	@Override
	public final void onSlashCommand(SlashCommandEvent event) {
		if(!beforeSlashCommandProcess(event))
			return;
		BotRunner.run(event, this);
	}
	/**
	 * [ES] Reservado para acciones del framework.<br>
	 * [EN] Reserved for framework actions.
	 * @param event <br>[ES] evento recibido. [EN] received event.
	 */
	@InDevelopment
	@Override
	public final void onSelectionMenu(SelectionMenuEvent event) {
		if(!beforeSelectionMenu(event))
			return;
	}
}
