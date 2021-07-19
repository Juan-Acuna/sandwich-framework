package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
/**
 * Indica que la clase es una Categoría, la cual será convertida en un objeto {@link xyz.sandwichframework.models.ModelCategory} por el framework.
 * Indicates that the class is a Category, which will be turned into a {@link xyz.sandwichframework.models.ModelCategory} object by the framework.
 * 
 * @author Juan Acuña
 * @version 1.1
 */
public @interface Category {
	/**
	 * Identificador de la categoría. Tambien es el nombre en el idioma por defecto indicado. Si no esta presente, se usa el nombre de la clase.
	 * Identifier of the category. Also it is the name in the default language indicated. If it is not present, it uses the class name.
	 */
	String id() default "";
	/**
	 * Descripción de la categoría.
	 * Description of the category.
	 */
	String desc() default "";
	/**
	 * Indica si la categoría es NSFW.
	 * Indicates if the category is NSFW.
	 */
	boolean nsfw() default false;
	/**
	 * La categoría es visible para los comandos de ayuda.
	 * The category is visible for the help commands.
	 */
	boolean visible() default true;
	/**
	 * Los comandos de esta categoría pueden ser ejecutados.
	 * The commands of this category can be executed.
	 */
	boolean enabled() default true;
	/**
	 * Indica si esta es una categoría especial.
	 * Indicates if this is a special category.
	 */
	boolean isSpecial() default false;
}
