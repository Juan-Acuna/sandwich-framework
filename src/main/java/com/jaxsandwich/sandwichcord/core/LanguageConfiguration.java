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

import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.development.NotDocumented;

@NotDocumented
public abstract class LanguageConfiguration {
	@NotDocumented
	private Map<String, Map<Language, String>> catNames;
	@NotDocumented
	private Map<String, Map<Language, String>> catDesc;
	@NotDocumented
	private Map<String, Map<Language, String>> cmdNames;
	@NotDocumented
	private Map<String, Map<Language, String>> cmdDesc;
	@NotDocumented
	private Map<String, Map<Language, String[]>> cmdAliases;
	@NotDocumented
	private Map<String, Map<Language, String>> values;
	@NotDocumented
	public LanguageConfiguration() {
		catNames = new HashMap<String, Map<Language, String>>();
		catDesc = new HashMap<String, Map<Language, String>>();
		cmdNames = new HashMap<String, Map<Language, String>>();
		cmdDesc = new HashMap<String, Map<Language, String>>();
		cmdAliases = new HashMap<String, Map<Language, String[]>>();
		values = new HashMap<String, Map<Language, String>>();
	}
	@NotDocumented
	public final void addCategoryNameTranslation(Language lang, String id, String translation) {
		if(catNames.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, translation);
			catNames.put(id, m);
		}else {
			catNames.get(id).put(lang, translation);
		}
	}
	@NotDocumented
	public final void addCommandNameTranslation(Language lang, String id, String translation) {
		if(cmdNames.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, translation);
			cmdNames.put(id, m);
		}else {
			cmdNames.get(id).put(lang, translation);
		}
	}
	@NotDocumented
	public final void addCategoryDescription(Language lang, String id, String description) {
		if(catDesc.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, description);
			catDesc.put(id, m);
		}else {
			catDesc.get(id).put(lang, description);
		}
	}
	@NotDocumented
	public final void addCommandDescription(Language lang, String id, String description) {
		if(cmdDesc.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, description);
			cmdDesc.put(id, m);
		}else {
			cmdDesc.get(id).put(lang, description);
		}
	}
	@NotDocumented
	public final void addCommandAliases(Language lang, String id, String...aliases) {
		if(cmdAliases.get(id)==null) {
			Map<Language, String[]> m = new HashMap<Language, String[]>();
			m.put(lang, aliases);
			cmdAliases.put(id, m);
		}else {
			cmdAliases.get(id).put(lang, aliases);
		}
	}
	@NotDocumented
	public final void addStringValue(Language lang, String id, String value) {
		if(values.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, value);
			values.put(id, m);
		}else {
			values.get(id).put(lang, value);
		}
	}
	@NotDocumented
	protected final Map<String, Map<Language, String>> getValues() {
		return values;
	}
	@NotDocumented
	protected final Map<String, Map<Language, String>> getCatNames() {
		return catNames;
	}
	@NotDocumented
	protected final Map<String, Map<Language, String>> getCatDesc() {
		return catDesc;
	}
	@NotDocumented
	protected final Map<String, Map<Language, String>> getCmdNames() {
		return cmdNames;
	}
	@NotDocumented
	protected final Map<String, Map<Language, String>> getCmdDesc() {
		return cmdDesc;
	}
	@NotDocumented
	protected final Map<String, Map<Language, String[]>> getCmdAliases() {
		return cmdAliases;
	}
	
}
