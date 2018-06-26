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
	
	public List<Double> commonChances;
	public List<Double> uncommonChances;
	public List<Double> rareChances;
	public List<Double> epicChances;
	public List<Double> legendaryChances;
	
	private Main plugin;
	
	public DropParty(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		dropParty = this;
		commonChances = new ArrayList<>();
		uncommonChances = new ArrayList<>();
		rareChances = new ArrayList<>();
		epicChances = new ArrayList<>();
		legendaryChances = new ArrayList<>();
	}

	public void modifyChances(List<Double> typeChances, int tier, double amount) {
		if (typeChances == commonChances) {
		    if (!(commonChances.size() <= (tier - 1))) {
                commonChances.set(tier - 1, commonChances.get(tier - 1) + amount);
            } else {
		        commonChances.add(amount);
            }
            plugin.dropChancesConfig.set("common", commonChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

		} else if (typeChances == uncommonChances) {
			if (!(uncommonChances.size() <= (tier - 1))) {
				uncommonChances.set(tier - 1, uncommonChances.get(tier - 1) + amount);
			} else {
				uncommonChances.add(amount);
			}
			plugin.dropChancesConfig.set("uncommon", uncommonChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

		} else if (typeChances == rareChances) {
			if (!(rareChances.size() <= (tier - 1))) {
				rareChances.set(tier - 1, rareChances.get(tier - 1) + amount);
			} else {
				rareChances.add(amount);
			}
			plugin.dropChancesConfig.set("rare", rareChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

		} else if (typeChances == epicChances) {
			if (!(epicChances.size() <= (tier - 1))) {
				epicChances.set(tier - 1, epicChances.get(tier - 1) + amount);
			} else {
				epicChances.add(amount);
			}
			plugin.dropChancesConfig.set("epic", epicChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

		} else if (typeChances == legendaryChances) {
			if (!(legendaryChances.size() <= (tier - 1))) {
				legendaryChances.set(tier - 1, legendaryChances.get(tier - 1) + amount);
			} else {
				legendaryChances.add(amount);
			}
			plugin.dropChancesConfig.set("legendary", legendaryChances);
			plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
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
