package me.mancy.dropparty.managers;

import me.mancy.dropparty.main.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropPartyManager {

    public static List<Location> dropLocations = new ArrayList<>();
    public static List<Location> validDropLocations = new ArrayList<>();
    private static boolean isActiveDropParty = false;

    public static List<Double> commonChances = new ArrayList<>();;
    public static List<Double> uncommonChances = new ArrayList<>();;
    public static List<Double> rareChances = new ArrayList<>();;
    public static List<Double> epicChances = new ArrayList<>();;
    public static List<Double> legendaryChances = new ArrayList<>();;

    private Map<Location, Block> capBlocks = new HashMap<>();


    public static double heightToDrop = 1.0;
    public static double radiusToDrop = 1.0;

    private Main plugin;

    public DropPartyManager(Main main) {
        this.plugin = main;
    }


    public static void toggleActiveParty() {
        DropPartyManager.isActiveDropParty = !DropPartyManager.isActiveDropParty;
    }

    public static boolean isActiveDropParty() {
        return isActiveDropParty;
    }



}
