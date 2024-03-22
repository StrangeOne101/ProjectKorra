package com.projectkorra.projectkorra.attribute;

public class AttributeModification {

    public static final String DAY_FACTOR = "PROJECTKORRA_DAY_FACTOR";
    public static final String NIGHT_FACTOR = "PROJECTKORRA_NIGHT_FACTOR";
    public static final String FULL_MOON_FACTOR = "PROJECTKORRA_FULL_MOON"; //For RPG
    public static final String SOZINS_COMET_FACTOR = "PROJECTKORRA_SOZINS_COMET"; //For RPG
    public static final String AVATAR_STATE_FACTOR = "PROJECTKORRA_AVATAR_STATE";

    public static final int PRIORITY_LOW = 1000;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_HIGH = -1000;


    private final Object modification;
    private final int priority;
    private final AttributeModifier modifier;
    private final String modificationName;

    private AttributeModification(final AttributeModifier modifier, final Object modification, final int priority, final String modificationName) {
        this.modification = modification;
        this.priority = priority;
        this.modifier = modifier;
        this.modificationName = modificationName;
    }

    public static AttributeModification of(final AttributeModifier modifier, final Number modification, final int priority, final String modificationName) {
        return new AttributeModification(modifier, modification, priority, modificationName);
    }

    public static AttributeModification setter(final Boolean value, final int priority, final String modificationName) {
        return new AttributeModification(AttributeModifier.SET, value, priority, modificationName);
    }

    public static AttributeModification setter(final Number value, final int priority, final String modificationName) {
        return new AttributeModification(AttributeModifier.SET, value, priority, modificationName);
    }

    public int getPriority() {
        return priority;
    }

    public AttributeModifier getModifier() {
        return modifier;
    }

    public Object getModification() {
        return modification;
    }

    public String getModificationName() {
        return modificationName;
    }
}
