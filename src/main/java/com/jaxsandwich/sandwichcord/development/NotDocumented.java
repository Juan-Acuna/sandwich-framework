package com.jaxsandwich.sandwichcord.development;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
/**
 * [ES] Anotación de uso exclusivo durante desarrollo. Indica que el componente no está documentado aún.<br>
 * [EN] Development exclusive use annotation. Indicates this component is not documented yet.
 * @author Juan Acuña
 * @version 1.0
 */
public @interface NotDocumented {

}
