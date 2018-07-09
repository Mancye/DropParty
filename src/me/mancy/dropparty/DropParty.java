package me.mancy.dropparty;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class DropParty implements Listener {

    public static DropParty dropParty;
    public static List<Location> dropLocations = new ArrayList<>();
    public static List<Location> validDropLocations = new ArrayList<>();
    public boolean isActiveDropParty;

    public List<Double> commonChances;
    public List<Double> uncommonChances;
    public List<Double> rareChances;
    public List<Double> epicChances;
    public List<Double> legendaryChances;

    private Map<Location, Block> capBlocks = new HashMap<>();


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
    public static void validateDropLocations() {
        if (dropLocations.size() == 0) return;

        for (Location loc : dropLocations) {
            int y = 0;

            while (loc.getWorld().getBlockAt(loc.add(0, y, 0)).getType() != Material.AIR) {

                if (!(loc.getWorld().getBlockAt(loc).getType().equals(Material.STAINED_GLASS)))  {
                    validDropLocations.add(loc.add(0, y, 0));
                    break;
                }

                if (!loc.getWorld().getBlockAt(loc.add(0, y, 0)).getType().equals(Material.STAINED_GLASS)) {
                    validDropLocations.add(loc.add(0, y, 0));
                } else {
                    y++;
                }

            }
        }

    }
    String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD.toString() + "P" + ChatColor.RED + ChatColor.BOLD.toString() + "A" + ChatColor.DARK_GRAY + ":" + ChatColor.GRAY + "Events" + ChatColor.DARK_GRAY + "]";

    @EventHandler
    private void locationValidated(BlockPlaceEvent event) {
        if (!(event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*"))) return;
        for (Location loc : dropLocations) {
            if (event.getBlock().getLocation().getBlockX() == loc.getBlockX() && event.getBlock().getLocation().getBlockZ() == loc.getBlockZ() && event.getBlock().getLocation().getBlockY() >= loc.getBlockY()) {
                if (!(event.getBlock().getType().equals(Material.STAINED_GLASS))) {
                    if (!(validDropLocations.contains(loc))) {
                        validDropLocations.add(event.getBlock().getLocation());
                        capBlocks.put(event.getBlock().getLocation(), event.getBlock());
                        event.getPlayer().sendMessage(prefix + ChatColor.GREEN + " Drop location successfully validated");
                    }
                }
            }
        }


    }
    @EventHandler
    private void locationUnvalidated(BlockBreakEvent event) {

        if (event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*")) {
            if (event.getBlock().getType().equals(Material.BEACON)) {

                for (Location loc : validDropLocations) {
                    if (event.getBlock().getX() == loc.getBlockX() && event.getBlock().getZ() == loc.getBlockZ()) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(prefix + ChatColor.RED + " You must remove the drop location before destroying the beacon");
                    }
                }

            } else if (!event.getBlock().getType().equals(Material.STAINED_GLASS)){
                for (Location loc : validDropLocations) {
                    if (loc.getBlockZ() == event.getBlock().getZ() && loc.getBlockX() == event.getBlock().getX()) {
                        if (validDropLocations.contains(event.getBlock().getLocation())) {
                            validDropLocations.remove(event.getBlock().getLocation());
                            if (capBlocks.containsKey(event.getBlock().getLocation()))
                            capBlocks.remove(event.getBlock().getLocation());
                            event.getPlayer().sendMessage(prefix + ChatColor.RED + " Location unvalidated, you must place a block above the beacon");
                        }
                    }
                }
            }
        } else {
            return;
        }
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

    private List<ItemStack> itemsToDrop = new ArrayList<>();
    private int currentDropIndex = 0;
    int amtDropLocs = 0;
    int itemsDropped = 0;
    private void dropParty(int tier) {

        int amtToDrop = Math.round(((float) Bukkit.getServer().getOnlinePlayers().size()) * 5f);
        amtDropLocs = Math.round(((float) Bukkit.getServer().getOnlinePlayers().size()) / 2f);
        if (amtDropLocs > validDropLocations.size()) amtDropLocs = validDropLocations.size();
        for (Location loc : validDropLocations) {
            Block highest = loc.getWorld().getBlockAt(loc.getWorld().getHighestBlockAt(loc).getLocation().subtract(0, 1, 0));
            capBlocks.put(highest.getLocation(), highest);
        }

        for (Location loc : capBlocks.keySet()) {
            capBlocks.get(loc).setType(Material.AIR);
        }

        for (int x = 1; x <= amtDropLocs; x++) {
            Location launchLoc = validDropLocations.get(x - 1).add(0, 1, 0);
            Firework f = launchLoc.getWorld().spawn(launchLoc, Firework.class);
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
        int randOrder = random.nextInt(6) + 1;
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
                break;
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
                    if (amtDropLocs == 1 || validDropLocations.size() == 1) {
                        locToDrop = validDropLocations.get(0);
                    } else {
                        if (currentDropIndex < amtDropLocs) {

                            locToDrop = validDropLocations.get(currentDropIndex);
                            currentDropIndex++;
                        } else {
                            currentDropIndex = 0;
                            locToDrop = validDropLocations.get(currentDropIndex);
                        }
                    }

                    double offsetX = -radiusToDrop + (radiusToDrop + radiusToDrop) * random.nextDouble();
                    double offsetZ = -radiusToDrop + (radiusToDrop + radiusToDrop) * random.nextDouble();
                    Location offsetLoc = new Location(locToDrop.getWorld(), locToDrop.getX() + offsetX, (locToDrop.getWorld().getHighestBlockYAt(locToDrop) + 1) + heightToDrop, locToDrop.getZ() + offsetZ);

                    Firework f = offsetLoc.getWorld().spawn(offsetLoc, Firework.class);
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
                            particles = new PacketPlayOutWorldParticles(EnumParticle.SPELL_MOB, true, (float) offsetLoc.getX() + randX, (float) offsetLoc.getY() + randY, (float) offsetLoc.getZ() + randZ, red, green, blue, 255, 0, 10);
                            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(particles);
                        }
                    }


                    offsetLoc.getWorld().dropItemNaturally(offsetLoc, i);
                    itemsDropped++;
                    if (itemsDropped == itemsToDrop.size()) {
                        DropGUI.dropgui.isActiveDropParty = false;
                        for (Location loc : capBlocks.keySet()) {
                            loc.getBlock().setType(Material.STEP);
                        }
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.GRAY + " A Drop Party Has Ended!");
                    }

                }

            }, 40L * (x + 1));

        }


    }

    private void generateItemLists(int amtToAdd, Inventory itemInv) {
        int itemsAdded = 0;
        for (ItemStack i : itemInv.getContents()) {
            if (itemsAdded < amtToAdd) {
                if (i != null) {
                    if (!(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back"))) {

                        itemsToDrop.add(i);
                        itemsAdded++;
                    }
                }
            } else {
                break;
            }
        }
    }
}
