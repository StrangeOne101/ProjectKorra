package com.projectkorra.projectkorra.event;

import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.projectkorra.ability.Ability;

/**
 * Called when an ability is removed via the {@link CoreAbility#remove()} method.
 */
public class AbilityEndEvent extends Event {
	private static final HandlerList HANDLERS = new HandlerList();

	Ability ability;

	public AbilityEndEvent(final Ability ability) {
		this.ability = ability;
	}

	/**
	 * Gets the ability that was removed.
	 * @return The ability that was removed.
	 */
	public Ability getAbility() {
		return this.ability;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
