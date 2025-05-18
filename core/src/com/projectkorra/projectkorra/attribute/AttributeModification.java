package com.projectkorra.projectkorra.attribute;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.event.AbilityRecalculateAttributeEvent;
import org.bukkit.NamespacedKey;

/**
 * A class to modify attributes in the {@link AbilityRecalculateAttributeEvent} event.
 * <br><br>
 * To create a new instance, use {@link #of(AttributeModifier, Number, int, NamespacedKey)},
 * {@link #setter(Boolean, int, NamespacedKey)} or
 * {@link #setter(Number, int, NamespacedKey)}.
 */
public class AttributeModification {

    public static final NamespacedKey DAY_FACTOR = new NamespacedKey(ProjectKorra.plugin, "DAY_FACTOR");
    public static final NamespacedKey NIGHT_FACTOR = new NamespacedKey(ProjectKorra.plugin, "NIGHT_FACTOR");
    public static final NamespacedKey FULL_MOON_FACTOR = new NamespacedKey(ProjectKorra.plugin, "FULL_MOON"); //For RPG
    public static final NamespacedKey SOZINS_COMET_FACTOR = new NamespacedKey(ProjectKorra.plugin, "SOZINS_COMET"); //For RPG
    public static final NamespacedKey AVATAR_STATE_FACTOR = new NamespacedKey(ProjectKorra.plugin, "AVATAR_STATE");
    public static final NamespacedKey BLUE_FIRE_DAMAGE = new NamespacedKey(ProjectKorra.plugin, "BLUE_FIRE_DAMAGE");
    public static final NamespacedKey BLUE_FIRE_RANGE = new NamespacedKey(ProjectKorra.plugin, "BLUE_FIRE_RANGE");
    public static final NamespacedKey BLUE_FIRE_COOLDOWN = new NamespacedKey(ProjectKorra.plugin, "BLUE_FIRE_COOLDOWN");

    public static final int PRIORITY_LOW = 1000;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_HIGH = -1000;

    private final Object modification;
    private final int priority;
    private final AttributeModifier modifier;
    private final NamespacedKey modificationName;

    private AttributeModification(final AttributeModifier modifier, final Object modification, final int priority, final NamespacedKey modificationName) {
        this.modification = modification;
        this.priority = priority;
        this.modifier = modifier;
        this.modificationName = modificationName;
    }

    /**
     * Create a new AttributeModification instance. This can be applied to any ability in the
     * {@link AbilityRecalculateAttributeEvent} event to modify the value of an attribute.
     * @param modifier The type of modification to apply
     * @param modification The value to apply
     * @param priority The priority of this modification. Lower values are applied first
     * @param modificationName A unique identifier for this modification
     * @return A new AttributeModification instance
     */
    public static AttributeModification of(final AttributeModifier modifier, final Number modification, final int priority, final NamespacedKey modificationName) {
        return new AttributeModification(modifier, modification, priority, modificationName);
    }

    /**
     * Create a new AttributeModification instance with a normal priority. This can be applied to any ability in the
     * {@link AbilityRecalculateAttributeEvent} event to modify the value of an attribute.
     * @param modifier The type of modification to apply
     * @param modification The value to apply
     * @param modificationName A unique identifier for this modification
     * @return A new AttributeModification instance
     */
    public static AttributeModification of(final AttributeModifier modifier, final Number modification, final NamespacedKey modificationName) {
        return new AttributeModification(modifier, modification, PRIORITY_NORMAL, modificationName);
    }

    /**
     * Create a new AttributeModification that sets an attribute to a specific value. This can be applied to any ability in the
     * {@link AbilityRecalculateAttributeEvent} event.
     * @param value The value to set the attribute to
     * @param priority The priority of this modification. Lower values are applied first
     * @param modificationName A unique identifier for this modification
     * @return A new AttributeModification instance
     */
    public static AttributeModification setter(final Boolean value, final int priority, final NamespacedKey modificationName) {
        return new AttributeModification(AttributeModifier.SET, value, priority, modificationName);
    }

    /**
     * Create a new AttributeModification that sets an attribute to a specific value. This can be applied to any ability in the
     * {@link AbilityRecalculateAttributeEvent} event.
     * @param value The value to set the attribute to
     * @param priority The priority of this modification. Lower values are applied first
     * @param modificationName A unique identifier for this modification
     * @return A new AttributeModification instance
     */
    public static AttributeModification setter(final Number value, final int priority, final NamespacedKey modificationName) {
        return new AttributeModification(AttributeModifier.SET, value, priority, modificationName);
    }

    public int getPriority() {
        return priority;
    }

    public AttributeModifier getModifier() {
        return modifier;
    }

    /**
     * Get the modification object. This can be a number, boolean, or any other object
     * @return The modification value
     */
    public Object getModification() {
        return modification;
    }

    /**
     * Get the unique identifier for this modification
     * @return The modification name
     */
    public NamespacedKey getModificationName() {
        return modificationName;
    }
}
