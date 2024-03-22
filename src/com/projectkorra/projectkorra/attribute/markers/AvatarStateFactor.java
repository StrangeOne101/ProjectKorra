package com.projectkorra.projectkorra.attribute.markers;

import com.projectkorra.projectkorra.attribute.AttributeMarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This marker is used to indicate that the attribute is affected by the AvatarState.
 * This marker will allow the attribute to be multiplied by a factor when the AvatarState is used
 */
@AttributeMarker
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface AvatarStateFactor {

    /**
     * The factor to affect this ability. Default: -1
     * When the factor is -1, the ability will use the default value from the config
     * @return The avatar state factor to multiply the attribute by
     */
    float factor() default -1F;

    /**
     * Whether the factor should be inverted. Inverted factors will be divided by instead.
     * This is useful for things like cooldowns, where a lower value is stronger
     * @return Whether the factor should be inverted
     */
    boolean invert() default false;

}
