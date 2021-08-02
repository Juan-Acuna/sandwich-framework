package com.jaxsandwich.framework.core.util.defaultvalues;

import com.jaxsandwich.framework.annotations.text.*;
import com.jaxsandwich.framework.core.util.Language;
@ValuesContainer(Language.EN)
/**
 * [ES] Valores por defecto dentro del framework. En este caso: Inglés.<br>
 * [EN] Defaul values inside the framework. In this case: English.
 * @author Juancho
 * @version 1.0
 */
public class Strings_en {
	@ValueID("xyz-sndwch-def-hlp-title")
	public static final String HLP_T = "Command list, sorted by categories.";
	
	@ValueID("xyz-sndwch-def-hlp-desc")
	public static final String HLP_D = "";
	
	@ValueID("xyz-sndwch-def-hlp-cmdhint")
	public static final String H_CMD_HINT = "\nTo know more about this command, type %help $%s.";
	
	@ValueID("xyz-sndwch-def-hlp-cathint")
	public static final String H_CAT_HINT = "\nFor more details, type %shelp %s.";

	@ValueID("xyz-sndwch-def-hlp-cattitle")
	public static final String H_CAT_T = "Category: %s";

	@ValueID("xyz-sndwch-def-hlp-cats")
	public static final String H_CATS = "Categories";
	
	@ValueID("xyz-sndwch-def-hlp-catcmd")
	public static final String H_CAT_CMD = "\n*Commands*:   %s";

	@ValueID("xyz-sndwch-def-hlp-cmd-opts")
	public static final String H_CMD_OPTS = "Parameters/Options:";

	@ValueID("xyz-sndwch-def-hlp-cmd-opt")
	public static final String H_CMD_OPT = "Parameter: %s";

	@ValueID("xyz-sndwch-def-hlp-dup")
	public static final String H_DUP = "```'%s' also references a command, to get info about the command, type '$' after the command.```";

	@ValueID("xyz-sndwch-def-hlp-cat-nf")
	public static final String H_CNF = "Category/command not found.";
	
	@ValueID("xyz-sndwch-def-t-dnf")
	public static final String T_0 = "Description not found";
	
	@ValueID("xyz-sndwch-def-t-na")
	public static final String T_1 = "Not available";
	
	@ValueID("xyz-sndwch-def-t-boff")
	public static final String T_2 = "This bot is out of order.";
	
	@ValueID("xyz-sndwch-def-t-ncmd")
	public static final String T_3 = "This command is not enabled. :pensive:";
	
}
