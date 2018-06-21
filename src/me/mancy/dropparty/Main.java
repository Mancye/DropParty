package me.mancy.dropparty;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("drop").setExecutor(new DropCommand(this));
		new DropCommand(this);
		System.out.println(ChatColor.GREEN + "DropPartyAlpha Enabled!");
	}
	
	@Override
	public void onDisable() {
		System.out.println(ChatColor.RED + "DropPartyAlpha Disabled!");
	}
}
