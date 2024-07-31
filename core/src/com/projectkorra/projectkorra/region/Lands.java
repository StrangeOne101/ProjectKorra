package com.projectkorra.projectkorra.region;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.type.NaturalFlag;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lands extends RegionProtectionBase {

    private static LandsIntegration landsIntegration;
    private static NaturalFlag bendingFlag;

    public Lands() {
        super("Lands");
    }

    @Override
    public boolean isRegionProtectedReal(Player player, Location location, CoreAbility ability, boolean igniteAbility, boolean explosiveAbility) {
        final Area area = landsIntegration.getArea(location);
        final boolean isClaimed = area != null;

        if (isClaimed) {
            return !area.hasNaturalFlag(bendingFlag);
        }
        return false;
    }

    public static void createBendingFlag(ProjectKorra pk) {
        //ISkullProvider provider = new ModernSkullProvider();
        //ItemStack aangSkull = provider.getSkull("http://textures.minecraft.net/texture/a6a9910be2e94179cc8d057ee58e0c6931d1793e8996edce29f04034d5d8eeb2");

        landsIntegration = LandsIntegration.of(pk);
        bendingFlag = NaturalFlag.of(landsIntegration, FlagTarget.PLAYER,"bending");

        bendingFlag.setDisplayName("Bending")
                .setIcon(new ItemStack(Material.ANDESITE))
                .setDescription("Enable or Disable bending on areas!")
                .setDefaultState(false)
                .setAlwaysAllowInWilderness(true)
                .setActiveInWar(true)
                .setApplyInSubareas(true);
    }
}
