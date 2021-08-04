package com.jaxsandwich.sandwichcord.models;
/**
 * [ES] Representa los parametros/opciones ingresados por el usuario.<br>
 * [EN] Represents the parameters/options entered by the user.
 * @author Juancho
 * @version 1.2
 */
public class InputParameter {
	/**
	 * [ES] <br>
	 * [EN]
	 */
	private static final String[] TRUE_ARRAY ={"1","s","si","t","tru","true","verdad","verdadero","y","yes"};
	/**
	 * [ES] <br>
	 * [EN]
	 */
	private String key = null;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	private String value = null;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	private InputParamType type;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public InputParameter() {
		type=InputParamType.NO_STANDAR;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public InputParameter(String clave) {
		this.key=clave;
		type=InputParamType.NO_STANDAR;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public InputParameter(String clave, String valor) {
		this.key=clave;
		this.value=valor;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public InputParameter(String clave, InputParamType tipo) {
		this.key=clave;
		this.type=tipo;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public InputParameter(String clave, String valor, InputParamType tipo) {
		this.key=clave;
		this.value=valor;
		this.type=tipo;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public void setKey(String clave) {
		this.key=clave;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public void setValue(String valor) {
		this.value=valor;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public void setType(InputParamType tipo) {
		this.type=tipo;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public String getKey() {
		return key;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public InputParamType getType() {
		return type;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public String getValueAsString() {
		return value;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public int getValueAsInt() {
		if(value==null) {
			return -1;
		}
		return Integer.parseInt((String)value);
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public double getValueAsDouble() {
		if(value==null) {
			return -1;
		}
		return Double.parseDouble(value);
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public float getValueAsFloat() {
		if(value==null) {
			return -1f;
		}
		return Float.parseFloat(value);
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public char getValueAsChar() {
		if(value==null) {
			return '\0';
		}
		return value.charAt(0);
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public boolean getValueAsBoolean(String[] standarTrue) {
		if(value==null)
			return false;
		for(String s : standarTrue) {
			if(value.equals(s)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public boolean getValueAsBoolean() {
		if(value==null)
			return false;
		for(String s : TRUE_ARRAY) {
			if(value.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	public boolean getValueAsBoolean(String textTrue) {
		if(value==null)
			return false;
		if(value.equals(textTrue)){
			return true;
		}
		return false;
	}
	/**
	 * [ES] Tipo de {@link InputParameter}.<br>
	 * [EN] Type of {@link InputParameter}.
	 */
	public static enum InputParamType{
		/**
		 * [ES] Parametro/opción preestablecida por el comando que sigue el formato [caracter][opción].<br>
		 * Ejemplo: para opción 'p' con caracter de opción '-' el formato sería: -p.<br>
		 * [EN] Parameter/option preset by command wich follows the format [character][option].<br>
		 * Example: for option 'p' and option character '-' the format is: -p.
		 */
		STANDAR,
		/**
		 * [ES] Parametro ingresado por el usuario que no tiene el formato de un parametro tipo {@link InputParamType#STANDAR}. Solo puede existir uno por comando.<br>
		 * [EN]
		 */
		NO_STANDAR,
		/**
		 * [ES] <br>
		 * [EN]
		 */
		CUSTOM
	}
}