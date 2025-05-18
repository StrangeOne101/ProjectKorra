package com.projectkorra.projectkorra.event;

import com.projectkorra.projectkorra.ability.util.CollisionManager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.projectkorra.ability.util.Collision;

/**
 * This event is called when a collision occurs between two abilities. Specifically, a collisions
 * registered in {@link CollisionManager} and not a collision between an ability and blocks created
 * by this ability.
 */
public class AbilityCollisionEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private boolean cancelled;
	private Collision collision;

	public AbilityCollisionEvent(final Collision collision) {
		this.collision = collision;
		this.cancelled = false;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * Gets the collision that occurred.
	 *
	 * @return The collision that occurred.
	 */
	public Collision getCollision() {
		return this.collision;
	}

	/**
	 * Override the collision that occurred.
	 *
	 * @param collision The collision that occurred.
	 */
	public void setCollision(final Collision collision) {
		this.collision = collision;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
