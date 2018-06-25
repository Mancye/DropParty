package me.mancy.dropparty;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Listener;

public class DropParty implements Listener {
	
	public static Map<Integer, Location> dropLocations = new HashMap<Integer, Location>();
	public static int numDropLocs;
	public static boolean isActiveDropParty;
	

	
	private Main plugin;
	
	public DropParty(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	public static void startDropParty(int type) {
		switch (type) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}

	
}
