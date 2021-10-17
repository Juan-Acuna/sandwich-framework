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
import com.jaxsandwich.sandwichcord.development.HalfDocumented;
import com.jaxsandwich.sandwichcord.development.InDevelopment;
import com.jaxsandwich.sandwichcord.development.NotDocumented;
@HalfDocumented
/**
 * [ES] Representa una configuración de idioma para los textos del bot.<br>
 * [EN] Represents a language configuration for the texts of bot.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.8.0
 * */
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
	@InDevelopment
	@NotDocumented
	private Map<String, Map<String, Map<Language, String>>> optNames;
	@InDevelopment
	@NotDocumented
	private Map<String, Map<String, Map<Language, String[]>>> optAliases;
	@NotDocumented
	private Map<String, Map<Language, String>> values;
	/**
	 * [ES] Constructor de LanguageConfiguration.<br>
	 * [EN] Constructor of LanguageConfiguration.
	 */
	public LanguageConfiguration() {
		catNames = new HashMap<String, Map<Language, String>>();
		catDesc = new HashMap<String, Map<Language, String>>();
		cmdNames = new HashMap<String, Map<Language, String>>();
		cmdDesc = new HashMap<String, Map<Language, String>>();
		cmdAliases = new HashMap<String, Map<Language, String[]>>();
		optNames = new HashMap<String, Map<String, Map<Language, String>>>();
		optAliases = new HashMap<String, Map<String, Map<Language, String[]>>>();
		values = new HashMap<String, Map<Language, String>>();
	}
	/**
	 * [ES] Agrega una traducción al nombre de la categoría especificada.<br>
	 * [EN] Adds a translation for the name of specified category.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param id <br>[ES] identificador de la categoría. [EN] category identifier.
	 * @param translation <br>[ES] nombre traducido. [EN] translated name.
	 */
	public final void addCategoryNameTranslation(Language lang, String id, String translation) {
		id=id.toLowerCase();
		if(catNames.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, translation);
			catNames.put(id, m);
		}else {
			catNames.get(id).put(lang, translation);
		}
	}
	/**
	 * [ES] Agrega una traducción al nombre del comando especificado.<br>
	 * [EN] Adds a translation for the name of specified command.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param id <br>[ES] identificador del comando. [EN] command identifier.
	 * @param translation <br>[ES] nombre traducido. [EN] translated name.
	 */
	public final void addCommandNameTranslation(Language lang, String id, String translation) {
		id=id.toLowerCase();
		if(cmdNames.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, translation);
			cmdNames.put(id, m);
		}else {
			cmdNames.get(id).put(lang, translation);
		}
	}
	/**
	 * [ES] Agrega una traducción a la descripción de la categoría especificada.<br>
	 * [EN] Adds a translation for the description of specified category.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param id <br>[ES] identificador de la categoría. [EN] category identifier.
	 * @param description <br>[ES] descripción traducida. [EN] translated description.
	 */
	public final void addCategoryDescription(Language lang, String id, String description) {
		id=id.toLowerCase();
		if(catDesc.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, description);
			catDesc.put(id, m);
		}else {
			catDesc.get(id).put(lang, description);
		}
	}
	/**
	 * [ES] Agrega una traducción a la descripción del comando especificado.<br>
	 * [EN] Adds a translation for the description of specified command.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param id <br>[ES] identificador del comando. [EN] command identifier.
	 * @param description <br>[ES] descripción traducida. [EN] translated description.
	 */
	public final void addCommandDescription(Language lang, String id, String description) {
		id=id.toLowerCase();
		if(cmdDesc.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, description);
			cmdDesc.put(id, m);
		}else {
			cmdDesc.get(id).put(lang, description);
		}
	}
	/**
	 * [ES] Agrega alias traducidos al comando especificado.<br>
	 * [EN] Adds translated aliases for specified command.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param id <br>[ES] identificador del comando. [EN] command identifier.
	 * @param aliases <br>[ES] alias del comando en este idioma. [EN] command aliases in this language.
	 */
	public final void addCommandAliases(Language lang, String id, String...aliases) {
		id=id.toLowerCase();
		if(cmdAliases.get(id)==null) {
			Map<Language, String[]> m = new HashMap<Language, String[]>();
			m.put(lang, aliases);
			cmdAliases.put(id, m);
		}else {
			cmdAliases.get(id).put(lang, aliases);
		}
	}
	/**
	 * [ES] Agrega una traducción de texto.<br>
	 * [EN] Adds a text translation.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param id <br>[ES] identificador del texto. [EN] text identifier.
	 * @param value <br>[ES] contenido del texto traducido. [EN] translated text content.
	 */
	public final void addStringValue(Language lang, String id, String value) {
		id=id.toLowerCase();
		if(values.get(id)==null) {
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, value);
			values.put(id, m);
		}else {
			values.get(id).put(lang, value);
		}
	}
	/**
	 * [ES] Agrega una traducción del nombre de una opción de un comando especificado.<br>
	 * [EN] Adds a translation for the name of an option of specified command.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param commandId <br>[ES] identificador del comando. [EN] command identifier.
	 * @param optionId <br>[ES] identificador de la opción. [EN] option identifier.
	 * @param translation <br>[ES] nombre traducido. [EN] translated name.
	 */
	@InDevelopment
	public final void addOptionNameTranslation(Language lang, String commandId, String optionId, String translation) {
		commandId=commandId.toLowerCase();
		optionId=optionId.toLowerCase();
		if(optNames.get(commandId)==null) {
			Map<String, Map<Language, String>> om = new HashMap<String, Map<Language, String>>();
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, translation);
			om.put(optionId, m);
			optNames.put(commandId, om);
		}else if(optNames.get(commandId).get(optionId)==null){
			Map<Language, String> m = new HashMap<Language, String>();
			m.put(lang, translation);
			optNames.get(commandId).put(optionId, m);
		}else {
			optNames.get(commandId).get(optionId).put(lang, translation);
		}
	}
	/**
	 * [ES] Agrega alias traducidos a una opción de un comando especificado.<br>
	 * [EN] Adds translated aliases for an option of specified command.
	 * @param lang <br>[ES] idioma de la tracucción. [EN] translation language.
	 * @param commandId <br>[ES] identificador del comando. [EN] command identifier.
	 * @param optionId <br>[ES] identificador de la opción. [EN] option identifier.
	 * @param aliases <br>[ES] alias traducidos. [EN] translated aliases.
	 */
	@InDevelopment
	public final void addOptionAliases(Language lang, String commandId, String optionId, String...aliases) {
		commandId=commandId.toLowerCase();
		optionId=optionId.toLowerCase();
		if(optAliases.get(commandId)==null) {
			Map<String, Map<Language, String[]>> om = new HashMap<String, Map<Language, String[]>>();
			Map<Language, String[]> m = new HashMap<Language, String[]>();
			m.put(lang, aliases);
			om.put(optionId, m);
			optAliases.put(commandId, om);
		}else if(optAliases.get(commandId).get(optionId)==null){
			Map<Language, String[]> m = new HashMap<Language, String[]>();
			m.put(lang, aliases);
			optAliases.get(commandId).put(optionId, m);
		}else {
			optAliases.get(commandId).get(optionId).put(lang, aliases);
		}
	}
	/**
	 * [ES] Devuelve las traducciones de textos.<br>
	 * [EN] Returns the translations of texts.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	protected final Map<String, Map<Language, String>> getValues() {
		return values;
	}
	/**
	 * [ES] Devuelve las traducciones de nombres de categorias.<br>
	 * [EN] Returns the translations of names of categories.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	protected final Map<String, Map<Language, String>> getCategoryNames() {
		return catNames;
	}
	/**
	 * [ES] Devuelve las traducciones de descripciones de categorias.<br>
	 * [EN] Returns the translations of descriptions of categories.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	protected final Map<String, Map<Language, String>> getCategoryDesc() {
		return catDesc;
	}
	/**
	 * [ES] Devuelve las traducciones de nombres de comandos.<br>
	 * [EN] Returns the translations of names of commands.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	protected final Map<String, Map<Language, String>> getCommandNames() {
		return cmdNames;
	}
	/**
	 * [ES] Devuelve las traducciones de descripciones de comandos.<br>
	 * [EN] Returns the translations of descriptions of commands.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	protected final Map<String, Map<Language, String>> getCommandDesc() {
		return cmdDesc;
	}
	/**
	 * [ES] Devuelve las traducciones de alias de comandos.<br>
	 * [EN] Returns the translations of aliases of commands.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	protected final Map<String, Map<Language, String[]>> getCommandAliases() {
		return cmdAliases;
	}
	/**
	 * [ES] Devuelve las traducciones de nombres de opciones.<br>
	 * [EN] Returns the translations of names of options.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	@InDevelopment
	protected final Map<String, Map<String, Map<Language, String>>> getOptionNames() {
		return optNames;
	}
	/**
	 * [ES] Devuelve las traducciones de alias de opciones.<br>
	 * [EN] Returns the translations of aliases of options.
	 * @return [ES] mapa de traducciones.<br>[EN] map of translations.
	 */
	@InDevelopment
	protected final Map<String, Map<String, Map<Language, String[]>>> getOptionAliases() {
		return optAliases;
	}
}
