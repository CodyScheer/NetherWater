package com.leetzilantonis.netherwater;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Main extends JavaPlugin {

	private List<String> dWorlds = new ArrayList<String>();
	private WorldGuardPlugin wg;

	@Override
	public void onEnable() {
		
		if(!getDataFolder().exists()) getDataFolder().mkdir();
        if(!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();

		dWorlds = this.getConfig().getStringList("disabledWorlds");

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
		if (wg == null) {
			return true;
		}
		return wg.canBuild(p, b);
	}

	public List<String> getWorlds() {
		return dWorlds;
	}
	
}
