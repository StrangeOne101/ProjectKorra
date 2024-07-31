package com.projectkorra.projectkorra.versions.modern;

import com.projectkorra.projectkorra.versions.ISkullProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ModernSkullProvider implements ISkullProvider {
	@Override
	public ItemStack getSkull(String skin) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		if (skin == null || skin.isEmpty())
			return skull;

		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		// GameProfile profile = new GameProfile(UUID.randomUUID(), null);

		return null;
	}


}
