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
import com.jaxsandwich.sandwichcord.core.builders.CommandPacketBuilder;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.components.ButtonActionObject;
import com.jaxsandwich.sandwichcord.models.guild.GuildConfig;
import com.jaxsandwich.sandwichcord.models.CategoryObject;
import com.jaxsandwich.sandwichcord.models.CommandMode;
import com.jaxsandwich.sandwichcord.models.CommandObject;
import com.jaxsandwich.sandwichcord.models.ResponseCommandObject;
import com.jaxsandwich.sandwichcord.models.SlashCommandObject;
import com.jaxsandwich.sandwichcord.models.SlashSubCommand;
import com.jaxsandwich.sandwichcord.models.SlashSubGroup;
import com.jaxsandwich.sandwichcord.models.actionable.IActionable;
import com.jaxsandwich.sandwichcord.models.OptionObject;
import com.jaxsandwich.sandwichcord.models.packets.ButtonPacket;
import com.jaxsandwich.sandwichcord.models.packets.CommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.ComponentPacket;
import com.jaxsandwich.sandwichcord.models.packets.Packet;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.SlashCommandPacket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * [ES] Clase intermediaria entre el bot(s) y el resto de elementos.<br>
 * [EN] Intermediate class between the bot(s) and the others elements.
 * @author Juan Acuña
 * @version 1.9
 * @since 0.0.1
 */
