package com.projectkorra.projectkorra.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.projectkorra.ability.Ability;

/**
 * Called when an ability calls {@link Ability#progress()}. While
 * this ability cannot be cancelled, you can call {@link Ability#remove()} to
 * remove the ability from the player.
 *
 * @author Philip
 *
 */
public class AbilityProgressEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	Ability ability;

	public AbilityProgressEvent(final Ability ability) {
		this.ability = ability;
	}

	public Ability getAbility() {
		return this.ability;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
