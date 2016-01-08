package com.leetzilantonis.netherwater;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Main extends JavaPlugin {

	List<String> dWorlds = new ArrayList<String>();
	private WorldGuardPlugin wg;

	@Override
	public void onEnable() {

		FileConfiguration c = this.getConfig();
		if (c.contains("disabledWorlds")) {
			List<String> worlds = new ArrayList<String>();
			worlds.add("noWaterWorld");
			c.addDefault("disabledWorlds", worlds);
			c.options().copyDefaults(true);
		}
		this.saveConfig();

		dWorlds = c.getStringList("disabledWorlds");

		wg = this.getWorldGuard();

		if (wg == null) {

			this.getLogger().info("World Guard cannot be found! JUST INFO");

		}

		this.getServer().getPluginManager().registerEvents(new WaterPlaceListener(this), this);
		this.getCommand("nwreload").setExecutor(new NWReloadCommand(this));

	}

	@Override
	public void onDisable() {

		this.saveConfig();

	}

	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}
	
	public boolean canBuild(Player p, Block b) {
		return wg.canBuild(p, b);
	}

}
