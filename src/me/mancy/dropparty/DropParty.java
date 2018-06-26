package me.mancy.dropparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Listener;

public class DropParty implements Listener {
	
	public static DropParty dropParty;
	
	public Map<Integer, Location> dropLocations = new HashMap<Integer, Location>();
	public int numDropLocs;
	public boolean isActiveDropParty;
	
	public List<Double> commonChances = new ArrayList<>();
	public List<Double> uncommonChances = new ArrayList<>();
	public List<Double> rareChances = new ArrayList<>();
	public List<Double> epicChances = new ArrayList<>();
	public List<Double> legendaryChances = new ArrayList<>();
	
	private Main plugin;
	
	public DropParty(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		dropParty = this;
	}


	public void modifyChances(List<Double> typeChances, int tier, double amount) {
		if (typeChances == commonChances) {
			commonChances.set(tier - 1, commonChances.get(tier - 1) + amount);
			plugin.dropChancesConfig.set("common", commonChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (typeChances == uncommonChances) {
			uncommonChances.set(tier - 1, uncommonChances.get(tier - 1) + amount);
			plugin.dropChancesConfig.set("common", uncommonChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (typeChances == rareChances) {
			rareChances.set(tier - 1, rareChances.get(tier - 1) + amount);
			plugin.dropChancesConfig.set("common", rareChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (typeChances == epicChances) {
			epicChances.set(tier - 1, epicChances.get(tier - 1) + amount);
			plugin.dropChancesConfig.set("common", epicChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (typeChances == legendaryChances) {
			legendaryChances.set(tier - 1, legendaryChances.get(tier - 1) + amount);
			plugin.dropChancesConfig.set("common", legendaryChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else {
			return;
		}
	}

	public void startDropParty(int type) {
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
