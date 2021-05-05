package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import xyz.sandwichframework.core.util.Language;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Indica que la clase se usa para configurar comandos, categorias, opciones y/o parametros segun el idioma especificado.
 * Indicates that the class is for configuration of commands, categories, options and/or parameters by the especified language.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Configuration {
	Language value();
}
