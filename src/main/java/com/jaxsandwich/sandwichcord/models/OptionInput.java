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

package com.jaxsandwich.sandwichcord.models;

import com.jaxsandwich.sandwichcord.development.HalfDocumented;
import com.jaxsandwich.sandwichcord.development.NotDocumented;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

/**
 * [ES] Representa los parametros/opciones ingresados por el usuario.<br>
 * [EN] Represents the parameters/options entered by the user.
 * @author Juancho
 * @version 2.1
 * @since 0.0.1
 */
public class OptionInput {
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	private static final String[] TRUE_ARRAY ={"1","s","si","t","tru","true","verdad","verdadero","y","yes"};
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	private String key = null;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	private String value = null;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	private OptionType valueType;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	private OptionInputType type;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	private OptionMapping map = null;
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInput() {
		type=OptionInputType.NO_STANDAR;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInput(OptionMapping map) {
		this.key = map.getName().toLowerCase();
		this.value = map.getAsString();
		this.type=OptionInputType.STANDAR;
		this.valueType = map.getType();
		this.map=map;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInput(String clave) {
		this.key=clave.toLowerCase();
		type=OptionInputType.NO_STANDAR;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInput(String clave, String valor) {
		this.key=clave.toLowerCase();
		this.value=valor;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInput(String clave, OptionInputType tipo) {
		this.key=clave.toLowerCase();
		this.type=tipo;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInput(String clave, String valor, OptionInputType tipo) {
		this.key=clave.toLowerCase();
		this.value=valor;
		this.type=tipo;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public void setKey(String clave) {
		this.key=clave.toLowerCase();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public void setValue(String valor) {
		this.value=valor;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public void setType(OptionInputType tipo) {
		this.type=tipo;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public String getKey() {
		return key;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public OptionInputType getType() {
		return type;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public String getValueAsString() {
		return value;
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
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
	@NotDocumented
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
	@NotDocumented
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
	@NotDocumented
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
	@NotDocumented
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
	@NotDocumented
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
	@NotDocumented
	public GuildChannel getValueAsGuildChannel() {
		return this.map.getAsGuildChannel();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public Member getValueAsMember() {
		return this.map.getAsMember();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public IMentionable getValueAsMentionable() {
		return this.map.getAsMentionable();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public MessageChannel getValueAsMessageChannel() {
		return this.map.getAsMessageChannel();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public Role getValueAsRole() {
		return this.map.getAsRole();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public User getValueAsUser() {
		return this.map.getAsUser();
	}
	/**
	 * [ES] <br>
	 * [EN]
	 */
	@NotDocumented
	public ChannelType getValueAsChannelType() {
		return this.map.getChannelType();
	}
	
	/**
	 * [ES] Tipo de {@link OptionInput}.<br>
	 * [EN] Type of {@link OptionInput}.
	 */
	public static enum OptionInputType{
		/**
		 * [ES] Parametro/opción preestablecida por el comando que sigue el formato [caracter][opción].<br>
		 * Ejemplo: para opción 'p' con caracter de opción '-' el formato sería: -p.<br>
		 * Para comandos slash todas las opciones pertenecen a este tipo.<br>
		 * [EN] NoStandarOption/option preset by command wich follows the format [character][option].<br>
		 * Example: for option 'p' and option character '-' the format is: -p.<br>
		 * For slash commands all options belong to this type.
		 */
		STANDAR,
		/**
		 * [ES] Parametro ingresado por el usuario que no tiene el formato de un parametro tipo {@link OptionInputType#STANDAR}. Solo puede existir uno por comando.<br>
		 * [EN]
		 */
		@HalfDocumented
		NO_STANDAR,
		/**
		 * [ES] <br>
		 * [EN]
		 */
		@NotDocumented
		CUSTOM
	}
}