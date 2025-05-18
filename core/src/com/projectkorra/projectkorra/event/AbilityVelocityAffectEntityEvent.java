package com.projectkorra.projectkorra.event;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.ability.Ability;

/**
 * Cancellable event called when an ability would push or alter the velocity of
 * an entity.
 * 
 * The entity can be changed, vector can be modified, and the ability that
 * caused the change can be accessed.
 *
 * @author dNiym
 *
 */

public class AbilityVelocityAffectEntityEvent extends Event implements Cancellable {

    Entity affected;
    Vector velocity;
    Ability ability;
    boolean cancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public AbilityVelocityAffectEntityEvent(Ability ability, Entity entity, Vector vector) {
        this.affected = entity;
        this.ability = ability;
        this.velocity = vector;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the entity that was affected.
     *
     * @return The entity that was affected.
     */
    public Entity getAffected() {
        return affected;
    }

    public void setAffected(Entity affected) {
        this.affected = affected;
    }

    /**
     * Gets the velocity that was applied to the entity.
     *
     * @return The velocity that was applied to the entity.
     */
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity that was applied to the entity.
     *
     * @param velocity The new velocity to be applied to the entity.
     */
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the ability that caused the event.
     *
     * @return The ability that caused the event.
     */
    public Ability getAbility() {
        return ability;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
