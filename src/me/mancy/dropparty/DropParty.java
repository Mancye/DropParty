package me.mancy.dropparty;

import de.slikey.effectlib.effect.ExplodeEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropParty implements Listener {
	
	public static DropParty dropParty;
	public int numDropLocs;
	public Map<Integer, Location> dropLocations = new HashMap<>();;
	public boolean isActiveDropParty;
	
	public List<Double> commonChances;
	public List<Double> uncommonChances;
	public List<Double> rareChances;
	public List<Double> epicChances;
	public List<Double> legendaryChances;
	
	private Main plugin;

	private Location locToDrop = null;

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
			dropParty(1);
			break;
		case 2:
		    dropParty(2);
			break;
		case 3:
		    dropParty(3);
			break;
		case 4:
		    dropParty(4);
			break;
		}
	}
/*
- Calculate total amount of items to drop(Player count / 2)
- Retrieve drop chances for each item category for the selected tier drop party, store these in variables
- Generate list of itemstacks, itemsToDrop
- Get amount of items for item category(In this case, common) (In this case, 6 common items, 1 uncommon, 1 rare, 1 epic, 1 legendary)
- Loop through item list(common) contents X(6) by generating a new random number between 1 and inv size and checking if the slot is empty or not, if not then add it to a list of just that category of item
- Repeat this for each item category to take a random item from the item list at the specified quantity
- Loop through each item category list and add each one to the itemsToDrop list to generate the final list of all the items that need to be dropped
- Loop through each element of itemsToDrop
- Increment a simple counter starting at 0
- Set location to drop = dropLocations.get(counter)
- Drop item at location to drop
- Check if the counter is greater than dropLocations.size(), if so then reset to 0 to cycle again through locations
- 1 second delay after each iteration of the loop

 */

	List <ItemStack> commonItems;
	List <ItemStack> uncommonItems;
	List <ItemStack> rareItems;
	List <ItemStack> epicItems;
	List <ItemStack> legendaryItems;
	List <ItemStack> itemsToDrop;
	private void dropParty(int tier) {
        float playerCount = (float) Bukkit.getServer().getOnlinePlayers().size();
		int amtToDrop = Math.round(playerCount * 20.5f);
		itemsToDrop = new ArrayList<>();
		int currentDropIndex = 1;
		float commonPercentage = (commonChances.get(tier - 1).floatValue()) / 100f; // 75 = .75
		float uncommonPercentage = (uncommonChances.get(tier - 1).floatValue()) / 100f; // 10 = .1
		float rarePercentage = (rareChances.get(tier - 1).floatValue()) / 100f; // 10 = .1
		float epicPercentage = (epicChances.get(tier - 1).floatValue()) / 100f; // 5 = .05
		float legendaryPercentage = (legendaryChances.get(tier - 1).floatValue()) / 100f; // 0

		int amtCommonItems = (int) (commonPercentage * amtToDrop);
        int amtUncommonItems = (int) (uncommonPercentage * amtToDrop);
        int amtRareItems =(int) (rarePercentage * amtToDrop);
        int amtEpicItems = (int) (epicPercentage * amtToDrop);
        int amtLegendaryItems = (int) (legendaryPercentage * amtToDrop);

        commonItems = new ArrayList<>();
        uncommonItems = new ArrayList<>();
        rareItems = new ArrayList<>();
        epicItems = new ArrayList<>();
        legendaryItems = new ArrayList<>();

        generateItemLists("common", amtCommonItems, DropItems.dropItems.commonItems);
        generateItemLists("uncommon", amtUncommonItems, DropItems.dropItems.uncommonItems);
        generateItemLists("rare", amtRareItems, DropItems.dropItems.rareItems);
        generateItemLists("epic", amtEpicItems, DropItems.dropItems.epicItems);
        generateItemLists("legendary", amtLegendaryItems, DropItems.dropItems.legendaryItems);

        int amtDropLocs =  Math.round(dropLocations.size() / 2f);

        //TODO Add fireworks

        for (ItemStack i : itemsToDrop) {
            ExplodeEffect effect = new ExplodeEffect(plugin.effectManager);

            if (commonItems.contains(i)) {
                effect.color = Color.WHITE;
            } else if (uncommonItems.contains(i)) {
                effect.color = Color.GREEN;
            } else if (rareItems.contains(i)) {
                effect.color = Color.BLUE;
            } else if (rareItems.contains(i)) {
                effect.color = Color.YELLOW;
            } else if (rareItems.contains(i)) {
                effect.color = Color.RED;
            } else {
                effect.color = Color.BLACK;
            }

            if (currentDropIndex <= amtDropLocs) {
                locToDrop = dropLocations.get(currentDropIndex);
                currentDropIndex++;
            } else {
                currentDropIndex = 1;
				locToDrop = dropLocations.get(currentDropIndex);
            }

            World world = locToDrop.getWorld();
            if (locToDrop == null) {
            	Bukkit.getServer().broadcastMessage("gotjcj");
			}
			if (i == null) {
            	Bukkit.getServer().broadcastMessage("how");
			}
			effect.setLocation(locToDrop);
			effect.start();
			world.dropItemNaturally(locToDrop, i);
                // Add a callback to the effect
                // Bleeding takes 15 seconds
                // period * iterations = time of effect



        }

        //TODO Add colored explosions

	}

	private void generateItemLists(String itemType, int amtToAdd, Inventory itemInv) {
        List<ItemStack> itemsList = null;
        switch (itemType) {
			case "common": {
				itemsList = commonItems;
				break;
			}
			case "uncommon": {
				itemsList = uncommonItems;
				break;
			}
			case "rare": {
				itemsList = rareItems;
				break;
			}
			case "epic": {
				itemsList = epicItems;
				break;
			}
			case "legendary": {
				itemsList = legendaryItems;
				break;
			}

		}

	    for (ItemStack item : itemInv.getContents()) {
        	if (itemInv != null) {
				if (item != null) {
					itemsList.add(item);
				}
			}
	    }

        for (ItemStack i : itemsList) {
        	if (itemsList.size() >= amtToAdd) {
        		break;
			}
            itemsToDrop.add(i);
        }
    }
}
