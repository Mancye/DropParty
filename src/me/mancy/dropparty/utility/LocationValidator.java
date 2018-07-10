package me.mancy.dropparty.utility;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class LocationValidator implements Listener {

    private Main plugin;

    public LocationValidator(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void validateLocations() {
        for (Location loc : LocationManager.getAllLocations()) {
            if (loc != null) {
                if (loc.getWorld().getHighestBlockAt(loc).getType() != Material.STAINED_GLASS) {
                    if (loc.getWorld().getHighestBlockAt(loc).getType() != Material.AIR) {
                        if (!(LocationManager.getValidLocations().contains(loc))) {
                            LocationManager.getValidLocations().add(loc);
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

                if (event.getBlock().getLocation().getBlockX() == loc.getBlockX() && event.getBlock().getLocation().getBlockZ() == loc.getBlockZ() && event.getBlock().getLocation().getBlockY() > loc.getBlockY()) {
                    MessageUtil.sendMessageWithPrefix(event.getPlayer(), ChatColor.GREEN + "Drop location was validated successfully!");
                    LocationManager.getValidLocations().add(loc);
                }

            } else {
                return;
            }
        }

    }
    @EventHandler
    private void locationUnvalidated(BlockBreakEvent event) {

        if (event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*")) {
            if (!event.getBlock().getType().equals(Material.STAINED_GLASS)){
                for (Location loc : LocationManager.getValidLocations()) {
                    if (loc.getBlockZ() == event.getBlock().getZ() && loc.getBlockX() == event.getBlock().getX() && loc.getBlockY() > loc.getBlockY()) {
                        MessageUtil.sendMessageWithPrefix(event.getPlayer(),ChatColor.RED + "Location unvalidated, you must place a block above the beacon");
                        LocationManager.getValidLocations().remove(loc);
                    }
                }
            }
        }
    }

}
