package com.projectkorra.projectkorra.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class that simplifies creating and emitting particles. For most uses, use
 * <code>new {@link BasicParticleBuilder}()</code>, but there other classes for special
 * particles like redstone/dust particles
 * @param <T> Itself
 */
public abstract class ParticleBuilder<T extends ParticleBuilder> {

    protected static final Vector BLANK = new Vector(0, 0, 0);

    protected int amount = 1;
    protected Vector direction;
    protected double speed;
    protected Location location;
    protected Particle type = Particle.CLOUD;
    protected boolean forceVisibility = true;
    protected double xOffset;
    protected double yOffset;
    protected double zOffset;
    protected Set<Player> limitPlayers = new HashSet<>();
    protected Object data;

    /**
     * Set the amount of particles to spawn
     * @param amount The amount
     * @return The builder
     */
    public T amount(int amount) {
        this.amount = amount;
        return (T) this;
    }

    /**
     * Get the amount of particles to spawn
     * @return The amount
     */
    public int amount() {
        return this.amount;
    }

    /**
     * Set the direction of the particles. Note that not all particles support
     * direction. Some particles that do not support it: `DUST`, `HEART`
     * @param direction The direction
     * @return The builder
     */
    public T direction(@Nullable Vector direction) {
        this.direction = direction;
        return (T) this;
    }

    /**
     * Get the direction of the particles.
     * @return
     */
    @Nullable
    public Vector direction() {
        return this.direction;
    }

    /**
     * Set the speed of the particles. If velocity is unset, the direction is random
     * @param speed The speed
     * @return The builder
     */
    public T speed(double speed) {
        this.speed = speed;
        return (T) this;
    }

    /**
     * Get the speed of the particles.
     * @return The speed
     */
    public double speed() {
        return this.speed;
    }

    /**
     * Set the location of the particles
     * @param location The location
     * @return The builder
     */
    public T location(Location location) {
        this.location = location;
        return (T) this;
    }

    /**
     * Get the location of the particles
     * @return The location
     */
    public Location location() {
        return location;
    }

    /**
     * Get the type of particles to spawn.
     */
    public Particle type() {
        return type;
    }

    /**
     * Set the amount of offset on the X axis to spawn the particles on.
     * @param xOffset The offset
     * @return The builder
     */
    public T offsetX(double xOffset) {
        this.xOffset = xOffset;
        return (T) this;
    }

    /**
     * Get the amount of offset on the X axis to spawn the particles on.
     * @return The offset
     */
    public double offsetX() {
        return this.xOffset;
    }

    /**
     * Set the amount of offset on the Y axis to spawn the particles on.
     * @param yOffset The offset
     * @return The builder
     */
    public T offsetY(double yOffset) {
        this.yOffset = yOffset;
        return (T) this;
    }

    /**
     * Get the amount of offset on the Y axis to spawn the particles on.
     * @return The offset
     */
    public double offsetY() {
        return this.yOffset;
    }

    /**
     * Set the amount of offset on the Z axis to spawn the particles on.
     * @param zOffset The offset
     * @return The builder
     */
    public T offsetZ(double zOffset) {
        this.zOffset = zOffset;
        return (T) this;
    }

    /**
     * Get the amount of offset on the Z axis to spawn the particles on.
     * @return The offset
     */
    public double offsetZ() {
        return this.zOffset;
    }

    /**
     * Set the amount of offset on the X, Y and Z axis to spawn the particles on.
     * @param xOffset The X offset
     * @param yOffset The Y offset
     * @param zOffset The Z offset
     * @return The builder
     */
    public T offset(double xOffset, double yOffset, double zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        return (T) this;
    }

    /**
     * Set the amount of offset on the X, Y and Z axis to spawn the particles on.
     * @param xyzOffset The offset
     * @return The builder
     */
    public T offset(double xyzOffset) {
        this.xOffset = xyzOffset;
        this.yOffset = xyzOffset;
        this.zOffset = xyzOffset;
        return (T) this;
    }

    /**
     * Set what players should see this particle. If this is not set, all
     * players will see the particle.
     * @param players The players
     * @return The builder
     */
    public T receivers(@NotNull Set<Player> players) {
        this.limitPlayers = players;
        return (T) this;
    }

    /**
     * Set what players should see this particle. If this is not set, all
     * players will see the particle.
     * @param players The players
     * @return The builder
     */
    public T receivers(Player... players) {
        this.limitPlayers = new HashSet<>();
        for (Player player : players) {
            this.limitPlayers.add(player);
        }
        return (T) this;
    }

    /**
     * Get all the players that should see this particle. If this is empty, all players
     * will see the particle.
     * @return The receivers
     */
    @NotNull
    public Set<Player> receivers() {
        return limitPlayers;
    }

    /**
     * Set whether this particle should be forcefully shown to players. If set to false,
     * players will only see the particle within 32 blocks of the player.
     * @param forceVisibility Whether to force visibility
     * @return The builder
     */
    public T forceVisibility(boolean forceVisibility) {
        this.forceVisibility = forceVisibility;
        return (T) this;
    }

    /**
     * Get whether this particle should be forcefully shown to players. If false,
     * players will only see the particle within 32 blocks of the player.
     * @return Whether to force visibility
     */
    public boolean forceVisibility() {
        return forceVisibility;
    }

    /**
     * Spawn this particle at the current location.
     */
    public void emit() {
        if (this.location == null) {
            throw new IllegalStateException("Location cannot be null");
        }
        if (this.type == null) {
            throw new IllegalStateException("Particle type cannot be null");
        }

        //If it is meant to go to all players
        if (this.limitPlayers.isEmpty()) {
            if (this.direction != null && !this.direction.equals(BLANK)) {
                for (int i = 0; i < this.amount; i++) {
                    Location location = this.location.clone();
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    double xOffset = random.nextDouble(-this.xOffset, this.xOffset);
                    double yOffset = random.nextDouble(-this.yOffset, this.yOffset);
                    double zOffset = random.nextDouble(-this.zOffset, this.zOffset);
                    location.add(xOffset, yOffset, zOffset);
                    this.location.getWorld().spawnParticle(this.type, location, 0, this.direction.getX(), this.direction.getY(), this.direction.getZ(), this.speed, this.data, this.forceVisibility);
                }
            } else {
                location.getWorld().spawnParticle(this.type, this.location, this.amount, this.xOffset, this.yOffset, this.zOffset, this.speed, this.data, this.forceVisibility);
            }
        } else { //If it is meant to be limited
            for (Player player : limitPlayers) {
                if (this.direction != null && !this.direction.equals(BLANK)) {
                    for (int i = 0; i < amount; i++) {
                        Location location = this.location.clone();
                        ThreadLocalRandom random = ThreadLocalRandom.current();
                        double xOffset = random.nextDouble(-this.xOffset, this.xOffset);
                        double yOffset = random.nextDouble(-this.yOffset, this.yOffset);
                        double zOffset = random.nextDouble(-this.zOffset, this.zOffset);
                        location.add(xOffset, yOffset, zOffset);
                        player.getWorld().spawnParticle(this.type, location, 0, this.direction.getX(), this.direction.getY(), this.direction.getZ(), this.speed, this.data, this.forceVisibility);
                    }
                } else {
                    player.getWorld().spawnParticle(this.type, this.location, this.amount, this.xOffset, this.yOffset, this.zOffset, this.speed, this.data, this.forceVisibility);
                }
            }
        }
    }
}
