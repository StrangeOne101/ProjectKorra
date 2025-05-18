package com.projectkorra.projectkorra.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * An event that is called when a player swings their arm. This event is a combination of the
 * {@link PlayerInteractEvent} and the {@link PlayerAnimationEvent}, after ProjectKorra has
 * checked for reasons you wouldn't want to fire an event on. This is useful for addon
 * abilities so they don't fire on things like "The player opening a crafting table".
 @author StrangeOne101
 */
public class PlayerSwingEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;
    private Player player;

    public PlayerSwingEvent(Player player) {
        this.player = player;
    }

    /**
     * Get the {@link Player} that swung their arm
     * @return the {@link Player} that was affected
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get whether the event is cancelled. If this is set to true, abilities
     * that fire on this event will not be able to fire.
     * @return true if the event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Set whether the event is cancelled. If this is set to true, abilities
     * that fire on this event will not be able to fire.
     * @param b true if the event should be cancelled
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
