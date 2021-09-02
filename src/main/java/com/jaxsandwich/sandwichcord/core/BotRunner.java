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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.jaxsandwich.sandwichcord.annotations.*;
import com.jaxsandwich.sandwichcord.annotations.configure.*;
import com.jaxsandwich.sandwichcord.annotations.text.ValueID;
import com.jaxsandwich.sandwichcord.annotations.text.ValuesContainer;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
import com.jaxsandwich.sandwichcord.models.CommandPacket;
import com.jaxsandwich.sandwichcord.models.ResponseCommandPacket;
import com.jaxsandwich.sandwichcord.models.components.ButtonActionObject;
import com.jaxsandwich.sandwichcord.models.CategoryObject;
import com.jaxsandwich.sandwichcord.models.CommandObject;
import com.jaxsandwich.sandwichcord.models.ResponseCommandObject;
import com.jaxsandwich.sandwichcord.models.OptionObject;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Clase intermediaria entre el bot(s) y el resto de elementos.<br>
 * [EN] Intermediate class between the bot(s) and the others elements.
 * @author Juancho
 * @version 1.8
 */
public final class BotRunner {
	/**
	 * [ES] Contenedor de bots. Solo disponible en modo MultiBot.<br>
	 * [EN] Bot container. Only available in MultiBot mode
	 */
	private Map<Integer, Bot> bots = Collections.synchronizedMap(new HashMap<Integer , Bot>());
	/**
	 * [ES] Variable temporal para la inicializacion.<br>
	 * [EN] Temporal variable for the initialization.
	 */
	private Map<String, Map<Language, String>> tempVals;
	/**
	 * [ES] Variable temporal para la inicializacion.<br>
	 * [EN] Temporal variable for the initialization.
	 */
	private Set<Class<?>> configs;
	/**
	 * [ES] Idioma por defecto para la inicializacion.<br>
	 * [EN] Default language for the initialization.
	 */
	private Language defaultLanguage = Language.EN;
	/**
	 * [ES] Variable que verifica que BotRunner se ha inicializado.<br>
	 * [EN] Variable wich verifies that BotRunner is started.
	 */
	private boolean started = false;
	/**
	 * [ES] Indica si es en modo MuliBot.<br>
	 * [EN] Indicates if it's in MultiBot mode.
	 */
	private boolean multibotmode = false;
	/**
	 * [ES] Instancia de BotRunner(Singleton).<br>
	 * [EN] Instancia of BotRunner(Singleton).
	 */
	private static BotRunner instance = new BotRunner();
	@NotDocumented
	private static List<LanguageConfiguration> lconfigs;
	/**
	 * [ES] Constructor de BotRunner privado(Singleton).<br>
	 * [EN] Private constructor of BotRunner(Singleton).
	 */
	private BotRunner() {
		configs = new HashSet<Class<?>>();
		tempVals = new HashMap<String, Map<Language, String>>();
		lconfigs = new ArrayList<LanguageConfiguration>();
	}
	/**
	 * [ES] Devuelve la instancia de BotRunner(Singleton).<br>
	 * [EN] Returns the instance of BotRunner(Singleton).
	 */
	public static BotRunner getInstance() {
		return BotRunner.instance;
	}
	/**
	 * [ES] Registra bot en BotRunner.<br>
	 * [EN] Registers bot in BotRunner.
	 */
	private static void registerBot(Bot bot) throws Exception {
		if(!instance.started)
			throw new Exception("You can't register a bot before starting the Bot Runner. Please use "
					+ "'BotRunner.singleBotModeInit(Bot)' or 'BotRunner.multiBotModeInit(Language, Bot)' methods in order to register a bot.");
		bot.guildsManager = GuildConfigManager.startService(bot);
		bot.responseCommandManager = ResponseCommandManager.startService(bot);
		bot.autoHelpCommand = new AutoHelpCommand(bot);
		bot.builder.addEventListeners(bot);
		bot.setRegistered();
		if(instance.multibotmode) {
			instance.bots.put(bot.tokenHash, bot);
		}else {
			instance.bots = null;
		}
		
	}
	/**
	 * [ES] Inicia BotRunner en modo SingleBot.<br>
	 * [EN] Starts BotRunner in SingleBot mode.
	 */
	public static void singleBotModeInit(Bot bot) throws Exception {
		if(instance.started)
			throw new Exception("The Bot Runner is already initialized"+(instance.multibotmode?" in multi-bot mode!":"!"));
		instance.multibotmode=false;
		instance.started=true;
		instance.defaultLanguage=bot.getDefaultLanguage();
		BotRunner.registerBot(bot);
		instance.initialize();
	}
	/**
	 * [ES] Inicia BotRunner en modo MultiBot.<br>
	 * [EN] Starts BotRunner in MultiBot mode.
	 */
	public static void multiBotModeInit(Language defaultLang, Bot...bots) throws Exception {
		if(instance.started)
			throw new Exception("The Bot Runner is already initialized"+(instance.multibotmode?"!":" in single-bot mode!"));
		if(bots==null || bots.length<=0)
			throw new Exception("You must give at least one bot to register!");
		instance.multibotmode=true;
		instance.started=true;
		instance.defaultLanguage=defaultLang;
		if(bots.length==1)
			System.out.println("You only registered one bot. You can register more with BotRunner.registerBot(Bot) method. "
					+ "If you will only use one bot, you should use the BotRunner.singleBotModeInit(Bot) method.");
		for(Bot bot : bots) {
			BotRunner.registerBot(bot);
		}
		instance.initialize();
	}
	@NotDocumented
	public static final void addConfiguration(LanguageConfiguration config) {
		lconfigs.add(config);
	}
	/**
	 * [ES] Busca el bot registrado en BotRunner segun su tokenHash. Solo disponible en modo MultiBot.<br>
	 * [EN] Finds the bot registered in BotRunner by its tokenHash. Only available in MultiBot mode.
	 */
	public static final Bot findBot(int tokenHash) throws Exception {
		if(!instance.isMultiBotMode())
			throw new Exception("Can't use this method in single-bot mode!");
		return instance.bots.get(tokenHash);
	}
	/**
	 * [ES] Busca el bot registrado en BotRunner según un token de Discord ingresado. Solo disponible en modo MultiBot.<br>
	 * [EN] Finds the bot registered in BotRunner by a Discord token. Only available in MultiBot mode.
	 */
	public static final Bot getBotByToken(String token) throws Exception {
		if(!instance.isMultiBotMode())
			throw new Exception("Can't use this method in single-bot mode!");
		return instance.bots.get(token.hashCode());
	}
	/**
	 * [ES] Devuelve todos los bots contenidos en forma de lista. Solo disponible en modo MultiBot.<br>
	 * [EN] Returns all the bots contained as a list. Only available in MultiBot mode.
	 */
	public static final List<Bot> getBotList() throws Exception{
		if(!instance.isMultiBotMode())
			throw new Exception("Can't use this method in single-bot mode!");
		return new ArrayList<Bot>(instance.bots.values());
	}
	/**
	 * [ES] Devuelve verdadero si BotRunner esta configurado en modo MultiBot.<br>
	 * [EN] Returns true if BotRunner is set in Multiot mode.
	 */
	public boolean isMultiBotMode() {
		return this.multibotmode;
	}
	/**
	 * [ES] Inicializa todos los objetos del framework.<br>
	 * [EN] Initializes all the framework objects.
	 */
	private void initialize() throws Exception {
		Package[] pkgs = Package.getPackages();
		boolean u = true;
		for(Package p : pkgs) {
			String pn = p.getName();
			if(pn.startsWith("com.jaxsandwich.sandwichcord.")) {
				if(u) {
					pn = "com.jaxsandwich.sandwichcord.core.util.defaultvalues";
					u=false;
				}else {
					continue;
				}
			}
			if(!(pn.startsWith("sun.") || pn.startsWith("java.")
					|| pn.startsWith("com.google") || pn.startsWith("net.dv8tion.jda")
					|| pn.startsWith("javassist") || pn.startsWith("org.reflections")
					|| pn.startsWith("jdk.") || pn.startsWith("org.slf4j.")) || pn.equals("com.jaxsandwich.sandwichcord.core.util.defaultvalues")) { //PACKAGES QUE SE OCUPAN DENTRO DEL FRAMEWORK
				// AQUI VA LO QUE SE DEBE HACER CON CADA PACKAGE
				String[] str = p.getName().split("\\.");
				Reflections r = new Reflections(str[0] + "." + str[1]);
				Set<Class<?>> coms = r.getTypesAnnotatedWith(ActionContainer.class);
				Set<Class<?>> vals = r.getTypesAnnotatedWith(ValuesContainer.class);
				Set<Class<?>> xcs = r.getTypesAnnotatedWith(ResponseCommandContainer.class);
				Set<Class<?>> cats = r.getTypesAnnotatedWith(Category.class);
				Set<Class<?>> cfgs = r.getTypesAnnotatedWith(Configuration.class);
				if(coms.size()>0) {
					for(Class<?> c : coms) {
						Method[] ms = c.getDeclaredMethods();
						for(Method m : ms) {
							ButtonAction ba = m.getAnnotation(ButtonAction.class);
							if(ba!=null) {
								Type[] ts = m.getGenericParameterTypes();
								if(ts.length!=1)
									continue;
								if(!ts[0].getTypeName().equals(ButtonClickEvent.class.getTypeName()))
									continue;
								ButtonActionObject.compute(new ButtonActionObject(ba.value(), m));
							}
						}
					}
				}
				if(vals.size()>0) {
					for(Class<?> c : vals) {
						Language l = c.getDeclaredAnnotation(ValuesContainer.class).value();
						Map<Language, String> m;
						Field[] fs = c.getDeclaredFields();
						for(Field f : fs) {
							ValueID id = f.getDeclaredAnnotation(ValueID.class);
							if(id!=null) {
								if(tempVals.get(id.value())!=null) {
									m = tempVals.get(id.value());
								}else {
									m = new HashMap<Language, String>();
									tempVals.put(id.value(), m);
								}
								m.put(l, (String) f.get(null));
							}
						}
					}
				}
				if(xcs.size()>0) {
					for(Class<?> c : xcs) {
						Method[] xcmds = c.getDeclaredMethods();
						if(xcmds.length>0) {
							ResponseCommandObject mxc;
							for(Method m : xcmds) {
								Type[] ts = m.getGenericParameterTypes();
								if(ts.length!=1)
									continue;
								if(!ts[0].getTypeName().equals(ResponseCommandPacket.class.getTypeName()))
									continue;
								ResponseCommand xn = m.getAnnotation(ResponseCommand.class);
								ResponseWaitingExecution xe = m.getAnnotation(ResponseWaitingExecution.class);
								ResponseSuccessExecution xa = m.getAnnotation(ResponseSuccessExecution.class);
								ResponseFailedExecution xne = m.getAnnotation(ResponseFailedExecution.class);
								ResponseFinallyExecution xf = m.getAnnotation(ResponseFinallyExecution.class);
								mxc = new ResponseCommandObject();
								String n = null;
								if(xe!=null) {
									n = xe.name();
									mxc.setEach(m);
								}else if(xa!=null) {
									n = xa.name();
									mxc.setAfter(m);
								}else if(xne!=null) {
									n = xne.name();
									mxc.setNoExecuted(m);
								}else if(xf!=null) {
									n = xf.name();
									mxc.setFinally(m);
								}else if(n==null) {
									n = m.getName();
									if(xn!=null)
										n = xn.value();
									mxc.setAction(m);
								}
								mxc.forceId(n);
								ResponseCommandObject.compute(mxc);
							}
						}
					}
				}
				if(cats.size()>0) {
					CategoryObject cmdcategory;
					CommandObject botcmd;
					for(Class<?> c : cats) {
						Method[] ms = c.getDeclaredMethods();
						Category catanno = c.getDeclaredAnnotation(Category.class);
						cmdcategory = new CategoryObject(defaultLanguage, (catanno.id().equals("")?c.getSimpleName():catanno.id()));
						if(!catanno.desc().equals("")) {
							cmdcategory.setDesc(defaultLanguage, catanno.desc());
						}
						cmdcategory.setNsfw(catanno.nsfw());
						cmdcategory.setVisible(catanno.visible());
						cmdcategory.setSpecial(catanno.isSpecial());
						for(Method m : ms) {
							Command cmdanno = m.getDeclaredAnnotation(Command.class);
							if(cmdanno==null)
								continue;
							Type[] ts = m.getGenericParameterTypes();
							if(ts.length!=1)
								continue;
							if(!ts[0].getTypeName().equals(CommandPacket.class.getTypeName()))
								continue;
							botcmd = new CommandObject(defaultLanguage, cmdanno.id(), cmdcategory, m);
							botcmd.setAlias(defaultLanguage, cmdanno.alias());
							botcmd.setDesc(defaultLanguage, cmdanno.desc());
							botcmd.setEnabled(cmdanno.enabled());
							botcmd.setVisible(cmdanno.visible());
							botcmd.setNsfw(cmdanno.isNSFW());
							botcmd.setHelpCommand(cmdanno.isHelpCommand());
							Option[] op = m.getDeclaredAnnotationsByType(Option.class);
							for(Option o : op) {
								String[] alias = null;
								if(!o.noStandar())
									alias = o.alias();
								botcmd.addOption(new OptionObject(defaultLanguage, o.id(), o.desc(), alias, o.enabled(), o.visible(), o.noStandar()));
							}
							botcmd.sortOptions();
							CommandObject.compute(botcmd);
						}
						cmdcategory.sortCommands();
						CategoryObject.compute(cmdcategory);
					}
				}
				if(cfgs.size()>0) {
					for(Class<?> cc : cfgs) {
						configs.add(cc);
					}
				}
			}
		}
		if(configs.size()>0 && CommandObject.getCommandCount()>0) {
			for(Class<?> c : configs) {
				Language lang = c.getDeclaredAnnotation(Configuration.class).value();
				Field[] fs = c.getDeclaredFields();
				for(Field f : fs) {
					if(f.getDeclaredAnnotation(CategoryID.class)!=null) {
						for(CategoryObject mc : CategoryObject.getAsList()) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CategoryID.class).value())) {
								if(f.getDeclaredAnnotation(CategoryDescription.class)!=null) {
									mc.setDesc(lang, (String)f.get(null));
								}
								if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
									mc.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
								}
							}
						}
					}else if(f.getDeclaredAnnotation(CommandID.class)!=null) {
						for(CommandObject mc : CommandObject.getAsList()) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CommandID.class).value())) {
								if(f.getDeclaredAnnotation(OptionID.class)!=null) {
									for(OptionObject mo : mc.getOptions()) {
										if(mo.getId().equals(f.getDeclaredAnnotation(OptionID.class).value())) {
											if(f.getDeclaredAnnotation(OptionDescription.class)!=null) {
												mo.setDesc(lang, (String)f.get(null));
											}else if(f.getDeclaredAnnotation(OptionAliases.class)!=null) {
												mo.setAlias(lang, (String[])f.get(null));
											}
											if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
												mo.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
											}
											break;
										}
									}
								}else {
									if(f.getDeclaredAnnotation(CommandDescription.class)!=null) {
										mc.setDesc(lang, (String)f.get(null));
									}else if(f.getDeclaredAnnotation(CommandAliases.class)!=null) {
										mc.setAlias(lang, (String[])f.get(null));
									}
									if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
										mc.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
									}
								}
							}
						}
					}else if(f.getDeclaredAnnotation(MultiCommandIDOption.class)!=null) {
						for(String id : f.getDeclaredAnnotation(MultiCommandIDOption.class).value()) {
							for(CommandObject mc : CommandObject.getAsList()) {
								if(mc.getId().equals(id)) {
									if(f.getDeclaredAnnotation(OptionID.class)!=null) {
										for(OptionObject mo : mc.getOptions()) {
											if(mo.getId().equals(f.getDeclaredAnnotation(OptionID.class).value())) {
												if(f.getDeclaredAnnotation(OptionDescription.class)!=null) {
													mo.setDesc(lang, (String)f.get(null));
												}else if(f.getDeclaredAnnotation(OptionAliases.class)!=null) {
													mo.setAlias(lang, (String[])f.get(null));
												}
												if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
													mo.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
												}
												break;
											}
										}
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		if(lconfigs.size()>0) {
			for(LanguageConfiguration c : lconfigs) {
				Map<String, Map<Language, String>> catNames = c.getCatNames();
				Map<String, Map<Language, String>> catDesc = c.getCatDesc();
				Map<String, Map<Language, String>> cmdNames = c.getCmdNames();
				Map<String, Map<Language, String>> cmdDesc = c.getCmdDesc();
				Map<String, Map<Language, String>> values = c.getValues();
				Map<String, Map<Language, String[]>> cmdAlias = c.getCmdAliases();
				if(catNames.size()>0 || catDesc.size()>0) {
					for(CategoryObject cat : CategoryObject.getAsList()) {
						if(catNames.get(cat.getId())!=null) {
							for(Language l : catNames.get(cat.getId()).keySet()) {
								cat.setName(l, catNames.get(cat.getId()).get(l));
							}
						}
						if(catDesc.get(cat.getId())!=null) {
							for(Language l : catDesc.get(cat.getId()).keySet()) {
								cat.setDesc(l, catDesc.get(cat.getId()).get(l));
							}
						}
					}
				}
				if(cmdNames.size()>0 || cmdDesc.size()>0 || cmdAlias.size()>0) {
					for(CommandObject cmd : CommandObject.getAsList()) {
						if(cmdNames.get(cmd.getId())!=null) {
							for(Language l : cmdNames.get(cmd.getId()).keySet()) {
								cmd.setName(l, cmdNames.get(cmd.getId()).get(l));
							}
						}
						if(cmdDesc.get(cmd.getId())!=null) {
							for(Language l : cmdDesc.get(cmd.getId()).keySet()) {
								cmd.setDesc(l, cmdDesc.get(cmd.getId()).get(l));
							}
						}
						if(cmdAlias.get(cmd.getId())!=null) {
							for(Language l : cmdAlias.get(cmd.getId()).keySet()) {
								cmd.setAlias(l, cmdAlias.get(cmd.getId()).get(l));
							}
						}
					}
				}
				if(values.size()>0) {
					for(String id : values.keySet()) {
						tempVals.put(id, values.get(id));
					}
				}
			}
		}
		Values.initialize(tempVals);
		lconfigs = null;
		tempVals = null;
		configs = null;
	}
	/**
	 * [ES] Analiza el evento {@link MessageReceivedEvent} y ejecuta el {@link CommandObject} para el {@link Bot} especificado.<br>
	 * [EN] Analyzes the event {@link MessageReceivedEvent} and executes the {@link CommandObject} for the specified {@link Bot}.
	 */
	public static void run(MessageReceivedEvent e, Bot bot) throws Exception {
		if(e.isWebhookMessage() && bot.isIgnoreWebHook())
			return;
		String prx = bot.getPrefix();
		String oprx = bot.getOptionsPrefix();
		boolean b = e.isFromGuild();
		String message = e.getMessage().getContentRaw();
		bot.getExtraCmdManager().CheckExtras(e);
		GuildConfig actualGuild = null;
		Language actualLang = bot.getDefaultLanguage();
		if(b) {
			actualGuild = bot.getGuildsManager().getConfig(e.getGuild().getIdLong());
			actualLang = actualGuild.getLanguage();
			if(actualGuild.getCustomPrefix()!=null)
				prx=actualGuild.getCustomPrefix();
			if(actualGuild.getCustomOptionsPrefix()!=null)
				oprx=actualGuild.getCustomOptionsPrefix();
		}
		if(message.toLowerCase().startsWith(prx) || !b) {
			String r = (message.split(" ")[0]).trim();
			if(bot.autoHelpEnabled) {
				if(r.toLowerCase().equalsIgnoreCase(prx + bot.autoHelpCommand.getName(actualLang)) || r.toLowerCase().equalsIgnoreCase(bot.autoHelpCommand.getName(actualLang))) {
					if(!bot.isOn()) {
						e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
					}
					if(bot.isTypingOnCommand()) {
						try {
							e.getChannel().sendTyping().queue();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,bot.autoHelpCommand,oprx);
					bot.runAutoHelpCommand(builder.build());
					return;
				}
				for(String cs : bot.autoHelpCommand.getAlias(actualLang)) {
					if(r.toLowerCase().equalsIgnoreCase(prx + cs.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
						if(!bot.isOn()) {
							e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
						}
						if(bot.isTypingOnCommand()) {
							try {
								e.getChannel().sendTyping().queue();
							}catch(Exception ex) {
								ex.printStackTrace();
							}
						}
						CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,bot.autoHelpCommand,oprx);
						bot.runAutoHelpCommand(builder.build());
						return;
					}
				}
			}
			for(CommandObject cmd : CommandObject.getAsList()) {
				if(r.toLowerCase().equalsIgnoreCase(prx + cmd.getName(actualLang).toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cmd.getName(actualLang).toLowerCase())){
					if(!cmd.isEnabled() || !cmd.getCategory().isEnabled()) {
						if(!cmd.isVisible() || !cmd.getCategory().isVisible()) {
							return;
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle(Values.value("xyz-sndwch-def-t-ncmd", actualLang));
						e.getChannel().sendMessageEmbeds(eb.build()).queue();
						return;
					}
					if(!bot.isOn() && !cmd.getCategory().isSpecial()) {
						e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
						return;
					}
					if(!GuildConfig.canRunThisCommand(actualGuild, cmd, e.getChannel(), e.getAuthor()))
						return;
					if(bot.isTypingOnCommand()) {
						try {
							e.getChannel().sendTyping().queue();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,cmd,oprx);
					new Thread(new CommandRunner(cmd.getAction(),builder.build())).start();
					return;
				}else {
					for(String a : cmd.getAlias(actualLang)) {
						if(r.toLowerCase().equalsIgnoreCase(prx + a.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
							if(!cmd.isEnabled()) {
								if(!cmd.isVisible()) {
									return;
								}
								EmbedBuilder eb = new EmbedBuilder();
								eb.setTitle(Values.value("xyz-sndwch-def-t-ncmd", actualLang));
								e.getChannel().sendMessageEmbeds(eb.build()).queue();
								return;
							}
							if(!bot.isOn() && !cmd.getCategory().isSpecial()) {
								e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
								return;
							}
							if(!GuildConfig.canRunThisCommand(actualGuild, cmd, e.getChannel(), e.getAuthor()))
								return;
							if(bot.isTypingOnCommand()) {
								try {
									e.getChannel().sendTyping().queue();
								}catch(Exception ex) {
									ex.printStackTrace();
								}
							}
							CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,cmd,oprx);
							new Thread(new CommandRunner(cmd.getAction(),builder.build())).start();
							return;
						}
					}
				}
			}
		}
	}
}
