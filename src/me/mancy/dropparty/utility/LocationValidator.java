package me.mancy.dropparty.utility;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class LocationValidator implements Listener {

    private Main plugin;

    public LocationValidator(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static Block getHighestBlock(Location loc) {
        int y = 0;
        if (loc == null) return null;
        while (loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + y, loc.getBlockZ())).getType().equals(Material.STAINED_GLASS) ||
                loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + y, loc.getBlockZ())).getType().equals(Material.BEACON)) {
            y++;
        }
        return loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + y, loc.getBlockZ()));
    }

    public static void validateLocations() {
        for (Location loc : LocationManager.getAllLocations()) {
            if (loc != null) {
                if (getHighestBlock(loc).getType() != Material.STAINED_GLASS) {
                    if (getHighestBlock(loc).getType() != Material.BEACON) {
                        if (getHighestBlock(loc).getType() != Material.AIR) {

                            if (!(LocationManager.getValidLocations().contains(loc))) {
                                LocationManager.getValidLocations().add(loc);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void locationValidated(BlockPlaceEvent event) {
        if (!(event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*"))) return;
        if (event.getBlock().getType().equals(Material.STAINED_GLASS)) return;
        for (Location loc : LocationManager.getAllLocations()) {
            if (!(LocationManager.getValidLocations().contains(loc))) {
                if (event.getBlock().getLocation().getBlockX() == loc.getBlockX() && event.getBlock().getLocation().getBlockZ() == loc.getBlockZ() && event.getBlock().getLocation().getBlockY() >= loc.getBlockY()) {
                    MessageUtil.sendMessageWithPrefix(event.getPlayer(), ChatColor.GREEN + "Drop location was validated successfully!");
                    LocationManager.getValidLocations().add(loc);
                }

            } else {
                MessageUtil.sendMessageWithPrefix(event.getPlayer(), ChatColor.RED + "Drop location was already validated!");
                return;
            }
        }

    }
    @EventHandler
    private void locationUnvalidated(BlockBreakEvent event) {

        if (event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*")) {
            if (!event.getBlock().getType().equals(Material.STAINED_GLASS)){
                List<Location> locsToRemove = new ArrayList<>();
                for (Location loc : LocationManager.getValidLocations()) {
                    if (loc.getBlockZ() == event.getBlock().getLocation().getBlockZ() && loc.getBlockX() == event.getBlock().getLocation().getBlockX() && event.getBlock().getLocation().getBlockY() >= loc.getBlockY()) {
                       locsToRemove.add(loc);
                       MessageUtil.sendMessageWithPrefix(event.getPlayer(),ChatColor.RED + "Location unvalidated, you must place a block above the beacon");
                    }
                }
                LocationManager.getValidLocations().removeAll(locsToRemove);
            }
        }
    }

}
