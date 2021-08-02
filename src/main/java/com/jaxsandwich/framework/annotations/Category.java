package com.jaxsandwich.framework.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
/**
 * [ES] Indica que la clase es una Categoría, la cual será convertida en un objeto {@link com.jaxsandwich.framework.models.ModelCategory} por el framework.<br>
 * [EN] Indicates that the class is a Category, which will be turned into a {@link com.jaxsandwich.framework.models.ModelCategory} object by the framework.
 * @author Juan Acuña
 * @version 1.2
 */
public @interface Category {
	/**
	 * [ES] Identificador de la categoría. Tambien es el nombre en el idioma por defecto indicado. Si no esta presente, se usa el nombre de la clase.<br>
	 * [EN] Identifier of the category. Also it is the name in the default language indicated. If it is not present, it uses the class name.
	 */
	String id() default "";
	/**
	 * [ES] Descripción de la categoría.<br>
	 * [EN] Description of the category.
	 */
	String desc() default "";
	/**
	 * [ES] Indica si la categoría es NSFW.<br>
	 * [EN] Indicates if the category is NSFW.
	 */
	boolean nsfw() default false;
	/**
	 * [ES] La categoría es visible para los comandos de ayuda.<br>
	 * [EN] The category is visible for the help commands.
	 */
	boolean visible() default true;
	/**
	 * [ES] Los comandos de esta categoría pueden ser ejecutados.<br>
	 * [EN] The commands of this category can be executed.
	 */
	boolean enabled() default true;
	/**
	 * [ES] Indica si esta es una categoría especial.<br>
	 * [EN] Indicates if this is a special category.
	 */
	boolean isSpecial() default false;
}
