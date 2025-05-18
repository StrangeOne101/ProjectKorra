package com.projectkorra.projectkorra.particles;

import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public class ItemParticleBuilder extends ParticleBuilder<ItemParticleBuilder> {

    public ItemParticleBuilder() {
        super();
        this.type = Particle.valueOf("ITEM_CRACK") != null ? Particle.valueOf("ITEM_CRACK") : Particle.valueOf("ITEM"); //ITEM_CRACK is used up till 1.20.4
        this.data = new ItemStack(org.bukkit.Material.STONE);
    }

    /**
     * Sets the item to be used for the particle.
     * @param item The item stack
     * @return The builder
     */
    public ItemParticleBuilder item(ItemStack item) {
        this.data = item;
        return this;
    }
}
