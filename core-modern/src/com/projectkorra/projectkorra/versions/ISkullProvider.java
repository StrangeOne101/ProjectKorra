package com.projectkorra.projectkorra.versions;

import org.bukkit.inventory.ItemStack;

public interface ISkullProvider {
	/**
	 * Interface created to make the Lands plugin bending flag
	 * use an Aang skin as Flag toggle icon.
	 * @param skin
	 * @return Skull ItemStack
	 */
	ItemStack getSkull(String skin);
}
