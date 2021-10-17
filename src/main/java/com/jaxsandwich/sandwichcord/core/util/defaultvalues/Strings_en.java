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

package com.jaxsandwich.sandwichcord.core.util.defaultvalues;

import com.jaxsandwich.sandwichcord.annotations.text.*;
import com.jaxsandwich.sandwichcord.core.util.Language;
@ValuesContainer(Language.EN)
/**
 * [ES] Valores por defecto dentro del framework. En este caso: Inglés.<br>
 * [EN] Defaul values inside the framework. In this case: English.
 * @author Juancho
 * @version 1.0
 * @since 0.4.2
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
	public static final String H_CMD_OPT = "NoStandarOption: %s";

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
