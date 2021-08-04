package com.jaxsandwich.sandwichcord.annotations.configure;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * [ES] Indica que el campo es la descripción del parametro.<br>
 * [EN] Indicates that the field is the description of the parameter.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere que el campo contenga una anotacion de tipo {@link CommandID} o {@link MultiCommandIDParameter}.<br>
 * [EN] Requires the field to have an annotation of type {@link CommandID} or {@link MultiCommandIDParameter}.
 */
public @interface ParameterDescription {

}
