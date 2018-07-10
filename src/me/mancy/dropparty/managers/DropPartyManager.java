package me.mancy.dropparty.managers;

import me.mancy.dropparty.main.Main;

import java.util.ArrayList;
import java.util.List;

public class DropPartyManager {

    private static boolean isActiveDropParty = false;

    public static List<Double> commonChances = new ArrayList<>();
    public static List<Double> uncommonChances = new ArrayList<>();
    public static List<Double> rareChances = new ArrayList<>();
    public static List<Double> epicChances = new ArrayList<>();
    public static List<Double> legendaryChances = new ArrayList<>();

    public static int amtDrops = LocationManager.getAllLocations().size();

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
