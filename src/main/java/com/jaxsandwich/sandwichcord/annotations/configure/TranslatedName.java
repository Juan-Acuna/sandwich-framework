package com.jaxsandwich.sandwichcord.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Nombre traducido en el idioma configurado para el elemento actual.<br>
 * [EN] Translated name to the configured language for the actual element.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere que el campo contenga una anotacion de tipo {@link CategoryID}, {@link CommandID} o {@link OptionID}.<br>
 * [EN] Requires the field to have an annotation of type {@link CategoryID}, {@link CommandID} o {@link OptionID}.
 */
public @interface TranslatedName {
	String value();
}