@SuppressWarnings("deprecation")
public final class BotRunner {
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
	 * [EN] Variable which verifies that BotRunner is started.
	 */
	private boolean started = false;
	/**
	 * [ES] Instancia de BotRunner(Singleton).<br>
	 * [EN] Instance of BotRunner(Singleton).
	 */
	private static BotRunner instance = new BotRunner();
	/**
	 * [ES] Contenedor de configuraciones de idioma.<br>
	 * [EN] Container of language configurations.
	 */
	private static List<LanguageConfiguration> lconfigs;
	/**
	 * [ES] Contenedor del bot.<br>
	 * [EN] Bot container.
	 */
	private static Bot bot = null;
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
	 * @return [ES] instancia de BotRunner(Singleton).<br>[ES] instance of BotRunner(Singleton).
	 */
	public static BotRunner getInstance() {
		return BotRunner.instance;
	}
	/**
	 * [ES] Devuelve la instancia del bot.<br>
	 * [EN] Returns the bot instance.
	 * @return [ES] instancia del bot. <br>[EN] instance of bot.
	 */
	public static Bot getBot() {
		return bot;
	}
	/**
	 * [ES] Registra bot en BotRunner.<br>
	 * [EN] Registers bot in BotRunner.
	 * @param bot <br>[ES] el bot de la aplicación. [EN] the application bot.
	 */
	private void registerBot(Bot bot) {
		bot.guildConfigManager = GuildConfigManager.startService(bot);
		bot.responseCommandManager = ResponseCommandManager.startService(bot);
		bot.autoHelpCommand = new AutoHelpCommand(bot);
		bot.builder.addEventListeners(bot);
		bot.setRegistered();
		BotRunner.bot=bot;
	}
	/**
	 * [ES] Inicia BotRunner en modo SingleBot.<br>
	 * [EN] Starts BotRunner in SingleBot mode.
	 * @param bot <br>[ES] el bot de la aplicación. [EN] the application bot.
	 * @throws Exception [ES] No se puede inicializar BotRunner mas de una vez.<br>
	 * [EN] can't initialice BotRunner more than one time.
	 * 	 */
	public static void init(Bot bot) throws Exception {
		if(instance.started)
			throw new Exception("The Bot Runner is already initialized!");
		instance.started=true;
		instance.defaultLanguage=bot.getDefaultLanguage();
		instance.registerBot(bot);
		instance.initialize();
	}
	/**
	 * [ES] Agrega una configuración de idioma.<br>
	 * [EN] Adds a language configuration.
	 * @param config <br>[ES] la configuración a agregar. [EN] the configuration to add.
	 */
	public static final void addConfiguration(LanguageConfiguration config) {
		if(instance==null)
			instance = new BotRunner();
		lconfigs.add(config);
	}
	/**
	 * [ES] Inicializa todos los objetos del framework.<br>
	 * [EN] Initializes all the framework objects.
	 * @throws Exception [ES] excepciones de 'Reflection'. [EN] exceptions of 'Reflection'.
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
				Set<Class<?>> scmds = r.getTypesAnnotatedWith(SlashCommand.class);
				Set<Class<?>> coms = r.getTypesAnnotatedWith(ActionContainer.class);
				Set<Class<?>> vals = r.getTypesAnnotatedWith(ValuesContainer.class);
				Set<Class<?>> xcs = r.getTypesAnnotatedWith(ResponseCommandContainer.class);
				Set<Class<?>> cats = r.getTypesAnnotatedWith(Category.class);
				Set<Class<?>> cfgs = r.getTypesAnnotatedWith(Configuration.class);
				if(scmds.size()>0) {
					for(Class<?> c : scmds) {
						SlashCommand sca = c.getDeclaredAnnotation(SlashCommand.class);
						if(SlashCommandObject.find(sca.name().toLowerCase())!=null) {
							continue;
						}
						Method[] ms;
						Class<?>[] cs = c.getDeclaredClasses();
						ArrayList<SlashSubGroup> slashSubGroupList = new ArrayList<>();
						ArrayList<SlashSubCommand> slashSubCommandList = null;
						SlashCommandObject slashCommand = null;
						if(cs.length>0) {
							for(Class<?> c1 : cs) {
								SlashCommandSubgroup scga = c1.getDeclaredAnnotation(SlashCommandSubgroup.class);
								if(scga==null)
									continue;
								ms = c1.getDeclaredMethods();
								if(ms.length>0) {
									slashSubCommandList = new ArrayList<>();
									for(Method m : ms) {
										SlashCommandAction scaa = m.getDeclaredAnnotation(SlashCommandAction.class);
										if(scaa==null)
											continue;
										Option[] options = m.getDeclaredAnnotationsByType(Option.class);
										OptionObject[] ops =null;
										if(options.length>0) {
											ops = new OptionObject[options.length];
											for(int i = 0; i<options.length;i++) {
												ops[i] = new OptionObject(defaultLanguage, options[i].id(), options[i].desc(), null, options[i].enabled(), options[i].visible(), false, options[i].required(), options[i].type());
											}
										}
										slashSubCommandList.add(new SlashSubCommand(scaa.name(), scaa.desc(), ops, m));
									}
								}
								if(slashSubCommandList!=null && slashSubCommandList.size()>0)
									slashSubGroupList.add(new SlashSubGroup(scga.name(),scga.desc(),slashSubCommandList));
							}
						}
						slashSubCommandList = null;
						ms = c.getDeclaredMethods();
						if(ms.length>0) {
							slashSubCommandList = new ArrayList<>();
							for(Method m : ms) {
								SlashCommandAction scaa = m.getDeclaredAnnotation(SlashCommandAction.class);
								if(scaa==null)
									continue;
								Option[] options = m.getDeclaredAnnotationsByType(Option.class);
								OptionObject[] ops =null;
								if(options.length>0) {
									ops = new OptionObject[options.length];
									for(int i = 0; i<options.length;i++) {
										ops[i] = new OptionObject(defaultLanguage, options[i].id(), options[i].desc(), null, options[i].enabled(), options[i].visible(), false, options[i].required(), options[i].type());
									}
								}
								slashSubCommandList.add(new SlashSubCommand(scaa.name(), scaa.desc(), ops, m));
							}
							if(slashSubCommandList.size()==0)
								slashSubCommandList = null;
						}
						if((slashSubCommandList!=null && slashSubCommandList.size()==1) && slashSubGroupList.size()==0) {
							slashCommand = new SlashCommandObject(sca.name(), sca.desc(), slashSubCommandList.get(0), sca.commandMode(), sca.guilds());
						}else if(slashSubCommandList!=null && slashSubGroupList.size()==0){
							slashCommand = new SlashCommandObject(sca.name(), sca.desc(), sca.commandMode(), sca.guilds(), slashSubCommandList);
						}else if(slashSubCommandList==null && slashSubGroupList.size()>0){
							slashCommand = new SlashCommandObject(sca.name(), sca.desc(), sca.commandMode(), sca.guilds(), slashSubGroupList);
						}
						SlashCommandObject.compute(slashCommand);
						CommandManager.registerSlashCommand(slashCommand);
					}
				}
				if(coms.size()>0) {
					for(Class<?> c : coms) {
						Method[] ms = c.getDeclaredMethods();
						for(Method m : ms) {
							ButtonAction ba = m.getAnnotation(ButtonAction.class);
							if(ba!=null) {
								Type[] ts = m.getGenericParameterTypes();
								if(ts.length!=1)
									continue;
								if(!(ts[0].getTypeName().equals(Packet.class.getTypeName()) || ts[0].getTypeName().equals(ButtonPacket.class.getTypeName()) || ts[0].getTypeName().equals(ComponentPacket.class.getTypeName())))
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
								if(!(ts[0].getTypeName().equals(Packet.class.getTypeName()) || ts[0].getTypeName().equals(ResponseCommandPacket.class.getTypeName()) || ts[0].getTypeName().equals(ReplyablePacket.class.getTypeName()) || ts[0].getTypeName().equals(CommandPacket.class.getTypeName())))
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
									mxc.setWaitingExecution(m);
								}else if(xa!=null) {
									n = xa.name();
									mxc.setSuccessExecution(m);
								}else if(xne!=null) {
									n = xne.name();
									mxc.setFailedExecution(m);
								}else if(xf!=null) {
									n = xf.name();
									mxc.setFinallyExecution(m);
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
						cmdcategory = new CategoryObject(defaultLanguage, (catanno.id().equals("")?c.getSimpleName():catanno.id()), catanno.commandMode(), catanno.guilds());
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
							if(!(ts[0].getTypeName().equals(Packet.class.getTypeName()) || ts[0].getTypeName().equals(CommandPacket.class.getTypeName()) || ts[0].getTypeName().equals(ReplyablePacket.class.getTypeName())))
								continue;
							botcmd = new CommandObject(defaultLanguage, cmdanno.id(), cmdcategory, m,(cmdanno.commandMode()==CommandMode.UNSET?catanno.commandMode():cmdanno.commandMode()));
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
								botcmd.addOption(new OptionObject(defaultLanguage, o.id(), o.desc(), alias, o.enabled(), o.visible(), o.noStandar(), o.required(), o.type()));
							}
							botcmd.sortOptions();
							if(botcmd.getCommandMode()!=CommandMode.SLASH_COMMAND_ONLY)
								CommandObject.compute(botcmd);
							if(botcmd.getCommandMode()==CommandMode.CLASIC_AND_SLASH_COMMAND || botcmd.getCommandMode()==CommandMode.SLASH_COMMAND_ONLY) 
								CommandManager.registerSlashCommand(botcmd);
						}
						cmdcategory.sortCommands();
						if(cmdcategory.getCommandMode()!=CommandMode.SLASH_COMMAND_ONLY)
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
				Map<String, Map<Language, String>> catNames = c.getCategoryNames();
				Map<String, Map<Language, String>> catDesc = c.getCategoryDesc();
				Map<String, Map<Language, String>> cmdNames = c.getCommandNames();
				Map<String, Map<Language, String>> cmdDesc = c.getCommandDesc();
				Map<String, Map<Language, String[]>> cmdAlias = c.getCommandAliases();
				Map<String, Map<Language, String>> values = c.getValues();
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
	 * [ES] Método que processa los comandos slash.<br>
	 * [EN] Method which process the slash commands.
	 * @param event <br>[ES] evento a procesar. [EN] event to process.
	 * @param bot <br>[ES] bot de la aplicación. [EN] application bot.
	 */
	public static void run(SlashCommandEvent event, Bot bot) {
		IActionable<?> a = CommandManager.findSlashCommand(event.getName().toLowerCase());
		if(a==null)
			return;
		SlashCommandPacket p = new SlashCommandPacket(bot, event);
		if(a instanceof SlashCommandObject) {
			try {
				((SlashCommandObject)a).execute(p);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if(a instanceof CommandObject) {
			try {
				((CommandObject)a).execute(p);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}/**
	 * [ES] Método que processa los comandos.<br>
	 * [EN] Method which process the commands.
	 * @param event <br>[ES] evento a procesar. [EN] event to process.
	 * @param bot <br>[ES] bot de la aplicación. [EN] application bot.
	 */
	public static void run(MessageReceivedEvent event, Bot bot) throws Exception {
		if(event.isWebhookMessage() && bot.isIgnoreWebHook())
			return;
		String prx = bot.getPrefix();
		String oprx = bot.getOptionsPrefix();
		boolean b = event.isFromGuild();
		String message = event.getMessage().getContentRaw();
		bot.getResponseCommandManager().CheckExtras(event);
		GuildConfig actualGuild = null;
		Language actualLang = bot.getDefaultLanguage();
		if(b) {
			actualGuild = bot.getGuildConfigManager().getConfig(event.getGuild().getIdLong());
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
						event.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
					}
					if(bot.isTypingOnCommand()) {
						try {
							event.getChannel().sendTyping().complete();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					CommandPacketBuilder<MessageReceivedEvent,CommandPacket> builder = new CommandPacketBuilder<MessageReceivedEvent,CommandPacket>(bot,event,actualLang,bot.autoHelpCommand,oprx);
					bot.autoHelpCommand.execute(builder.build());
					return;
				}
				for(String cs : bot.autoHelpCommand.getAlias(actualLang)) {
					if(r.toLowerCase().equalsIgnoreCase(prx + cs.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
						if(!bot.isOn()) {
							event.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
						}
						if(bot.isTypingOnCommand()) {
							try {
								event.getChannel().sendTyping().complete();
							}catch(Exception ex) {
								ex.printStackTrace();
							}
						}
						CommandPacketBuilder<MessageReceivedEvent,CommandPacket> builder = new CommandPacketBuilder<MessageReceivedEvent,CommandPacket>(bot,event,actualLang,bot.autoHelpCommand,oprx);
						bot.autoHelpCommand.execute(builder.build());
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
						event.getChannel().sendMessageEmbeds(eb.build()).queue();
						return;
					}
					if(!bot.isOn() && !cmd.getCategory().isSpecial()) {
						event.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
						return;
					}
					if(!GuildConfig.canRunThisCommand(actualGuild, cmd, event.getChannel(), event.getAuthor()))
						return;
					if(bot.isTypingOnCommand()) {
						try {
							event.getChannel().sendTyping().complete();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					CommandPacketBuilder<MessageReceivedEvent,CommandPacket> builder = new CommandPacketBuilder<MessageReceivedEvent,CommandPacket>(bot,event,actualLang,cmd,oprx);
					cmd.execute(builder.build());
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
								event.getChannel().sendMessageEmbeds(eb.build()).queue();
								return;
							}
							if(!bot.isOn() && !cmd.getCategory().isSpecial()) {
								event.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
								return;
							}
							if(!GuildConfig.canRunThisCommand(actualGuild, cmd, event.getChannel(), event.getAuthor()))
								return;
							if(bot.isTypingOnCommand()) {
								try {
									event.getChannel().sendTyping().complete();
								}catch(Exception ex) {
									ex.printStackTrace();
								}
							}
							CommandPacketBuilder<MessageReceivedEvent,CommandPacket> builder = new CommandPacketBuilder<MessageReceivedEvent,CommandPacket>(bot,event,actualLang,cmd,oprx);
							cmd.execute(builder.build());
							return;
						}
					}
				}
			}
		}
	}
}
