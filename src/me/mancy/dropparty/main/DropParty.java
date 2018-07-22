package me.mancy.dropparty.main;

import me.mancy.dropparty.managers.DropPartyManager;
import me.mancy.dropparty.managers.LocationManager;
import me.mancy.dropparty.menus.EditItems;
import me.mancy.dropparty.utility.MessageUtil;
import net.minecraft.server.v1_13_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
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
    private Main plugin;
    private Location locToDrop = null;
    private int tier;

    public DropParty(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        dropParty = this;

    }

    public DropParty(Main main, int tier) {
        this.plugin = main;
        this.tier = tier;
    }

    private void removeBeaconCaps() {
        int amtDone = 0;

        for (Location loc : LocationManager.getValidLocations()) {
            if (amtDone < amtDropLocs) {
                Block highest = loc.getWorld().getBlockAt(loc.getWorld().getHighestBlockAt(loc).getLocation().subtract(0, 1, 0));
                capBlocks.put(highest.getLocation(), highest);
                amtDone++;
            } else {
                break;
            }
        }

        for (Location loc : capBlocks.keySet()) {
            capBlocks.get(loc).setType(Material.AIR);
        }
    }

    private void generateItemLists() {

        int amtToDrop = Math.round(((float) Bukkit.getServer().getOnlinePlayers().size()) * 5f);

        float commonPercentage = (DropPartyManager.commonChances.get(tier - 1).floatValue()) / 100f; // 75 = .75
        float uncommonPercentage = (DropPartyManager.uncommonChances.get(tier - 1).floatValue()) / 100f; // 10 = .1
        float rarePercentage = (DropPartyManager.rareChances.get(tier - 1).floatValue()) / 100f; // 10 = .1
        float epicPercentage = (DropPartyManager.epicChances.get(tier - 1).floatValue()) / 100f; // 5 = .05
        float legendaryPercentage = (DropPartyManager.legendaryChances.get(tier - 1).floatValue()) / 100f; // 0

        int amtCommonItems = (int) (commonPercentage * amtToDrop);
        int amtUncommonItems = (int) (uncommonPercentage * amtToDrop);
        int amtRareItems = (int) (rarePercentage * amtToDrop);
        int amtEpicItems = (int) (epicPercentage * amtToDrop);
        int amtLegendaryItems = (int) (legendaryPercentage * amtToDrop);

        Random random = new Random();
        int randOrder = random.nextInt(6) + 1;
        switch (randOrder) {
            case 1: {


                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                generateItemLists(amtUncommonItems, EditItems.editItems.uncommonItems);
                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                break;
            }
            case 2: {


                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                generateItemLists(amtUncommonItems, EditItems.editItems.uncommonItems);
                break;
            }
            case 3: {


                generateItemLists(amtUncommonItems, EditItems.editItems.uncommonItems);
                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                break;
            }
            case 4: {

                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtUncommonItems, EditItems.editItems.uncommonItems);
                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                break;
            }
            case 5: {


                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                generateItemLists(amtUncommonItems, EditItems.editItems.uncommonItems);
                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                break;
            }
            case 6: {


                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtUncommonItems, EditItems.editItems.uncommonItems);
                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                break;
            }
            default: {
                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtCommonItems, EditItems.editItems.commonItems);
                generateItemLists(amtRareItems, EditItems.editItems.rareItems);
                generateItemLists(amtEpicItems, EditItems.editItems.epicItems);
                generateItemLists(amtLegendaryItems, EditItems.editItems.legendaryItems);
                break;
            }

        }
    }

    private void startFireworks() {
        for (int x = 1; x <= amtDropLocs; x++) {
            Location launchLoc = LocationManager.getValidLocations().get(x - 1).add(0, 2, 0);
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
    }

    private void cycleLocations() {
        if (amtDropLocs == 1 || LocationManager.getValidLocations().size() == 1) {
            locToDrop = LocationManager.getValidLocations().get(0);
        } else {
            if (currentDropIndex < amtDropLocs) {

                locToDrop = LocationManager.getValidLocations().get(currentDropIndex);
                currentDropIndex++;
            } else {
                currentDropIndex = 0;
                locToDrop = LocationManager.getValidLocations().get(currentDropIndex);
            }
        }
    }

    private void playParticleEffects(Location offsetLoc, ItemStack i) {
        Random random = new Random();
        float red;
        float green;
        float blue;

        if (EditItems.editItems.commonItems.contains(i)) {
            red = 255;
            green = 255;
            blue = 255;
        } else if (EditItems.editItems.uncommonItems.contains(i)) {
            red = 21;
            green = 255;
            blue = 0;
        } else if (EditItems.editItems.rareItems.contains(i)) {
            red = 0;
            green = 72;
            blue = 255;
        } else if (EditItems.editItems.epicItems.contains(i)) {
            red = 255;
            green = 225;
            blue = 0;
        } else if (EditItems.editItems.legendaryItems.contains(i)) {
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
                online.spawnParticle(Particle.SPELL_MOB,offsetLoc.getX() + randX, offsetLoc.getY() + randY,offsetLoc.getZ() + randZ, 50);
            }
        }
    }

    private void playDropFireworks(Location offsetLoc) {
        Firework f = offsetLoc.getWorld().spawn(offsetLoc.add(0, 2, 0), Firework.class);
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
    }

    private void replaceBeaconCaps() {
        DropPartyManager.setIsActiveDropParty(false);
        for (Location loc : capBlocks.keySet()) {
            loc.getBlock().setType(Material.STONE_SLAB);
        }
        Bukkit.getServer().broadcastMessage(MessageUtil.getPrefix() + ChatColor.GRAY + " A Drop Party Has Ended!");
    }

    private List<ItemStack> itemsToDrop = new ArrayList<>();
    private int currentDropIndex = 0;
    private int amtDropLocs = Math.round(((float) Bukkit.getServer().getOnlinePlayers().size()) / 2f);
    private int itemsDropped = 0;

    private Map<Location, Block> capBlocks = new HashMap<>();

    public void start() {
        int tier = this.tier;

        DropPartyManager.setIsActiveDropParty(true);


        if (amtDropLocs > LocationManager.getValidLocations().size()) amtDropLocs = LocationManager.getValidLocations().size();

        removeBeaconCaps();
        startFireworks();
        generateItemLists();

        BukkitScheduler bukkitScheduler = Bukkit.getServer().getScheduler();

        for (int x = 0; x < itemsToDrop.size(); x++) {
            final ItemStack i = itemsToDrop.get(x);
            i.setAmount(1);

            bukkitScheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {

                    cycleLocations();

                    double offsetX = -DropPartyManager.radiusToDrop + (DropPartyManager.radiusToDrop + DropPartyManager.radiusToDrop) * new Random().nextDouble();
                    double offsetZ = -DropPartyManager.radiusToDrop + (DropPartyManager.radiusToDrop + DropPartyManager.radiusToDrop) * new Random().nextDouble();
                    Location offsetLoc = new Location(locToDrop.getWorld(), locToDrop.getX() + offsetX, (locToDrop.getWorld().getHighestBlockYAt(locToDrop) + 1) + DropPartyManager.heightToDrop, locToDrop.getZ() + offsetZ);

                   playDropFireworks(offsetLoc);
                   playParticleEffects(offsetLoc, i);

                    offsetLoc.getWorld().dropItemNaturally(offsetLoc, i);
                    itemsDropped++;
                    if (itemsDropped >= itemsToDrop.size()) {
                       replaceBeaconCaps();
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
