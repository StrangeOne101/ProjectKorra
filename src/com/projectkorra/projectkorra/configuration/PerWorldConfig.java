package com.projectkorra.projectkorra.configuration;

import com.projectkorra.projectkorra.ProjectKorra;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PerWorldConfig extends Config {

    private static Map<String, ConfigurationSection> worldConfigs = new HashMap<>();

    /**
     * Creates a new {@link Config} with the file being the configuration file.
     *
     * @param file The file to create/load
     */
    public PerWorldConfig(File file) {
        super(file);
    }

    /**
     * Loads all PerWorldConfigs from the config
     */
    public void loadWorldConfigs() {
        worldConfigs.clear();

        ConfigurationSection section = get().getConfigurationSection("PerWorldConfig");
        if (section != null) {
            for (String world : section.getKeys(false)) {
                World worldObject = Bukkit.getWorld(world);
                if (worldObject == null) {
                    ProjectKorra.log.severe("Could not find world " + world + " for per world config settings. Skipping...");
                    continue;
                }

                Object config = get().get("PerWorldConfig." + world);
                if (config instanceof ConfigurationSection) {
                    //We clone the config completely, so if an addon happens to get a configuration section
                    //and list the keys, it has ALL keys and not just the ones specific for this world
                    ConfigurationSection clonedConfig = clone(get());
                    ConfigurationSection newSection = merge(clonedConfig, (ConfigurationSection) config);
                    worldConfigs.put(world.toLowerCase(), newSection);
                }
            }
        }
    }

    /**
     * Clones a {@link ConfigurationSection} and returns a new copy of it
     * @param section The section to clone
     * @return A new copy of the section
     */
    protected ConfigurationSection clone(ConfigurationSection section) {
        return merge(new YamlConfiguration(), section);
    }

    /**
     * Merges two {@link ConfigurationSection}s together.
     * @param base The main section that the other section will be merged into
     * @param other The section to merge into the base section
     * @return The base section
     */
    protected ConfigurationSection merge(ConfigurationSection base, ConfigurationSection other) {
        for (String key : other.getKeys(true)) {
            if (key.toLowerCase().startsWith("perworldconfig")) continue;

            Object object = other.get(key);
            if (object instanceof ConfigurationSection) continue; //Skip sections; we are only merging values as sections will delete existing things

            base.set(key, object);
        }
        return base;
    }


    /**
     * Gets the config for the specified world.
     * @param world The world to get the config for.
     * @return The config for the specified world.
     */
    public ConfigurationSection get(String world) {
        if (worldConfigs.containsKey(world.toLowerCase())) {
            return worldConfigs.get(world.toLowerCase());
        }
        return get();
    }

    /**
     * Gets the config for the specified world.
     * @param world The world to get the config for.
     * @return The config for the specified world.
     */
    public ConfigurationSection get(World world) {
        return get(world.getName());
    }
}
