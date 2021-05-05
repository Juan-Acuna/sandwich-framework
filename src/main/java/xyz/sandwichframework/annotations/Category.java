package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
/**
 * Indica que la clase es una Categoría.
 * Indicates that the class is a Category.
 * 
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Category {
	/**
	 * Identificador de la categoría. Tambien es el nombre en el idioma por defecto indicado. Si no esta presente, se usa el nombre de la clase.
	 * Identifier of the category. Also it is the name in the default language indicated. If it is not present, it uses the class name.
	 */
	String name() default "NoID";
	String desc() default "NoDesc";
	boolean nsfw() default false;
	boolean visible() default true;
	boolean isSpecial() default false;
}
