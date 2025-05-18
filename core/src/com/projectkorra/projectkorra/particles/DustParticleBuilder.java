package com.projectkorra.projectkorra.particles;

import org.bukkit.Particle;

/**
 * A builder for creating redstone dust particles with customizable properties.
 * Works for both {@link Particle#REDSTONE}.
 */
public class DustParticleBuilder extends ParticleBuilder<DustParticleBuilder> {

    private float size = 1;
    private int color = 0xFFFFFF; // Default to white color
    private int color2 = -1;

    public DustParticleBuilder() {
        super();
        this.type = Particle.valueOf("REDSTONE") != null ? Particle.valueOf("REDSTONE") : Particle.valueOf("DUST");
        this.data = new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 255, 255), 1);
    }

    /**
     * Set the size of the dust particle.
     * @param size The size of the dust particle.
     * @return The builder.
     */
    public DustParticleBuilder size(float size) {
        this.size = size;
        if (this.type != Particle.DUST_COLOR_TRANSITION) this.data = new Particle.DustOptions(org.bukkit.Color.fromRGB(color), size);
        else this.data = new Particle.DustTransition(org.bukkit.Color.fromRGB(color), org.bukkit.Color.fromRGB(color2), size);
        return this;
    }

    /**
     * Get the size of the dust particle.
     * @return The size of the dust particle.
     */
    public float getSize() {
        return this.size;
    }

    /**
     * Set the color of the dust particle.
     * @param color The color of the dust particle.
     * @return The builder.
     */
    public DustParticleBuilder color(int color) {
        this.color = color;
        this.data = new Particle.DustOptions(org.bukkit.Color.fromRGB(color), size);
        return this;
    }

    /**
     * Get the color of the dust particle.
     * @return The color of the dust particle.
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Get the first color of the dust particle. This is used for color transition
     * particles.
     * @return The second color of the dust particle.
     */
    public int getFromColor() {
        return this.color;
    }

    /**
     * Get the second color of the dust particle. This is used for color transition
     * particles.
     * @return The second color of the dust particle.
     */
    public int getToColor() {
        return this.color2;
    }

    /**
     * Set the color of the dust particle.
     * @param color The color of the dust particle.
     * @return The builder.
     */
    public DustParticleBuilder color(org.bukkit.Color color) {
        this.color = color.asRGB();
        this.data = new Particle.DustOptions(color, size);
        return this;
    }

    /**
     * Set the color of the dust particle.
     * @param red The red component of the color (0-1).
     * @param green The green component of the color (0-1).
     * @param blue The blue component of the color (0-1).
     * @return The builder.
     */
    public DustParticleBuilder color(float red, float green, float blue) {
        this.color = org.bukkit.Color.fromRGB((int) (red * 255), (int) (green * 255), (int) (blue * 255)).asRGB();
        this.data = new Particle.DustOptions(org.bukkit.Color.fromRGB(color), size);
        return this;
    }

    /**
     * Set the colors for a dust transition particle.
     * @param fromColor The starting color of the transition.
     * @param toColor The ending color of the transition.
     * @return The builder.
     */
    public DustParticleBuilder colorTransition(int fromColor, int toColor) {
        this.color = fromColor;
        this.color2 = toColor;
        this.type = Particle.DUST_COLOR_TRANSITION;
        this.data = new Particle.DustTransition(org.bukkit.Color.fromRGB(fromColor), org.bukkit.Color.fromRGB(toColor), size);
        return this;
    }

    /**
     * Set the colors for a dust transition particle.
     * @param fromColor The starting color of the transition.
     * @param toColor The ending color of the transition.
     * @return The builder.
     */
    public DustParticleBuilder colorTransition(org.bukkit.Color fromColor, org.bukkit.Color toColor) {
        this.color = fromColor.asRGB();
        this.color2 = toColor.asRGB();
        this.type = Particle.DUST_COLOR_TRANSITION;
        this.data = new Particle.DustTransition(fromColor, toColor, size);
        return this;
    }
}
