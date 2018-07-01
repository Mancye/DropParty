package me.mancy.dropparty;

import de.slikey.effectlib.effect.ExplodeEffect;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropParty implements Listener {

    public static DropParty dropParty;
    public int numDropLocs;
    public Map<Integer, Location> dropLocations = new HashMap<>();
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
    private List<ItemStack> itemsToDrop = new ArrayList<>();
    int currentDropIndex = 1;

    private void dropParty(int tier) {

        int amtToDrop = Math.round(((float) Bukkit.getServer().getOnlinePlayers().size()) * 20.5f);
        int amtDropLocs = Math.round(dropLocations.size() / 2f);


        for (int x = 1; x <= amtDropLocs; x++) {
            Firework f = dropLocations.get(x).getWorld().spawn(dropLocations.get(x), Firework.class);

            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder()
                    .flicker(false)
                    .trail(true)
                    .with(FireworkEffect.Type.CREEPER)
                    .withColor(Color.GREEN)
                    .withFade(Color.BLUE)
                    .build());
            fm.setPower(3);
            f.setFireworkMeta(fm);
        }

        float commonPercentage = (commonChances.get(tier - 1).floatValue()) / 100f; // 75 = .75
        float uncommonPercentage = (uncommonChances.get(tier - 1).floatValue()) / 100f; // 10 = .1
        float rarePercentage = (rareChances.get(tier - 1).floatValue()) / 100f; // 10 = .1
        float epicPercentage = (epicChances.get(tier - 1).floatValue()) / 100f; // 5 = .05
        float legendaryPercentage = (legendaryChances.get(tier - 1).floatValue()) / 100f; // 0

        int amtCommonItems = (int) (commonPercentage * amtToDrop);
        int amtUncommonItems = (int) (uncommonPercentage * amtToDrop);
        int amtRareItems = (int) (rarePercentage * amtToDrop);
        int amtEpicItems = (int) (epicPercentage * amtToDrop);
        int amtLegendaryItems = (int) (legendaryPercentage * amtToDrop);


        generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
        generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
        generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
        generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
        generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);

        BukkitScheduler bukkitScheduler = Bukkit.getServer().getScheduler();

        int amtDropped = 0;


        for (ItemStack i : itemsToDrop) {
            bukkitScheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    ExplodeEffect effect = new ExplodeEffect(plugin.effectManager);

                    if (DropItems.dropItems.commonItems.contains(i)) {
                        effect.color = Color.WHITE;
                    } else if (DropItems.dropItems.uncommonItems.contains(i)) {
                        effect.color = Color.GREEN;
                    } else if (DropItems.dropItems.rareItems.contains(i)) {
                        effect.color = Color.BLUE;
                    } else if (DropItems.dropItems.epicItems.contains(i)) {
                        effect.color = Color.YELLOW;
                    } else if (DropItems.dropItems.legendaryItems.contains(i)) {
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

                    effect.setLocation(locToDrop);
                    effect.visibleRange = 100f;
                    effect.amount = 20;
                    effect.start();
                    effect.cancel();
                    locToDrop.getWorld().dropItemNaturally(locToDrop, i);

                }
            }, 20L);
        }


    }

    private void generateItemLists(int amtToAdd, Inventory itemInv) {
        int itemsAdded = 0;
        for (ItemStack i : itemInv.getContents()) {
            if (itemsAdded < amtToAdd) {
                if (i != null) {
                    itemsToDrop.add(i);
                    itemsAdded++;
                }
            } else {
                break;
            }
        }
    }
}
