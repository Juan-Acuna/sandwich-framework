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

package com.jaxsandwich.sandwichcord.core.builders;

import java.util.ArrayList;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CommandBase;
import com.jaxsandwich.sandwichcord.models.OptionInput;
import com.jaxsandwich.sandwichcord.models.OptionObject;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.jaxsandwich.sandwichcord.models.OptionInput.OptionInputType;

/**
 * [ES] Herramienta para la construccion de {@link ReplyablePacket}s.<br>
 * [EN] Tool for building of {@link ReplyablePacket}s.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public abstract class ReplyablePacketBuilder<T extends ReplyablePacket<?>> extends PacketBuilder<T> {

	/**
	 * [ES] Analiza la entrada y devuelve los parametros del ReplyablePacket.<br>
	 * [EN] Analyzes the input and returns the paramters of the ReplyablePacket.
	 * @return [ES] opciones ingresadas por el usuario.<br>[EN] user entered options.
	 * @param lang <br>[ES] idioma del ReplyablePacket. [EN] language of the ReplyablePacket.
	 * @param input <br>[ES] texto de entrada. [EN] text input.
	 * @param command <br>[ES] comando objetivo. [EN] target command.
	 * @param oprx <br>[ES] caracter(es) de prefijo de opcion. [EN] option prefix character(s).
	 */
	protected OptionInput[] findParameters(Language lang, String input,CommandBase command, String oprx){
		String[] s = input.split(" ");
		if(s.length<=1)
			return new OptionInput[0];
		ArrayList<OptionInput> lista = new ArrayList<OptionInput>();
		OptionInput p = new OptionInput();
		for(int i=1;i<s.length;i++) {
			if((s[i]).startsWith(oprx)) {
				p=null;
				p = new OptionInput();
				for(OptionObject mo : command.getOptions()) {
					if(s[i].toLowerCase().equalsIgnoreCase(oprx + mo.getName(lang))) {
						p.setKey(mo.getName(lang));
						p.setType(OptionInputType.STANDAR);
						break;
					}else {
						for(String a : mo.getAlias(lang)) {
							if(s[i].toLowerCase().equalsIgnoreCase(oprx+a)) {
								p.setKey(mo.getName(lang));
								p.setType(OptionInputType.STANDAR);
								break;
							}
						}
					}
				}
				if(p.getType() == OptionInputType.NO_STANDAR) {
					p.setType(OptionInputType.CUSTOM);
					p.setKey(s[i]);
				}
			}else if(i==1) {
				p.setType(OptionInputType.NO_STANDAR);
				p.setKey("nostandar");
				p.setValue(s[i]);
			}else if(p.getValueAsString()!=null){
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
		OptionInput[] res = new OptionInput[lista.size()];
		return lista.toArray(res);
	}
}
