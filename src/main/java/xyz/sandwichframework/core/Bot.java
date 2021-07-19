package xyz.sandwichframework.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.CommandPacket;
/**
 * Representa al Bot de Discord. Contiene lo básico para la construcción de un bot.
 * Represents the Discord Bot. Contains the basics for build a bot.
 * @author Juancho
 * @version 1.1
 */
public abstract class Bot extends ListenerAdapter{
	/**
	 * Identificador del Bot (útil en el caso del modo MultiBot). Se genera a partir de su Token de Discord.
	 * Bot identifier (useful in MultiBot mode). It's generated from its Discord Token.
	 */
	protected int tokenHash;
	/**
	 * Indica si el bot se encuentra registrado en el contenedor de bots dentro de la clase BotRunner.
	 * Indicates if the bot is already registered in the bots container inside the BotRunner class.
	 */
	private boolean registeredOnRunner = false;
	/**
	 * Instancia de {@link JDA} (Librería en la cual se construye este framework).
	 * Instance of {@link JDA}.
	 */
	protected JDA jda = null;
	/**
	 * Objeto para configurar y construir el {@link JDA} del bot.
	 * Object to configure and build the {@link JDA} of the bot.
	 */
	protected JDABuilder builder;
	/**
	 * Prefijo del bot. Se usa por defecto en todos los servidores({@link xyz.sandwichframework.models.discord.ModelGuild}) en los cuales no se ha especificado un prefijo.
	 * Bot prefix. Is used as defaultin all the guilds({@link xyz.sandwichframework.models.discord.ModelGuild}) which doesn't have a specific prefix).
	 */
	protected String prefix = ">";
	/**
	 * Prefijo de opción del bot. Se usa por defecto en todos los servidores({@link xyz.sandwichframework.models.discord.ModelGuild}) en los cuales no se ha especificado un prefijo de opción.
	 * Bot option prefix. Is used as default in all the guilds({@link xyz.sandwichframework.models.discord.ModelGuild}) which doesn't have a specific option prefix).
	 */
	protected String opt_prefix = "-";
	/**
	 * Indica si el bot esta encendido o apagado (enendido = escuchando comandos). Las categorías 'especiales' ({@link xyz.sandwichframework.annotations.Category}(special=true)) pueden ser escuchadas sin importar el estado de este atributo.
	 * Indicates if the bot is on or off (on = listenning to commands). The 'special' categories ({@link xyz.sandwichframework.annotations.Category}(special=true)) can be executed no matter the value of this attribute.
	 * */
	protected boolean on = false;
	/**
	 * Idioma nativo del bot. Por defecto inglés [EN].
	 * Native language of the bot. Default english [EN].
	 */
	protected Language def_lang = Language.EN;
	/**
	 * Activa un comando de ayuda automatico para el bot. Puede ser reemplazado por uno propio.
	 * Enables a automatic help command for the bot. Can be replaced by a custom command.
	 */
	protected boolean autoHelpEnabled = false;
	/**
	 * Indica si el bot escucha commandos(mensajes) enviados por este mismo.
	 * Indicates if the bot listen to commands(messages) sent by itself.
	 * */
	protected boolean ignoreSelfCommands = false;
	/**
	 * Indica si el bot escucha commandos(mensajes) enviados por otros bots.
	 * Indicates if the bot listen to commands(messages) sent by other bots.
	 * */
	protected boolean ignoreBotsCommands = false;
	/**
	 * Activa la simulación de typeo('Bot esta escribiendo...' en el cliente oficial de Discord) cada vez que se reciva un comando.
	 * Enables the typing simulation('Bot is typing...' in the Discord oficial client) every time the bot get a command.
	 * */
	protected boolean typingOnCommand = false;
	/**
	 * Indica si el bot escucha commandos(mensajes) enviados desde un WebHook.
	 * Indicates if the bot listen to commands(messages) sent through a WebHook.
	 * */
	protected boolean ignoreWebHook = true;
	/**
	 * Inidica si los comandos y categorías etiquetadas como 'NSFW' deben acultarse al usar el comando de ayuda automático.
	 * Indicates if the commands an categories tagged as 'NSFW' have to been hidden when using the automatic help command.
	 */
	protected boolean hide_nsfw_category = false;
	/**
	 * Gestor de servidores({@link xyz.sandwichframework.models.discord.ModelGuild}) del bot.
	 * Manager of guilds({@link xyz.sandwichframework.models.discord.ModelGuild}) of the bot.
	 */
	protected GuildsManager guildsManager;
	/**
	 * Gestor de comandos extra({@link xyz.sandwichframework.models.ModelExtraCommand}) del bot.
	 * Manager of extra command({@link xyz.sandwichframework.models.ModelExtraCommand}) of the bot.
	 */
	protected ExtraCmdManager extraCmdManager;
	/**
	 * Representa el comando de ayuda automatico({@link AutoHelpCommand}) del bot.
	 * Represents the automatic help command({@link AutoHelpCommand}) of the bot.
	 */
	protected AutoHelpCommand autoHelpCommand;
	/**
	 * Construye un bot indicando el Token de Discord y el idioma por defecto del bot.
	 * Builds a bot using the given Discord Token and the default bot language.
	 */
	protected Bot(String token, Language defaultLang) {
		this.tokenHash = token.hashCode();
		this.def_lang=defaultLang;
		builder = JDABuilder.createDefault(token);
	}
	/**
	 * Inicia la ejecución del bot.
	 * Starts the bot execution.
	 */
	public final void runBot() throws Exception {
		if(!registeredOnRunner)
			throw new Exception("Unregistered Bot, can't start running a non-registered bot!");
		this.jda = builder.build();
		this.builder=null;
		this.on=true;
	}
	/**
	 * Metodo que permite al BotRunner({@link BotRunner}) indicar al bot que fue registrado.
	 * Method that allows the BotRunner({@link BotRunner}) to indicate the bot was registered[*]
	 */
	final void setRegistered() {
		this.registeredOnRunner=true;
	}
	/**
	 * Devuelve el gestor de servidores({@link GuildsManager}) de este bot.
	 * Returns the guilds manager({@link GuildsManager}) of this bot.
	 */
	public GuildsManager getGuildsManager() {
		return guildsManager;
	}
	/**
	 * Devuelve el gestor de comandos extra({@link ExtraCmdManager}) de este bot.
	 * Returns the extra command manager({@link ExtraCmdManager}) of this bot.
	 */
	public ExtraCmdManager getExtraCmdManager() {
		return extraCmdManager;
	}
	/**
	 * Devuelve el idioma({@link Language}) por defecto de este bot.
	 * Returns the default language({@link Language}) of this bot.
	 */
	public Language getDefaultLanguage() {
		return def_lang;
	}
	/**
	 * Configura el idioma({@link Language}) por defecto de este bot.
	 * Sets the default language({@link Language}) of this bot.
	 */
	public void setDefaultLanguage(Language def_lang) {
		this.def_lang = def_lang;
	}
	/**
	 * Devuelve el prefijo de comandos de este bot.
	 * Returns the commands prefix of this bot.
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * Configura el prefijo de comandos de este bot.
	 * Sets the commands prefix of this bot.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * Devuelve el prefijo de opciones de este bot.
	 * Returns the options prefix of this bot.
	 */
	public String getOptionsPrefix() {
		return opt_prefix;
	}
	/**
	 * Configura el prefijo de opciones de este bot.
	 * Sets the options prefix of this bot.
	 */
	public void setOptionsPrefix(String opt_prefix) {
		this.opt_prefix = opt_prefix;
	}
	/**
	 * Devuelve verdadero si el comando de ayuda automatico({@link AutoHelpCommand}) esta activado en este bot.
	 * Returns true if the automatic help command({@link AutoHelpCommand}) is enabled in this bot.
	 */
	public final boolean isAutoHelpCommandEnabled() {
		return autoHelpEnabled;
	}
	/**
	 * Configura si el comando de ayuda automatico({@link AutoHelpCommand}) esta activado en este bot.
	 * Sets if the automatic help command({@link AutoHelpCommand}) is enabled in this bot.
	 */
	public final void setAutoHelpCommandEnabled(boolean auto) {
		this.autoHelpEnabled = auto;
	}
	/**
	 * Devuelve verdadero si el la protección de contenido NSFW esta activada en este bot.
	 * Returns true if the NSFW protection is enabled in this bot.
	 */
	public boolean isHideNSFWCategory() {
		return this.hide_nsfw_category;
	}
	/**
	 * Configura si el la protección de contenido NSFW esta activada en este bot.
	 * Sets if the NSFW protection is enabled in this bot.
	 */
	public void setHideNSFWCategory(boolean hide) {
		this.hide_nsfw_category = hide;
	}
	/**
	 * Devuelve verdadero si el bot esta encendido.
	 * Returns true if the bot is on.
	 */
	public final boolean isOn() {
		return this.on;
	}
	/**
	 * Configura si el bot esta encendido.
	 * Sets if the bot is on.
	 */
	public final void setOn(boolean on) {
		this.on=on;
	}
	/**
	 * Devuelve el {@link JDABuilder} asociado a este bot. Una vez que el bot ha iniciado su ejecución este metodo siempre retornará nulo.
	 * Returns the {@link JDABuilder} associated to this bot. Once the bot started, this method always returns null.
	 */
	public final JDABuilder getBuilder() {
		return builder;
	}
	/**
	 * Devuelve el {@link JDABuilder} asociado a este bot.
	 * Returns the {@link JDABuilder} associated to this bot.
	 */
	public final JDA getJDA() {
		return jda;
	}
	/**
	 * Devuelve el {@link User} asociado a este bot.
	 * Returns the {@link User} associated to this bot.
	 */
	public final User getSelfUser() {
		return this.jda.getSelfUser();
	}
	/**
	 * Configura la {@link Activity} de este bot.
	 * Sets the {@link Activity} of this bot.
	 */
	public final void setActivity(String activity) {
		this.jda.getPresence().setActivity(Activity.playing(activity));
	}
	/**
	 * Configura la {@link Activity} de este bot.
	 * Sets the {@link Activity} of this bot.
	 */
	public final void setActivity(Activity activity) {
		this.jda.getPresence().setActivity(activity);
	}
	/**
	 * Devuelve la {@link Activity} de este bot.
	 * Returns the {@link Activity} of this bot.
	 */
	public final Activity getActivity() {
		return this.jda.getPresence().getActivity();
	}
	/**
	 * Configura el {@link OnlineStatus} de este bot.
	 * Sets the {@link OnlineStatus} of this bot.
	 */
	public final void setStatus(OnlineStatus status) {
		this.jda.getPresence().setStatus(status);
	}
	/**
	 * Devuelve el {@link OnlineStatus} de este bot.
	 * Returns the {@link OnlineStatus} of this bot.
	 */
	public final OnlineStatus getStatus() {
		return this.jda.getPresence().getStatus();
	}
	/**
	 * Ejecuta el comando automatico de ayuda({@link AutoHelpCommand}) de este bot.
	 * Runs the automatic help command({@link AutoHelpCommand}) of this bot.
	 */
	public final void runAutoHelpCommand(CommandPacket packet) {
		this.autoHelpCommand.help(packet);
	}
	/**
	 * Devuelve verdadero si el bot ignora sus propios comandos.
	 * Retuns true if the bot ignores commands sent by itself.
	 */
	public final boolean isIgnoreSelfCommands() {
		return ignoreSelfCommands;
	}
	/**
	 * Configura si el bot ignora sus propios comandos.
	 * Sets if the bot ignores commands sent by itself.
	 */
	public final void setIgnoreSelfCommands(boolean ignore) {
		this.ignoreSelfCommands = ignore;
	}
	/**
	 * Devuelve verdadero si el bot ignora comandos enviados por otros bots.
	 * Retuns true if the bot ignores commands sent by others bots.
	 */
	public final boolean isIgnoreBotsCommands() {
		return ignoreBotsCommands;
	}
	/**
	 * Configura si el bot ignora comandos enviados por otros bots.
	 * Sets if the bot ignores commands sent by others bots.
	 */
	public final void setIgnoreBotsCommands(boolean ignore) {
		this.ignoreBotsCommands = ignore;
	}
	/**
	 * Devuelve verdadero si el bot debe simular escribir cada vez que recibe un comando.
	 * Retuns true if the bot has to simulate typing for every time it receive a command.
	 */
	public final boolean isTypingOnCommand() {
		return typingOnCommand;
	}
	/**
	 * Configura si el bot debe simular escribir cada vez que recibe un comando.
	 * Sets if the bot has to simulate typing for every time it receive a command.
	 */
	public final void setTypingOnCommand(boolean enable) {
		this.typingOnCommand = enable;
	}
	/**
	 * Devuelve verdadero si el bot ignora comandos enviados a traves de WebHooks.
	 * Retuns true if the bot ignores commands sent through WebHooks.
	 */
	public final boolean isIgnoreWebHook() {
		return ignoreWebHook;
	}
	/**
	 * Configura si el bot ignora comandos enviados a traves de WebHooks.
	 * Sets if the bot ignores commands sent through WebHooks.
	 */
	public final void setIgnoreWebHook(boolean ignore) {
		this.ignoreWebHook = ignore;
	}
	/**
	 * Devuelve el tokenHash de este bot.
	 * Retuns hashToken of this bot.
	 */
	public final int getTokenHash() {
		return this.tokenHash;
	}
	/**
	 * Analiza el evento({@link MessageReceivedEvent}) y ejecuta el comando({@link xyz.sandwichframework.models.ModelCommand}) si esta presente.
	 * Analyzes the event({@link MessageReceivedEvent}) and executes the commad({@link xyz.sandwichframework.models.ModelCommand}) if it's present.
	 */
	protected final void runCommand(MessageReceivedEvent event) throws Exception {
		if((this.ignoreBotsCommands && event.getAuthor().isBot()) && (this.ignoreSelfCommands && getSelfUser().equals(event.getAuthor())))
			return;
		BotRunner.run(event, this);
	}
	@Override
	/**
	 * Metodo que gestiona el evento {@link GuildJoinEvent}.
	 * Method wich Manages the event {@link GuildJoinEvent}.
	 */
	public abstract void onGuildJoin(GuildJoinEvent e);
	@Override
	/**
	 * Metodo que gestiona el evento {@link MessageReceivedEvent}. Dentro de este metodo se debe ejecutar '{@link Bot#runCommand(MessageReceivedEvent)}'.
	 * Method wich Manages the event {@link MessageReceivedEvent}. Inside this method you have to execute '{@link Bot#runCommand(MessageReceivedEvent)}'.
	 */
	public abstract void onMessageReceived(MessageReceivedEvent e);
}
