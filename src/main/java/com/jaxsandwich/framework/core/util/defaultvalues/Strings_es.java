package com.jaxsandwich.framework.core.util.defaultvalues;

import com.jaxsandwich.framework.annotations.text.*;
import com.jaxsandwich.framework.core.util.Language;
@ValuesContainer(Language.ES)
/**
 * [ES] Valores por defecto dentro del framework. En este caso: Español.<br>
 * [EN] Defaul values inside the framework. In this case: Spanish.
 * @author Juancho
 * @version 1.0
 */
public class Strings_es {
	@ValueID("xyz-sndwch-def-hlp-title")
	public static final String HLP_T = "Lista de comandos por categorias.";
	
	@ValueID("xyz-sndwch-def-hlp-desc")
	public static final String HLP_D = "";
	
	@ValueID("xyz-sndwch-def-hlp-cmdhint")
	public static final String H_CMD_HINT = "\nPara saber más sobre este comando, escriba %sayuda $%s.";
	
	@ValueID("xyz-sndwch-def-hlp-cathint")
	public static final String H_CAT_HINT = "\nPara más detalles, escriba %sayuda %s.";

	@ValueID("xyz-sndwch-def-hlp-cattitle")
	public static final String H_CAT_T = "Categoría: %s";

	@ValueID("xyz-sndwch-def-hlp-cats")
	public static final String H_CATS = "Categorias";
	
	@ValueID("xyz-sndwch-def-hlp-catcmd")
	public static final String H_CAT_CMD = "\n*Comandos*:   %s";

	@ValueID("xyz-sndwch-def-hlp-cmd-opts")
	public static final String H_CMD_OPTS = "Parámetros/Opciones:";

	@ValueID("xyz-sndwch-def-hlp-cmd-opt")
	public static final String H_CMD_OPT = "Parametro: %s";

	@ValueID("xyz-sndwch-def-hlp-dup")
	public static final String H_DUP = "```'%s' tambien hace referencia a un comando, para consultar por este, anteponga '$' para diferenciar de la categoria.```";

	@ValueID("xyz-sndwch-def-hlp-cat-nf")
	public static final String H_CNF = "No se encontró categoría/comando.";
	
	@ValueID("xyz-sndwch-def-t-dnf")
	public static final String T_0 = "No se encontró descripción.";
	
	@ValueID("xyz-sndwch-def-t-na")
	public static final String T_1 = "No disponible";
	
	@ValueID("xyz-sndwch-def-t-boff")
	public static final String T_2 = "Este bot se encuentra fuera de servicio.";
	
	@ValueID("xyz-sndwch-def-t-ncmd")
	public static final String T_3 = "Este comando no se encuentra habilitado. :pensive:";
	
}
