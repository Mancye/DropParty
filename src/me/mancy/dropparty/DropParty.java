package me.mancy.dropparty;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Listener;

public class DropParty implements Listener {
	
	public static Map<Integer, Location> dropLocations = new HashMap<Integer, Location>();
	
	private Main plugin;
	
	public DropParty(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	
}
