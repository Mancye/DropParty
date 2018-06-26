package me.mancy.dropparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Listener;

public class DropParty implements Listener {
	
	public static Map<Integer, Location> dropLocations = new HashMap<Integer, Location>();
	public static int numDropLocs;
	public static boolean isActiveDropParty;
	
	public static List<Double> commonChances = new ArrayList<Double>();
	public static List<Double> uncommonChances = new ArrayList<Double>();
	public static List<Double> rareChances = new ArrayList<Double>();
	public static List<Double> epicChances = new ArrayList<Double>();
	public static List<Double> legendaryChances = new ArrayList<Double>();
	
	private Main plugin;
	
	public DropParty(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static void modifyChances(List<Double> typeChances, int tier, double amount) {
		if (typeChances == commonChances) {
			commonChances.set(tier - 1, commonChances.get(tier - 1) + amount);
			Main.getPlugin(Main.class).dropChancesConfig.set("common", commonChances);
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropChancesConfig, Main.getPlugin(Main.class).dropChances);
		} else if (typeChances == uncommonChances) {
			uncommonChances.set(tier - 1, uncommonChances.get(tier - 1) + amount);
			Main.getPlugin(Main.class).dropChancesConfig.set("common", uncommonChances);
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropChancesConfig, Main.getPlugin(Main.class).dropChances);
		} else if (typeChances == rareChances) {
			rareChances.set(tier - 1, rareChances.get(tier - 1) + amount);
			Main.getPlugin(Main.class).dropChancesConfig.set("common", rareChances);
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropChancesConfig, Main.getPlugin(Main.class).dropChances);
		} else if (typeChances == epicChances) {
			epicChances.set(tier - 1, epicChances.get(tier - 1) + amount);
			Main.getPlugin(Main.class).dropChancesConfig.set("common", epicChances);
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropChancesConfig, Main.getPlugin(Main.class).dropChances);
		} else if (typeChances == legendaryChances) {
			legendaryChances.set(tier - 1, legendaryChances.get(tier - 1) + amount);
			Main.getPlugin(Main.class).dropChancesConfig.set("common", legendaryChances);
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropChancesConfig, Main.getPlugin(Main.class).dropChances);
		} else {
			return;
		}
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
