package com.projectkorra.projectkorra.particles;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

/**
 * A builder for creating particles that require a block type to spawn, such as
 * {@link Particle#BLOCK_DUST} or {@link Particle#FALLING_DUST}.
 */
public class BlockParticleBuilder extends ParticleBuilder<BlockParticleBuilder> {

    public BlockParticleBuilder() {
        super();
        this.type = Particle.BLOCK_MARKER;
        this.data = Material.STONE.createBlockData();
    }

    /**
     * Sets the type of particle to spawn. Can be {@link Particle#BLOCK_DUST}, {@link Particle#FALLING_DUST}, or any particle
     * that requires {@link BlockData} to spawn.
     * @param type The type of particle
     * @return The builder
     */
    public BlockParticleBuilder type(@NotNull Particle type) {
        if (!type.getDataType().equals(BlockData.class)) throw new IllegalArgumentException("Particle type must be of BlockData type. Got: " + type.getDataType().getSimpleName() + " instead.");
        this.type = type;
        return this;
    }

    /**
     * Sets the block data of the particle. If not set, {@link Material#STONE} will be used.
     * @param blockData The block data
     * @return The builder
     */
    public BlockParticleBuilder block(@NotNull BlockData blockData) {
        this.data = blockData;
        return this;
    }

    /**
     * Sets the block data of the particle. If not set, {@link Material#STONE} will be used.
     * @param material The material
     * @return The builder
     */
    public BlockParticleBuilder block(@NotNull Material material) {
        this.data = material.createBlockData();
        return this;
    }

    /**
     * Gets the block data of the particle.
     * @return The block data
     */
    public BlockData getBlockData() {
        return (BlockData) this.data;
    }

}
