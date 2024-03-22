package com.projectkorra.projectkorra.attribute.markers;

import com.projectkorra.projectkorra.attribute.AttributeMarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This marker is used to indicate that the attribute is affected by the AvatarState.
 * Rather than be multiplied by a factor, the attribute has a separate value in the config for its
 * value when the AvatarState is used.
 */
@AttributeMarker
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface AvatarStateForeignValue {
}
