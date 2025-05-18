package com.projectkorra.projectkorra.event;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.attribute.AttributeModification;
import com.projectkorra.projectkorra.attribute.markers.DayNightFactor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This event is called when an ability's attribute is recalculated.
 * This happens once on ability start, and then whenever
 * {@link CoreAbility#recalculateAttributes()} is called.
 * <br><br>
 * It allows for modifications to be added to the recalculation process,
 * such as day/night modifiers, AvatarState modifiers, and any
 * other modifiers that should be applied. These can be added with
 * {@link #addModification(AttributeModification)} in the event.
 * <br><br>
 * This event is called per each attribute in the ability. Therefore you can
 * test the attribute name to determine which attribute is being recalculated,
 * as well as what modifiers you should add.
 */
public class AbilityRecalculateAttributeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private CoreAbility ability;
    private String attribute;
    private Object originalValue;

    private Set<AttributeModification> modifications = new TreeSet<>(Comparator.comparingInt(AttributeModification::getPriority));

    public AbilityRecalculateAttributeEvent(final CoreAbility ability, final String attribute, final Object originalValue) {
        this.ability = ability;
        this.attribute = attribute;
        this.originalValue = originalValue;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets the original value of the attribute before any modifications were applied.
     * @return The original value of the attribute.
     */
    public Object getOriginalValue() {
        return originalValue;
    }

    /**
     * Gets a marker of the specified class from the attribute field. For more
     * information about markers, see {@link #hasMarker(Class)}.
     * @param markerClass The class of the marker
     * @return The marker of the specified class, or null if it does not exist.
     * @param <T> The type of the marker
     */
    @Nullable
    public <T extends Annotation> T getMarker(Class<T> markerClass) {
        return (T) CoreAbility.getAttributeCache(ability).get(attribute).getMarker(markerClass);
    }

    /**
     * Checks if the attribute has a marker of the specified class. Markers are annotations added to
     * the field an attribute applies to in order to mark it for specific attribute modifiers.
     * ProjectKorra has a single in built marker, {@link DayNightFactor}, which marks that this
     * attribute should be modified by the day/night cycle.
     * @param markerClass The class of the marker
     * @return True if the attribute has a marker of the specified class, false otherwise.
     */
    public boolean hasMarker(Class<? extends Annotation> markerClass) {
        return CoreAbility.getAttributeCache(ability).get(attribute).hasMarker(markerClass);
    }

    /**
     * Adds a modification to the recalculation process. Each modification needs a Namespace to name
     * the type of modification being applied, such as {@link AttributeModification#DAY_FACTOR}, a priority of the modifier,
     * and the type of modification. For more information, see {@link AttributeModification}.
     * @param modification The modification to add.
     */
    public void addModification(final AttributeModification modification) {
        modifications.add(modification);
    }

    /**
     * Gets the original value of the attribute and casts it to an int.
     * @return The original value of the attribute as an int.
     */
    public int getOriginalValueAsInt() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }

        return ((Number)originalValue).intValue();
    }

    /**
     * Gets the original value of the attribute and casts it to a float.
     * @return The original value of the attribute as a float.
     */
    public float getOriginalValueAsFloat() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }
        return ((Number)originalValue).floatValue();
    }

    /**
     * Gets the original value of the attribute and casts it to a double.
     * @return The original value of the attribute as a double.
     */
    public double getOriginalValueAsDouble() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }
        return ((Number)originalValue).doubleValue();
    }

    /**
     * Gets the original value of the attribute and casts it to a long.
     * @return The original value of the attribute as a long.
     */
    public long getOriginalValueAsLong() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }
        return ((Number)originalValue).longValue();
    }

    /**
     * Gets the original value of the attribute and casts it to a boolean.
     * @return The original value of the attribute as a boolean.
     */
    public boolean getOriginalValueAsBoolean() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue;
        }
        return ((Number)originalValue).intValue() % 2 == 1;
    }

    /**
     * Gets the ability that this event is for.
     * @return The ability that this event is for.
     */
    @NotNull
    public CoreAbility getAbility() {
        return ability;
    }

    /**
     * Gets the attribute that this event is for. See
     * {@link Attribute} for a list
     * of common attributes.
     * @return The attribute that this event is for.
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Gets the modifications that have been added to this event. This set is mutable and therefore can
     * be edited, including adding/removing existing modifications created by ProjectKorra or other plugins.
     * @return The modifications that have been added to this event.
     */
    public Set<AttributeModification> getModifications() {
        return modifications;
    }
}
