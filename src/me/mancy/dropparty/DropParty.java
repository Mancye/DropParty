package me.mancy.dropparty;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class DropParty implements Listener {

    public static DropParty dropParty;
    public List<Location> dropLocations = new ArrayList<>();
    public boolean isActiveDropParty;

    public List<Double> commonChances;
    public List<Double> uncommonChances;
    public List<Double> rareChances;
    public List<Double> epicChances;
    public List<Double> legendaryChances;

    public double heightToDrop = 1.0;
    public double radiusToDrop = 1.0;

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

        int amtToDrop = Math.round(((float) Bukkit.getServer().getOnlinePlayers().size()) * 10.5f);
        int amtDropLocs = Math.round(dropLocations.size() / 2f);

        for (int x = 1; x <= amtDropLocs; x++) {
            Firework f = dropLocations.get(x).getWorld().spawn(dropLocations.get(x - 1), Firework.class);
            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder()
                    .flicker(false)
                    .trail(true)
                    .withColor(Color.fromBGR(255, 0, 0))
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .withColor(Color.ORANGE)
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
        Random random = new Random();
        int randOrder = 1 + random.nextInt() * 6;
        switch (randOrder) {
            case 1: {
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
                generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                break;
            }
            case 2: {
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
                generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
                break;
            }
            case 3: {
                generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                break;
            }
            case 4: {
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
                break;
            }
            case 5: {
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
                generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                break;
            }
            case 6: {
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtUncommonItems, DropItems.dropItems.uncommonItems);
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                break;
            }
            default: {
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtCommonItems, DropItems.dropItems.commonItems);
                generateItemLists(amtRareItems, DropItems.dropItems.rareItems);
                generateItemLists(amtEpicItems, DropItems.dropItems.epicItems);
                generateItemLists(amtLegendaryItems, DropItems.dropItems.legendaryItems);
            }

        }
        BukkitScheduler bukkitScheduler = Bukkit.getServer().getScheduler();

        for (int x = 0; x < itemsToDrop.size(); x++) {
            final ItemStack i = itemsToDrop.get(x);
            i.setAmount(1);
            bukkitScheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    float red;
                    float green;
                    float blue;

                    if (currentDropIndex <= amtDropLocs) {
                        locToDrop = dropLocations.get(currentDropIndex);
                        currentDropIndex++;
                    } else {
                        currentDropIndex = 1;
                        locToDrop = dropLocations.get(currentDropIndex);
                    }
                    double offsetX = (-radiusToDrop) * random.nextDouble() * radiusToDrop;
                    double offsetZ = (-radiusToDrop) * random.nextDouble() * radiusToDrop;
                    Location offsetLoc = new Location(locToDrop.getWorld(), locToDrop.getX() + offsetX, locToDrop.getY() + heightToDrop, locToDrop.getZ() + offsetZ);
                    Location fireWorkLoc = new Location(offsetLoc.getWorld(), offsetLoc.getX(), locToDrop.getY(), offsetLoc.getZ());

                    Firework f = fireWorkLoc.getWorld().spawn(fireWorkLoc, Firework.class);

                    FireworkMeta fm = f.getFireworkMeta();
                    fm.addEffect(FireworkEffect.builder()
                            .flicker(false)
                            .trail(true)
                            .withColor(Color.LIME)
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .withColor(Color.ORANGE)
                            .withFade(Color.ORANGE)
                            .build());
                    fm.setPower(3);
                    f.setFireworkMeta(fm);




                    if (DropItems.dropItems.commonItems.contains(i)) {
                        red = 255;
                        green = 255;
                        blue = 255;
                    } else if (DropItems.dropItems.uncommonItems.contains(i)) {
                        red = 21;
                        green = 255;
                        blue = 0;
                    } else if (DropItems.dropItems.rareItems.contains(i)) {
                        red = 0;
                        green = 72;
                        blue = 255;
                    } else if (DropItems.dropItems.epicItems.contains(i)) {
                        red = 255;
                        green = 225;
                        blue = 0;
                    } else if (DropItems.dropItems.legendaryItems.contains(i)) {
                        red = 255;
                        green = 0;
                        blue = 0;
                    } else {
                        red = 0;
                        green = 0;
                        blue = 0;
                    }

                    PacketPlayOutWorldParticles particles;


                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        for (int amt = 0; amt < 40; amt++) {
                            float randX = 0.1f + random.nextFloat() * (1);
                            float randY = 0.4f + random.nextFloat() * (1);
                            float randZ = 0.1f + random.nextFloat() * (1);
                            particles = new PacketPlayOutWorldParticles(EnumParticle.SPELL_MOB, true, (float) fireWorkLoc.getX() + randX, (float) fireWorkLoc.getY() + randY, (float) fireWorkLoc.getZ() + randZ, red, green, blue, 255, 0, 10);
                            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(particles);
                        }
                    }


                    offsetLoc.getWorld().dropItemNaturally(offsetLoc, i);


                }
            }, 40L * (x + 1));
        }

        DropGUI.dropgui.isActiveDropParty = false;

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
