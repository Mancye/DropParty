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
		setDefaultChances();
	}

	private void setDefaultChances() {
	    modifyChances(commonChances, 1, 25);
	    modifyChances(commonChances, 2, 25);
	    modifyChances(commonChances, 3, 25);
	    modifyChances(commonChances, 4, 25);


	    modifyChances(uncommonChances, 1, 25);
	    modifyChances(uncommonChances, 2, 25);
	    modifyChances(uncommonChances, 3, 25);
	    modifyChances(uncommonChances, 4, 25);


	    modifyChances(rareChances, 1, 25);
	    modifyChances(rareChances, 2, 25);
	    modifyChances(rareChances, 3, 25);
	    modifyChances(rareChances, 4, 25);

	    modifyChances(epicChances, 1, 25);
	    modifyChances(epicChances, 2, 25);
	    modifyChances(epicChances, 3, 25);
	    modifyChances(epicChances, 4, 25);

	    modifyChances(legendaryChances, 1, 25);
	    modifyChances(legendaryChances, 2, 25);
	    modifyChances(legendaryChances, 3, 25);
	    modifyChances(legendaryChances, 4, 25);

    }

	public void modifyChances(List<Double> typeChances, int tier, double amount) {
		if (typeChances == commonChances) {
		    if (!(commonChances.size() <= (tier - 1))) {
                commonChances.set(tier - 1, commonChances.get(tier - 1) + amount);
            } else {
		        commonChances.add(amount);
            }
            commonChances.set(tier - 1, commonChances.get(tier - 1) + amount);
            plugin.dropChancesConfig.set("common", commonChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (!(uncommonChances.size() <= (tier - 1))) {
            if (!(uncommonChances.get(tier - 1) == null)) {
                uncommonChances.set(tier - 1, uncommonChances.get(tier - 1) + amount);
            } else {
                uncommonChances.add(amount);
            }
            uncommonChances.set(tier - 1, uncommonChances.get(tier - 1) + amount);
            plugin.dropChancesConfig.set("uncommon", commonChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (!(rareChances.size() <= (tier - 1))) {
            if (!(rareChances.get(tier - 1) == null)) {
                rareChances.set(tier - 1, rareChances.get(tier - 1) + amount);
            } else {
                rareChances.add(amount);
            }
            rareChances.set(tier - 1, rareChances.get(tier - 1) + amount);
            plugin.dropChancesConfig.set("rare", rareChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (!(epicChances.size() <= (tier - 1))) {
            if (!(epicChances.get(tier - 1) == null)) {
                epicChances.set(tier - 1, epicChances.get(tier - 1) + amount);
            } else {
                epicChances.add(amount);
            }
            epicChances.set(tier - 1, epicChances.get(tier - 1) + amount);
            plugin.dropChancesConfig.set("epic", epicChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
		} else if (!(legendaryChances.size() <= (tier - 1))) {
            if (!(legendaryChances.get(tier - 1) == null)) {
                legendaryChances.set(tier - 1, legendaryChances.get(tier - 1) + amount);
            } else {
                legendaryChances.add(amount);
            }
            legendaryChances.set(tier - 1, legendaryChances.get(tier - 1) + amount);
            plugin.dropChancesConfig.set("legendary", legendaryChances);
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
