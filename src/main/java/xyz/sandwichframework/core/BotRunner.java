package xyz.sandwichframework.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.annotations.configure.*;
import xyz.sandwichframework.annotations.text.ValueID;
import xyz.sandwichframework.annotations.text.Values;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.LanguageHandler;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.ModelCategory;
import xyz.sandwichframework.models.ModelCommand;
import xyz.sandwichframework.models.ModelExtraCommand;
import xyz.sandwichframework.models.ModelOption;
import xyz.sandwichframework.models.discord.ModelGuild;
import xyz.sandwichframework.models.InputParameter.InputParamType;
/**
 * Clase para configurar el bot.
 * Class for bot configuration.
 * @author Juancho
 * @version 1.0
 */
public class BotRunner {
	//settings
	protected JDA jda = null;
	protected String commandsPrefix = ">";
	protected String optionsPrefix = "-";
	protected boolean autoHelpCommand = false;
	protected String help_title;		//CORREGIR
	protected String help_description;	//CORREGIR
	protected boolean hide_nsfw_category=false;
	private boolean bot_on;
	protected Language def_lang = Language.EN;
	
	//objs
	protected List<ModelCategory> categories;
	protected List<ModelCommand> commands;
	private Set<ModelCategory> hcategories;
	protected Map<String, Map<Language, String>> tempVals;
	private Set<Class<?>> configs;
	protected Reflections reflections;
	protected static BotRunner _self = null;
	protected BotGuildsManager guildsManager;
	public String getPrefix() {
		return commandsPrefix;
	}
	public void setPrefix(String prefix) {
		this.commandsPrefix = prefix;
	}
	public void setJDA(JDA jda) {
		this.jda=jda;
	}
	public JDA getJDA() {
		return this.jda;
	}
	public boolean isAutoHelpCommand() {
		return autoHelpCommand;
	}
	public void setAutoHelpCommand(boolean autoHelpCommand) {
		this.autoHelpCommand = autoHelpCommand;
	}
	public String getOptionsPrefix() {
		return optionsPrefix;
	}
	public void setOptionsPrefix(String optionsPrefix) {
		this.optionsPrefix = optionsPrefix;
	}
	public String getHelp_title() {
		return help_title;
	}
	public void setHelp_title(String help_title) {
		this.help_title = help_title;
	}
	public String getHelp_description() {
		return help_description;
	}
	public void setHelp_description(String help_description) {
		this.help_description = help_description;
	}
	public boolean isHide_nsfw_category() {
		return hide_nsfw_category;
	}
	public void setHide_nsfw_category(boolean hide_nsfw_category) {
		this.hide_nsfw_category = hide_nsfw_category;
	}
	public boolean isBot_on() {
		return bot_on;
	}
	public void setBot_on(boolean bot_is_on) {
		this.bot_on = bot_is_on;
	}
	public Language getDefaultLanguage() {
		return def_lang;
	}
	public void setDefaultLanguage(Language def_lang) {
		this.def_lang = def_lang;
	}
	public BotGuildsManager getGuildsManager() {
		return guildsManager;
	}
	private BotRunner(Language def_lang) {
		this.def_lang= def_lang; 
		commands = (List<ModelCommand>)Collections.synchronizedList(new ArrayList<ModelCommand>());
		hcategories = (Set<ModelCategory>)Collections.synchronizedSet(new HashSet<ModelCategory>());
		configs = (Set<Class<?>>)Collections.synchronizedSet(new HashSet<Class<?>>());
		tempVals = new HashMap<String, Map<Language, String>>();
		this.guildsManager = BotGuildsManager.getManager();
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static BotRunner init(Language def_lang) {
		return _self = new BotRunner(def_lang);
	}
	public static BotRunner init() {
		return _self = new BotRunner(Language.EN);
	}
	private void initialize() throws Exception {
		Package[] pkgs = Package.getPackages();
		boolean u = true;
		for(Package p : pkgs) {
			String pn = p.getName();
			if(pn.startsWith("xyz.sandwichframework.")) {
				if(u) {
					pn = "xyz.sandwichframework.core.util.defaultvalues";
				}else {
					continue;
				}
				
			}
			if(!(pn.startsWith("sun.") || pn.startsWith("java.")
					|| pn.startsWith("com.google") || pn.startsWith("net.dv8tion.jda")
					|| pn.startsWith("javassist") || pn.startsWith("org.reflections")
					|| pn.startsWith("jdk.") || pn.startsWith("org.slf4j.")) || pn.equals("xyz.sandwichframework.core.util.defaultvalues")) { //PACKAGES QUE SE OCUPAN DENTRO DEL FRAMEWORK
				// AQUI VA LO QUE SE DEBE HACER CON CADA PACKAGE
				String[] str = p.getName().split("\\.");
				Reflections r = new Reflections(str[0] + "." + str[1]);
				// ESCANEO DE CATEGORIAS
				Set<Class<?>> vals = r.getTypesAnnotatedWith(Values.class);
				Set<Class<?>> xcs = r.getTypesAnnotatedWith(ExtraCommandContainer.class);
				Set<Class<?>> cats = r.getTypesAnnotatedWith(Category.class);
				Set<Class<?>> cfgs = r.getTypesAnnotatedWith(Configuration.class);
				if(vals.size()>0) {
					for(Class<?> c : vals) {
						Language l = c.getDeclaredAnnotation(Values.class).value();
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
							ModelExtraCommand mxc;
							for(Method m : xcmds) {
								ExtraCmdExecutionName xn = m.getAnnotation(ExtraCmdExecutionName.class);
								ExtraCmdEachExecution xe = m.getAnnotation(ExtraCmdEachExecution.class);
								ExtraCmdAfterExecution xa = m.getAnnotation(ExtraCmdAfterExecution.class);
								ExtraCmdNoExecution xne = m.getAnnotation(ExtraCmdNoExecution.class);
								ExtraCmdFinallyExecution xf = m.getAnnotation(ExtraCmdFinallyExecution.class);
								mxc = new ModelExtraCommand();
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
								mxc.setName(n);
								ModelExtraCommand.compute(mxc);
							}
						}
					}
				}
				if(cats.size()>0) {
					ModelCategory cmdcategory;
					ModelCommand botcmd;
					for(Class<?> c : cats) {
						// AQUI VA LO QUE SE DEBE HACER CON CADA CLASE
						Method[] ms = c.getDeclaredMethods();
						Category catanno = c.getDeclaredAnnotation(Category.class);
						cmdcategory = new ModelCategory(def_lang, (catanno.name().equals("NoID")?c.getSimpleName():catanno.name()));
						if(!catanno.desc().equals("NoDesc")) {
							cmdcategory.setDesc(def_lang, catanno.desc());
						}
						cmdcategory.setNsfw(catanno.nsfw());
						cmdcategory.setVisible(catanno.visible());
						cmdcategory.setSpecial(catanno.isSpecial());
						for(Method m : ms) {
							Command cmdanno = m.getDeclaredAnnotation(Command.class);
							if(cmdanno==null) {
								continue;
							}
							botcmd = new ModelCommand(def_lang, cmdanno.name(), cmdcategory, m);
							botcmd.setAlias(def_lang, cmdanno.alias());
							botcmd.setDesc(def_lang, cmdanno.desc());
							botcmd.setEnabled(cmdanno.enabled());
							botcmd.setVisible(cmdanno.visible());
							Parameter par = m.getDeclaredAnnotation(Parameter.class);
							if(par!=null) {
								botcmd.setParameter(def_lang, par.name());
								botcmd.setParameterDesc(def_lang, par.desc());
							}
							Option[] op = m.getDeclaredAnnotationsByType(Option.class);
							for(Option o : op) {
								botcmd.addOption(new ModelOption(def_lang, o.name(), o.desc(), o.alias(),o.enabled(),o.visible()));
							}
							botcmd.sortOptions();
							commands.add(botcmd);
						}
						cmdcategory.sortCommands();
						hcategories.add(cmdcategory);
					}
				}
				if(cfgs.size()>0) {
					for(Class<?> cc : cfgs) {
						configs.add(cc);
					}
				}
			}
		}
		xyz.sandwichframework.core.Values.initialize(tempVals);
		categories = (List<ModelCategory>)Collections.synchronizedList(new ArrayList<>(hcategories));
		Collections.sort(categories);
		hcategories = null;
		if(configs.size()>0 && commands.size()>0) {
			for(Class<?> c : configs) {
				Language lang = c.getDeclaredAnnotation(Configuration.class).value();
				Field[] fs = c.getDeclaredFields();
				for(Field f : fs) {
					if(f.getDeclaredAnnotation(CategoryID.class)!=null) {
						for(ModelCategory mc : categories) {
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
						for(ModelCommand mc : commands) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CommandID.class).value())) {
								if(f.getDeclaredAnnotation(OptionID.class)!=null) {
									for(ModelOption mo : mc.getOptions()) {
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
								}else if(f.getDeclaredAnnotation(CommandParameter.class)!=null) {
									if(f.getDeclaredAnnotation(ParameterDescription.class)!=null) {
										mc.setParameterDesc(lang, (String)f.get(null));
									}
									if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
										mc.setParameter(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
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
							for(ModelCommand mc : commands) {
								if(mc.getId().equals(id)) {
									if(f.getDeclaredAnnotation(OptionID.class)!=null) {
										for(ModelOption mo : mc.getOptions()) {
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
					}else if(f.getDeclaredAnnotation(MultiCommandIDParameter.class)!=null) {
						for(String id : f.getDeclaredAnnotation(MultiCommandIDParameter.class).value()) {
							for(ModelCommand mc : commands) {
								if(mc.getId().equals(id)) {
									if(f.getDeclaredAnnotation(ParameterDescription.class)!=null) {
										mc.setParameterDesc(lang, (String)f.get(null));
									}
									if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
										mc.setParameter(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	public void listenForCommand(MessageReceivedEvent e) throws Exception {
		if(e.isWebhookMessage())
			return;
		boolean b = e.isFromGuild();
		String message = e.getMessage().getContentRaw();
		ExtraCmdManager.getManager().CheckExtras(e.getMessage());
		ModelGuild actualGuild = null;
		if(message.toLowerCase().startsWith(commandsPrefix) || !b) {
			Language actualLang = def_lang;
			if(b) {
				actualGuild = guildsManager.getGuild(e.getGuild().getIdLong());
				actualLang = actualGuild.getLanguage();
			}
			String r = (message.split(" ")[0]).trim();
			if(autoHelpCommand) {
				for(String cs : AutoHelpCommand.getHelpOptions(actualLang)) {
					if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + cs.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
						if(!bot_on) {
							e.getChannel().sendMessage(LanguageHandler.botOffMessage(actualLang)).queue();
						}
						Thread runner;
						Method ayudacmd = AutoHelpCommand.class.getDeclaredMethod("help", MessageReceivedEvent.class, ArrayList.class);
						ArrayList<InputParameter> pars = findParametros(message);
						CommandRunner cr = new CommandRunner(ayudacmd, pars, e);
						runner = new Thread(cr);
						runner.start();
						return;
					}
				}
			}
			for(ModelCommand cmd : commands) {
				if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + cmd.getName(actualLang).toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cmd.getName(actualLang).toLowerCase())){
					ArrayList<InputParameter> pars = findParametros(actualLang,message,cmd);
					if(!cmd.isEnabled()) {
						if(!cmd.isVisible()) {
							return;
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle(LanguageHandler.commandDisabledMessage(actualLang));
						e.getChannel().sendMessage(eb.build()).queue();
						return;
					}
					if(!bot_on && !cmd.getCategory().isSpecial()) {
						e.getChannel().sendMessage(LanguageHandler.botOffMessage(actualLang)).queue();
						return;
					}
					if(actualGuild!=null) {
						boolean b12 = true;
						if(e.getMember().getRoles().size()>0) {
							b12 = actualGuild.isRoleAllowed(e.getMember().getRoles().get(0).getId());
						}
						if(!actualGuild.isCategoryAllowed(cmd.getCategory().getId())
								|| !actualGuild.isCommandAllowed(cmd.getId())
								|| !b12
								|| !actualGuild.isChannelAllowed(e.getChannel().getId())
								|| !actualGuild.isMemberAllowed(e.getMember().getId())) {
							if(actualGuild.getSpecialRole("admin")==null) {
								return;
							}else {
								boolean rb = false;
								for(Role role : e.getMember().getRoles()) {
									if(actualGuild.getSpecialRole("admin").equals(role.getId())) {
										rb=true;
										break;
									}
								}
								if(!rb)
									return;
							}
						}
					}
					Thread runner;
					CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
					runner = new Thread(cr);
					runner.start();
					return;
				}else {
					for(String a : cmd.getAlias(actualLang)) {
						if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + a.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
							ArrayList<InputParameter> pars = findParametros(actualLang,message,cmd);
							if(!cmd.isEnabled()) {
								if(!cmd.isVisible()) {
									return;
								}
								EmbedBuilder eb = new EmbedBuilder();
								eb.setTitle(LanguageHandler.commandDisabledMessage(actualLang));
								e.getChannel().sendMessage(eb.build()).queue();
								return;
							}
							if(!bot_on && !cmd.getCategory().isSpecial()) {
								e.getChannel().sendMessage(LanguageHandler.botOffMessage(actualLang)).queue();
								return;
							}
							if(actualGuild!=null) {
								List<Role> lr = e.getMember().getRoles();
								boolean lbr = lr.size()>0;
								if(lbr) {
									lbr = !actualGuild.isRoleAllowed(e.getMember().getRoles().get(0).getId());
								}
								if(!actualGuild.isCategoryAllowed(cmd.getCategory().getId())
										|| !actualGuild.isCommandAllowed(cmd.getId())
										|| lbr
										|| !actualGuild.isChannelAllowed(e.getChannel().getId())
										|| !actualGuild.isMemberAllowed(e.getMember().getId())) {
									if(actualGuild.getSpecialRole("admin")==null) {
										return;
									}else {
										boolean rb = false;
										for(Role role : e.getMember().getRoles()) {
											if(actualGuild.getSpecialRole("admin").equals(role.getId())) {
												rb=true;
												break;
											}
										}
										if(!rb)
											return;
									}
								}
							}
							Thread runner;
							CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
							runner = new Thread(cr);
							runner.start();
							return;
						}
					}
				}
			}
		}
	}
	public void listenForPrivateCommand(PrivateMessageReceivedEvent e) throws Exception {/*
		ModelGuild actualGuild = guildsManager.getGuild(e.getAuthor().getJDA().getMutualGuilds(SandwichBot.ActualBot().getJDA().getSelfUser()).get(0).getId());
		String message = e.getMessage().getContentRaw();
		String r = (message.split(" ")[0]).trim();
		if(r.toLowerCase().startsWith(commandsPrefix)) {
			r = r.substring(commandsPrefix.length());
		}
		if(autoHelpCommand) {
			for(String cs : AutoHelpCommand.getHelpOptions(actualGuild.getLanguage())) {
				if(r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
					if(!bot_on) {
						e.getChannel().sendMessage(LanguageHandler.botOffMessage(actualGuild.getLanguage())).queue();
					}
					Thread runner;
					Method ayudacmd = AutoHelpCommand.class.getDeclaredMethod("help", MessageReceivedEvent.class, ArrayList.class);
					ArrayList<InputParameter> pars = findParametros(message);
					CommandRunner cr = new CommandRunner(ayudacmd, pars, e);
					runner = new Thread(cr);
					runner.start();
					return;
				}
			}
		}
		for(ModelCommand cmd : commands) {
			if(r.toLowerCase().equalsIgnoreCase(cmd.getName(actualGuild.getLanguage()).toLowerCase())){
				ArrayList<InputParameter> pars = findParametros(actualGuild.getLanguage(),message,cmd);
				if(!cmd.isEnabled()) {
					if(!cmd.isVisible()) {
						return;
					}
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle(LanguageHandler.commandDisabledMessage(actualGuild.getLanguage()));
					e.getChannel().sendMessage(eb.build()).queue();
					return;
				}
				if(!bot_on && !cmd.getCategory().isSpecial()) {
					e.getChannel().sendMessage(LanguageHandler.botOffMessage(actualGuild.getLanguage())).queue();
				}
				Thread runner;
				CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
				runner = new Thread(cr);
				runner.start();
				return;
			}else {
				for(String a : cmd.getAlias(actualGuild.getLanguage())) {
					if(r.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
						ArrayList<InputParameter> pars = findParametros(actualGuild.getLanguage(),message,cmd);
						if(!cmd.isEnabled()) {
							if(!cmd.isVisible()) {
								return;
							}
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle(LanguageHandler.commandDisabledMessage(actualGuild.getLanguage()));
							e.getChannel().sendMessage(eb.build()).queue();
							return;
						}
						if(!bot_on && !cmd.getCategory().isSpecial()) {
							e.getChannel().sendMessage(LanguageHandler.botOffMessage(actualGuild.getLanguage())).queue();
						}
						Thread runner;
						CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
						runner = new Thread(cr);
						runner.start();
						return;
					}
				}
			}
		}*/
	}
	private ArrayList<InputParameter> findParametros(Language lang, String input,ModelCommand command){
		String[] s = input.split(" ");
		ArrayList<InputParameter> lista = new ArrayList<InputParameter>();
		InputParameter p = new InputParameter();
		for(int i=1;i<s.length;i++) {
			if((s[i]).startsWith(optionsPrefix)) {
				p=null;
				p = new InputParameter();
				for(ModelOption mo : command.getOptions()) {
					if(s[i].toLowerCase().equalsIgnoreCase(optionsPrefix + mo.getName(lang))) {
						p.setKey(mo.getName(lang));
						p.setType(InputParamType.Standar);
						break;
					}else {
						for(String a : mo.getAlias(lang)) {
							if(s[i].toLowerCase().equalsIgnoreCase(optionsPrefix+a)) {
								p.setKey(mo.getName(lang));
								p.setType(InputParamType.Standar);
								break;
							}
						}
					}
				}
				if(p.getType() == InputParamType.Custom) {
					for(String hs : AutoHelpCommand.getHelpOptions(lang)) {
						if(s[i].equalsIgnoreCase(optionsPrefix+hs)) {
							p.setKey(AutoHelpCommand.AUTO_HELP_KEY);
							p.setType(InputParamType.Standar);
							break;
						}
					}
					if(p.getType() == InputParamType.Custom) {
						p.setType(InputParamType.Invalid);
						p.setKey(s[i]);
					}
				}
			}else if(i==1) {
				p.setType(InputParamType.Custom);
				p.setKey("custom");
				p.setValue(s[i]);
			}else if(!p.getValueAsString().equalsIgnoreCase("none")){
				p.setValue(p.getValueAsString()+" "+s[i]);
			}else {
				p.setValue(s[i]);
			}
			if(lista.size()>0) {
				if(lista.lastIndexOf(p) == -1) {
					lista.add(p);
				}
			}else {
				lista.add(p);
			}
		}
		return lista;
	}
	private ArrayList<InputParameter> findParametros(String input){
		String[] s = input.split(" ");
		ArrayList<InputParameter> lista = new ArrayList<InputParameter>();
		InputParameter p = new InputParameter();
		for(int i=1;i<s.length;i++) {
			if(i==1) {
				p.setType(InputParamType.Custom);
				p.setKey("custom");
				p.setValue(s[i]);
			}else if((s[i]).startsWith(optionsPrefix)) {
				p=null;
				p = new InputParameter();
				if(s[i].toLowerCase().startsWith(optionsPrefix)) {
					p.setKey(s[i]);
					p.setType(InputParamType.Invalid);
					break;
				}
			}else if(!p.getValueAsString().equalsIgnoreCase("none")){
				p.setValue(p.getValueAsString()+" "+s[i]);
			}else {
				p.setValue(s[i]);
			}
			if(lista.size()>0) {
				if(lista.lastIndexOf(p) == -1) {
					lista.add(p);
				}
			}else {
				lista.add(p);
			}
		}
		return lista;
	}
}
