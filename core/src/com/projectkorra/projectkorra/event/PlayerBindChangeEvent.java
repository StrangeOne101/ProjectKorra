package com.projectkorra.projectkorra.event;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.OfflineBendingPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event called when a player binds or unbinds an ability
 */
public class PlayerBindChangeEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean cancelled = false;
	
	private final OfflinePlayer player;
	private final String ability;
	private final int slot; 
	private final boolean isBinding, isMultiAbility; 

	public PlayerBindChangeEvent(OfflinePlayer player, String ability, int slot, boolean isBinding, boolean isMultiAbility) {
		this.player = player;
		this.ability = ability;
		this.slot = slot;
		this.isBinding = isBinding;
		this.isMultiAbility = isMultiAbility;
	}
	
	public PlayerBindChangeEvent(Player player, String ability, boolean isBinding, boolean isMultiAbility) {
		this(player, ability, player.getInventory().getHeldItemSlot(), isBinding, isMultiAbility);
	}

	/**
	 * Get the {@link OfflinePlayer} that was affected
	 * @return the {@link OfflinePlayer} that was affected
	 */
	public OfflinePlayer getPlayer() {
		return this.player;
	}

	/**
	 * Get the {@link OfflineBendingPlayer} that was affected. You can check
	 * if the player is online with {@link #isOnline()} and if it is true,
	 * it is safe to cast this to {@link BendingPlayer}
	 * @return the {@link OfflineBendingPlayer} that was affected
	 */
	public OfflineBendingPlayer getBendingPlayer() {
		return BendingPlayer.getBendingPlayer(this.player);
	}

	/**
	 * Get whether the player is online. If true, it is safe to cast the
	 * {@link OfflineBendingPlayer} to {@link BendingPlayer}
	 * @return true if the player is online
	 */
	public boolean isOnline() {
		return this.player.isOnline();
	}

	/**
	 * Get the ability being bound or unbound
	 * @return affected ability
	 */
	public String getAbility() {
		return this.ability;
	}

	/**
	 * Get the slot being changed. Binding normal abilities will return the slot that it is being bound to / unbound from
	 * <ul>
	 * <li>In the case of binding a multiability, returns 0
	 * <li>In the case of unbinding a multiability, returns the original slot
	 * </ul>
	 * @return affected slot
	 */
	public int getSlot() {
		return this.slot;
	}

	/**
	 * Get whether this event is binding or unbinding an ability
	 * @return true if binding, false if unbinding
	 */
	public boolean isBinding() {
		return this.isBinding;
	}

	/**
	 * Get whether this event was caused by a multiability or not
	 * @return true if caused by multiability
	 */
	public boolean isMultiAbility() {
		return this.isMultiAbility;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
