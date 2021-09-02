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

package com.jaxsandwich.sandwichcord.models.components;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.development.NotDocumented;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

/**
 * [ES] Representa a la acción que se ejecuta cuando un botón específico es presionado.<br>
 * [EN] Represents the action which is executed when a specific button is pressed.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.8.0
 * */
public class ButtonActionObject implements Comparable<ButtonActionObject>{
	@NotDocumented
	private static Map<String, ButtonActionObject> cont = Collections.synchronizedMap(new HashMap<String, ButtonActionObject>());
	@NotDocumented
	private String id;
	@NotDocumented
	private Method action;
	@NotDocumented
	public ButtonActionObject(String id, Method action) {
		this.id=id;
		this.action=action;
	}
	@NotDocumented
	public static final void compute(ButtonActionObject button) {
		cont.put(button.id.toLowerCase(), button);
	}
	@NotDocumented
	public static final ButtonActionObject find(String id) {
		return cont.get(id.toLowerCase());
	}
	@NotDocumented
	public static final ArrayList<ButtonActionObject> getAsList() {
		ArrayList<ButtonActionObject> l = new ArrayList<ButtonActionObject>(cont.values());
		Collections.sort(l);
		return l;
	}
	@NotDocumented
	public static final int getCommandCount() {
		return cont.size();
	}
	@Override
	public int compareTo(ButtonActionObject b) {
		return this.id.compareTo(b.id);
	}
	@NotDocumented
	public void executeAction(ButtonClickEvent event) {
		try {
			this.action.invoke(null, event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
