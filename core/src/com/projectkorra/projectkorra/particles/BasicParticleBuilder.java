package com.projectkorra.projectkorra.particles;

import org.bukkit.Particle;

public class BasicParticleBuilder extends ParticleBuilder<BasicParticleBuilder> {

    /**
     * Sets the type of particle to spawn.
     * @param type The type
     * @return The builder
     */
    public BasicParticleBuilder type(Particle type) {
        this.type = type;
        return this;
    }

    /**
     * Gets the type of particle to spawn.
     * @return The type
     */
    public Particle type() {
        return this.type;
    }
}
