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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.development.InDevelopment;
import com.jaxsandwich.sandwichcord.development.NotDocumented;

import net.dv8tion.jda.api.interactions.components.Button;

@NotDocumented
@InDevelopment
public class ComponentManager {
	@NotDocumented
	private Map<Long, Map<String, Button>> cont;
	@NotDocumented
	private Bot bot;
	@NotDocumented
	private ComponentManager(Bot bot) {
		this.bot=bot;
		this.cont = Collections.synchronizedMap(new HashMap<Long, Map<String, Button>>());
	}
	@NotDocumented
	protected ComponentManager startService(Bot bot) {
		return new ComponentManager(bot);
	}
	public Button registerButton(Button button) {
		return button;
	}
}
