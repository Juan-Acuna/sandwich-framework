package xyz.sandwichframework.models;
/**
 * Representa la entrada del usuario.
 * Represents the user's input.
 * @author Juancho
 * @version 1.0
 */
public class InputParameter {
	private String key = "none";
	private String value = "none";
	private InputParamType type;
	
	public InputParameter() {
		type=InputParamType.Custom;
	}
	public InputParameter(String clave) {
		this.key=clave;
		type=InputParamType.Custom;
	}
	public InputParameter(String clave, String valor) {
		this.key=clave;
		this.value=valor;
	}
	public InputParameter(String clave, InputParamType tipo) {
		this.key=clave;
		this.type=tipo;
	}
	public InputParameter(String clave, String valor, InputParamType tipo) {
		this.key=clave;
		this.value=valor;
		this.type=tipo;
	}
	public void setKey(String clave) {
		this.key=clave;
	}
	public void setValue(String valor) {
		this.value=valor;
	}
	public void setType(InputParamType tipo) {
		this.type=tipo;
	}
	public String getKey() {
		return key;
	}
	public InputParamType getType() {
		return type;
	}
	public String getValueAsString() {
		return value;
	}
	public int getValueAsInt() {
		if(value.equals("none")) {
			return -1;
		}
		return Integer.parseInt((String)value);
	}
	public double getValueAsDouble() {
		if(value.equals("none")) {
			return -1;
		}
		return Double.parseDouble(value);
	}
	public float getValueAsFloat() {
		if(value.equals("none")) {
			return -1f;
		}
		return Float.parseFloat(value);
	}
	public char getValueAsChar() {
		if(value.equals("none")) {
			return '\0';
		}
		return value.charAt(0);
	}
	public boolean getValueAsBoolean(String[] standarTrue) {
		for(String s : standarTrue) {
			if(value.equals(s)) {
				return true;
			}
		}
		return false;
	}
	public boolean getValueAsBoolean(String textTrue) {
		if(value.equals(textTrue)){
			return true;
		}
		return false;
	}
	public static enum InputParamType{
		Standar,
		Custom,
		Invalid
	}
}