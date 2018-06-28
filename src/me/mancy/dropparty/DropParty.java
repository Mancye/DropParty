package me.mancy.dropparty;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ExplodeEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DropParty implements Listener {
	
	public static DropParty dropParty;
	
	public Map<Integer, Location> dropLocations;
	public boolean isActiveDropParty;
	
	public List<Double> commonChances;
	public List<Double> uncommonChances;
	public List<Double> rareChances;
	public List<Double> epicChances;
	public List<Double> legendaryChances;
	
	private Main plugin;

	private Location locToDrop;

	public DropParty(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		dropParty = this;
		commonChances = new ArrayList<>();
		uncommonChances = new ArrayList<>();
		rareChances = new ArrayList<>();
		epicChances = new ArrayList<>();
		legendaryChances = new ArrayList<>();
        dropLocations = new HashMap<>();
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
	private void dropParty(int tier) {
        float playerCount = (float) Bukkit.getServer().getOnlinePlayers().size();
		int amtToDrop = Math.round(playerCount * 1.5f);

		List <ItemStack> itemsToDrop = new ArrayList<>();

		Double commonPercentage = commonChances.get(tier - 1) / 100; // 75 = .75
		Double uncommonPercentage = uncommonChances.get(tier - 1) / 100; // 10 = .1
		Double rarePercentage = rareChances.get(tier - 1) / 100; // 10 = .1
		Double epicPercentage = epicChances.get(tier - 1) / 100; // 5 = .05
		Double legendaryPercentage = legendaryChances.get(tier - 1) / 100; // 0

		int amtCommonItems = commonPercentage.intValue() * amtToDrop;
        int amtUncommonItems = uncommonPercentage.intValue() * amtToDrop;
        int amtRareItems = rarePercentage.intValue() * amtToDrop;
        int amtEpicItems = epicPercentage.intValue() * amtToDrop;
        int amtLegendaryItems = legendaryPercentage.intValue() * amtToDrop;

        List <ItemStack> commonItems = new ArrayList<>();
        List <ItemStack> uncommonItems = new ArrayList<>();
        List <ItemStack> rareItems = new ArrayList<>();
        List <ItemStack> epicItems = new ArrayList<>();
        List <ItemStack> legendaryItems = new ArrayList<>();

        generateItemLists(commonItems, amtCommonItems, itemsToDrop, DropItems.dropItems.commonItems);
        generateItemLists(uncommonItems, amtUncommonItems, itemsToDrop, DropItems.dropItems.uncommonItems);
        generateItemLists(rareItems, amtRareItems, itemsToDrop, DropItems.dropItems.rareItems);
        generateItemLists(epicItems, amtEpicItems, itemsToDrop, DropItems.dropItems.epicItems);
        generateItemLists(legendaryItems, amtLegendaryItems, itemsToDrop, DropItems.dropItems.legendaryItems);

        int amtDropLocs =  Math.round(playerCount / 10f);
        int currentDropIndex = 0;
        //TODO Add fireworks
        EffectManager em = new EffectManager(plugin);

        for (int x = 0; x < itemsToDrop.size(); x++) {
            ExplodeEffect effect = new ExplodeEffect(em);
            ItemStack itemToDrop = itemsToDrop.get(x);
            if (commonItems.contains(itemToDrop)) {
                effect.color = Color.WHITE;
            } else if (uncommonItems.contains(itemToDrop)) {
                effect.color = Color.GREEN;
            } else if (rareItems.contains(itemToDrop)) {
                effect.color = Color.BLUE;
            } else if (rareItems.contains(itemToDrop)) {
                effect.color = Color.YELLOW;
            } else if (rareItems.contains(itemToDrop)) {
                effect.color = Color.RED;
            } else {
                effect.color = Color.BLACK;
            }

            if (currentDropIndex <= amtDropLocs) {
                locToDrop = dropLocations.get(currentDropIndex);
                currentDropIndex++;
            } else {
                currentDropIndex = 0;
            }

                World world = locToDrop.getWorld();
                // Add a callback to the effect
                effect.callback = new Runnable() {

                    @Override
                    public void run() {
                        world.dropItemNaturally(locToDrop, itemToDrop);
                    }

                };
                // Bleeding takes 15 seconds
                // period * iterations = time of effect
                effect.iterations = 2 * 20;
                effect.start();


        }

        //TODO Add colored explosions

	}

	private void generateItemLists(List<ItemStack> itemsList, int amtToAdd, List<ItemStack> finalListItems, Inventory itemInv) {
	    Random rand = new Random();
        int x = 0;
        int randNum;
	    while (itemsList.size() < amtToAdd) {

	        randNum = rand.nextInt(1) + 53;

           if (itemsList.get(x) == null) {
               if (itemInv.getItem(randNum) != null) {
                   itemsList.add(itemInv.getItem(randNum));
               }
           }
           x++;
	    }

        for (ItemStack i : itemsList) {
            finalListItems.add(i);
        }
    }
}
