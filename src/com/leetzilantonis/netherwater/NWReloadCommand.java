package com.leetzilantonis.netherwater;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NWReloadCommand implements CommandExecutor {

	private Main plugin;

	public NWReloadCommand(Main instance) {

		plugin = instance;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("netherwater.reload")) {

			sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return true;

		} else {

			plugin.reloadConfig();
			plugin.saveConfig();
			sender.sendMessage(ChatColor.GREEN + "Nether Water configuration reloaded!");
			return true;

		}

	}
}
