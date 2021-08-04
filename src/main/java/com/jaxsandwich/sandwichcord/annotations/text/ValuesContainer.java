package com.jaxsandwich.sandwichcord.annotations.text;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jaxsandwich.sandwichcord.core.util.Language;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * [ES] Indica que la clase contiene textos para un determinado idioma.(ex Values)<br>
 * [EN] Indicates that the class contains text for a determined language.(ex Values)
 * @author Juan Acuña
 * @version 1.2
 */
public @interface ValuesContainer {
	Language value();
}
