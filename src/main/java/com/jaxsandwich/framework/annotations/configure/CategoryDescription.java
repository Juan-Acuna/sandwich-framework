package com.jaxsandwich.framework.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Indica que el campo es la descripción de la categoría.<br>
 * [EN] Indicates that the field is the description of the category.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere que el campo contenga una anotacion del tipo {@link CategoryID}<br>
 * [EN] Requires the field to have an annotation of type {@link CategoryID}
 */
public @interface CategoryDescription {

}
