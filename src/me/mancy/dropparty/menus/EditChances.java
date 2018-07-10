package me.mancy.dropparty.menus;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.managers.DropPartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditChances implements Listener {

    private Main plugin;

    public EditChances(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static Inventory getDropChancesMenu() {
        Inventory dropChances = Bukkit.createInventory(null, 27, ChatColor.RED + "Edit Drop Chances");

        ItemStack common = new ItemStack(Material.COAL_ORE);
        ItemMeta commonMeta = common.getItemMeta();
        List<String> commonLore = new ArrayList<String>();
        commonMeta.setDisplayName(ChatColor.DARK_GRAY + "Common");
        commonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Common Item Drop Chances");
        commonMeta.setLore(commonLore);
        common.setItemMeta(commonMeta);
        dropChances.setItem(10, common);

        ItemStack uncommon = new ItemStack(Material.IRON_BLOCK);
        ItemMeta uncommonMeta = uncommon.getItemMeta();
        List<String> uncommonLore = new ArrayList<String>();
        uncommonMeta.setDisplayName(ChatColor.GRAY + "Uncommon");
        uncommonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Uncommon Item Drop chances");
        uncommonMeta.setLore(uncommonLore);
        uncommon.setItemMeta(uncommonMeta);
        dropChances.setItem(11, uncommon);

        ItemStack rare = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta rareMeta = rare.getItemMeta();
        List<String> rareLore = new ArrayList<String>();
        rareMeta.setDisplayName(ChatColor.GOLD + "Rare");
        rareLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Rare Item Drop Chances");
        rareMeta.setLore(rareLore);
        rare.setItemMeta(rareMeta);
        dropChances.setItem(12, rare);

        ItemStack epic = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta epicMeta = epic.getItemMeta();
        List<String> epicLore = new ArrayList<String>();
        epicMeta.setDisplayName(ChatColor.AQUA + "Epic");
        epicLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Epic Item Drop Chances");
        epicMeta.setLore(epicLore);
        epic.setItemMeta(epicMeta);
        dropChances.setItem(13, epic);

        ItemStack legendary = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta legendaryMeta = legendary.getItemMeta();
        List<String> legendaryLore = new ArrayList<String>();
        legendaryMeta.setDisplayName(ChatColor.AQUA + "Legendary");
        legendaryLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Legendary Item Drop Chances");
        legendaryMeta.setLore(legendaryLore);
        legendary.setItemMeta(legendaryMeta);
        dropChances.setItem(14, legendary);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "Exit");
        exit.setItemMeta(exitMeta);
        dropChances.setItem(26, exit);

        return dropChances;
    }

    private Inventory editChances(String selected) {
        Inventory menu = Bukkit.createInventory(null, 18, selected.toUpperCase() + " DROP CHANCES");

        ItemStack increaseOne = new ItemStack(Material.COAL_ORE);
        ItemMeta increaseOneMeta = increaseOne.getItemMeta();
        List<String> increaseOneLore = new ArrayList<String>();
        increaseOneMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 1");
        increaseOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Increase Drop Chance");


        ItemStack decreaseOne = new ItemStack(Material.COAL_ORE);
        ItemMeta decreaseOneMeta = decreaseOne.getItemMeta();
        List<String> decreaseOneLore = new ArrayList<String>();
        decreaseOneMeta.setDisplayName(ChatColor.GRAY + "Tier 1");
        decreaseOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Decrease Drop Chance");


        ItemStack increaseTwo = new ItemStack(Material.IRON_BLOCK);
        ItemMeta increaseTwoMeta = increaseTwo.getItemMeta();
        List<String> increaseTwoLore = new ArrayList<String>();
        increaseTwoMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 2");
        increaseTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Increase Drop Chance");


        ItemStack decreaseTwo = new ItemStack(Material.IRON_BLOCK);
        ItemMeta decreaseTwoMeta = decreaseTwo.getItemMeta();
        List<String> decreaseTwoLore = new ArrayList<String>();
        decreaseTwoMeta.setDisplayName(ChatColor.GRAY + "Tier 2");
        decreaseTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Decrease Drop Chance");


        ItemStack increaseThree = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta increaseThreeMeta = increaseThree.getItemMeta();
        List<String> increaseThreeLore = new ArrayList<String>();
        increaseThreeMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 3");
        increaseThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Increase Drop Chance");


        ItemStack decreaseThree = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta decreaseThreeMeta = decreaseThree.getItemMeta();
        List<String> decreaseThreeLore = new ArrayList<String>();
        decreaseThreeMeta.setDisplayName(ChatColor.GRAY + "Tier 3");
        decreaseThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Decrease Drop Chance");


        ItemStack increaseFour = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta increaseFourMeta = increaseFour.getItemMeta();
        List<String> increaseFourLore = new ArrayList<String>();
        increaseFourMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 4");
        increaseFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Increase Drop Chance");


        ItemStack decreaseFour = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta decreaseFourMeta = decreaseTwo.getItemMeta();
        List<String> decreaseFourLore = new ArrayList<String>();
        decreaseFourMeta.setDisplayName(ChatColor.GRAY + "Tier 4");
        decreaseFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Decrease Drop Chance");

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);
        menu.setItem(17, back);
        switch (selected) {
            case "common":
                increaseOneLore.add("Current: " + DropPartyManager.commonChances.get(0));
                decreaseOneLore.add("Current: " + DropPartyManager.commonChances.get(0));

                increaseTwoLore.add("Current: " + DropPartyManager.commonChances.get(1));
                decreaseTwoLore.add("Current: " + DropPartyManager.commonChances.get(1));

                increaseThreeLore.add("Current: " + DropPartyManager.commonChances.get(2));
                decreaseThreeLore.add("Current: " + DropPartyManager.commonChances.get(2));

                increaseFourLore.add("Current: " + DropPartyManager.commonChances.get(3));
                decreaseFourLore.add("Current: " + DropPartyManager.commonChances.get(3));
                break;
            case "uncommon":
                increaseOneLore.add("Current: " + DropPartyManager.uncommonChances.get(0));
                decreaseOneLore.add("Current: " + DropPartyManager.uncommonChances.get(0));

                increaseTwoLore.add("Current: " + DropPartyManager.uncommonChances.get(1));
                decreaseTwoLore.add("Current: " + DropPartyManager.uncommonChances.get(1));

                increaseThreeLore.add("Current: " + DropPartyManager.uncommonChances.get(2));
                decreaseThreeLore.add("Current: " + DropPartyManager.uncommonChances.get(2));

                increaseFourLore.add("Current: " + DropPartyManager.uncommonChances.get(3));
                decreaseFourLore.add("Current: " + DropPartyManager.uncommonChances.get(3));
                break;

            case "rare":
                increaseOneLore.add("Current: " + DropPartyManager.rareChances.get(0));
                decreaseOneLore.add("Current: " + DropPartyManager.rareChances.get(0));

                increaseTwoLore.add("Current: " + DropPartyManager.rareChances.get(1));
                decreaseTwoLore.add("Current: " + DropPartyManager.rareChances.get(1));

                increaseThreeLore.add("Current: " + DropPartyManager.rareChances.get(2));
                decreaseThreeLore.add("Current: " + DropPartyManager.rareChances.get(2));

                increaseFourLore.add("Current: " + DropPartyManager.rareChances.get(3));
                decreaseFourLore.add("Current: " + DropPartyManager.rareChances.get(3));
                break;

            case "epic":
                increaseOneLore.add("Current: " + DropPartyManager.epicChances.get(0));
                decreaseOneLore.add("Current: " + DropPartyManager.epicChances.get(0));

                increaseTwoLore.add("Current: " + DropPartyManager.epicChances.get(1));
                decreaseTwoLore.add("Current: " + DropPartyManager.epicChances.get(1));

                increaseThreeLore.add("Current: " + DropPartyManager.epicChances.get(2));
                decreaseThreeLore.add("Current: " + DropPartyManager.epicChances.get(2));

                increaseFourLore.add("Current: " + DropPartyManager.epicChances.get(3));
                decreaseFourLore.add("Current: " + DropPartyManager.epicChances.get(3));
                break;

            case "legendary":
                increaseOneLore.add("Current: " + DropPartyManager.legendaryChances.get(0));
                decreaseOneLore.add("Current: " + DropPartyManager.legendaryChances.get(0));

                increaseTwoLore.add("Current: " + DropPartyManager.legendaryChances.get(1));
                decreaseTwoLore.add("Current: " + DropPartyManager.legendaryChances.get(1));

                increaseThreeLore.add("Current: " + DropPartyManager.legendaryChances.get(2));
                decreaseThreeLore.add("Current: " + DropPartyManager.legendaryChances.get(2));

                increaseFourLore.add("Current: " + DropPartyManager.legendaryChances.get(3));
                decreaseFourLore.add("Current: " + DropPartyManager.legendaryChances.get(3));
                break;
        }

        increaseOneMeta.setLore(increaseOneLore);
        increaseOne.setItemMeta(increaseOneMeta);
        menu.setItem(0, increaseOne);

        decreaseOneMeta.setLore(decreaseOneLore);
        decreaseOne.setItemMeta(decreaseOneMeta);
        menu.setItem(9, decreaseOne);

        increaseTwoMeta.setLore(increaseTwoLore);
        increaseTwo.setItemMeta(increaseTwoMeta);
        menu.setItem(1, increaseTwo);

        decreaseTwoMeta.setLore(decreaseTwoLore);
        decreaseTwo.setItemMeta(decreaseTwoMeta);
        menu.setItem(10, decreaseTwo);

        increaseThreeMeta.setLore(increaseThreeLore);
        increaseThree.setItemMeta(increaseThreeMeta);
        menu.setItem(2, increaseThree);

        decreaseThreeMeta.setLore(decreaseThreeLore);
        decreaseThree.setItemMeta(decreaseThreeMeta);
        menu.setItem(11, decreaseThree);

        increaseFourMeta.setLore(increaseFourLore);
        increaseFour.setItemMeta(increaseFourMeta);
        menu.setItem(3, increaseFour);

        decreaseFourMeta.setLore(decreaseFourLore);
        decreaseFour.setItemMeta(decreaseFourMeta);
        menu.setItem(12, decreaseFour);

        return menu;

    }

    public void modifyChances(List<Double> typeChances, int tier, double amount) {
        if (typeChances == DropPartyManager.commonChances) {
            if (!(DropPartyManager.commonChances.size() <= (tier - 1))) {
                DropPartyManager.commonChances.set(tier - 1, DropPartyManager.commonChances.get(tier - 1) + amount);
            } else {
                DropPartyManager.commonChances.add(amount);
            }
            plugin.dropChancesConfig.set("common", DropPartyManager.commonChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

        } else if (typeChances == DropPartyManager.uncommonChances) {
            if (!(DropPartyManager.uncommonChances.size() <= (tier - 1))) {
                DropPartyManager.uncommonChances.set(tier - 1, DropPartyManager.uncommonChances.get(tier - 1) + amount);
            } else {
                DropPartyManager.uncommonChances.add(amount);
            }
            plugin.dropChancesConfig.set("uncommon", DropPartyManager.uncommonChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

        } else if (typeChances == DropPartyManager.rareChances) {
            if (!(DropPartyManager.rareChances.size() <= (tier - 1))) {
                DropPartyManager.rareChances.set(tier - 1, DropPartyManager.rareChances.get(tier - 1) + amount);
            } else {
                DropPartyManager.rareChances.add(amount);
            }
            plugin.dropChancesConfig.set("rare", DropPartyManager.rareChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

        } else if (typeChances == DropPartyManager.epicChances) {
            if (!(DropPartyManager.epicChances.size() <= (tier - 1))) {
                DropPartyManager.epicChances.set(tier - 1, DropPartyManager.epicChances.get(tier - 1) + amount);
            } else {
                DropPartyManager.epicChances.add(amount);
            }
            plugin.dropChancesConfig.set("epic", DropPartyManager.epicChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);

        } else if (typeChances == DropPartyManager.legendaryChances) {
            if (!(DropPartyManager.legendaryChances.size() <= (tier - 1))) {
                DropPartyManager.legendaryChances.set(tier - 1, DropPartyManager.legendaryChances.get(tier - 1) + amount);
            } else {
                DropPartyManager.legendaryChances.add(amount);
            }
            plugin.dropChancesConfig.set("legendary", DropPartyManager.legendaryChances);
            plugin.saveCustomYml(plugin.dropChancesConfig, plugin.dropChances);
        }
    }

    @EventHandler
    private void editDropChances(InventoryClickEvent event) {
        List<Double> dropChancesList = null;
        String selected = null;

        if (!(event.getWhoClicked() instanceof Player)) return;
        Inventory inv = event.getInventory();

        if (inv.getName().contains("COMMON DROP CHANCES") && !inv.getName().contains("UNCOMMON")) {
            dropChancesList = DropPartyManager.commonChances;
            selected = "common";
        } else if (inv.getName().contains("UNCOMMON DROP CHANCES")) {
            dropChancesList = DropPartyManager.uncommonChances;
            selected = "uncommon";
        } else if (inv.getName().contains("RARE DROP CHANCES")) {
            dropChancesList = DropPartyManager.rareChances;
            selected = "rare";
        } else if (inv.getName().contains("EPIC DROP CHANCES")) {
            dropChancesList = DropPartyManager.epicChances;
            selected = "epic";
        } else if (inv.getName().contains("LEGENDARY DROP CHANCES")) {
            dropChancesList = DropPartyManager.legendaryChances;
            selected = "legendary";
        } else {
            return;
        }


        Player p = (Player) event.getWhoClicked();


        int slot = event.getSlot();
        event.setCancelled(true);
        switch (slot) {
            case 0:
                modifyChances(dropChancesList, 1, 1);
                p.openInventory(editChances(selected));
                break;
            case 9:
                modifyChances(dropChancesList, 1, -1);
                p.openInventory(editChances(selected));
                break;
            case 1:
                modifyChances(dropChancesList, 2, 1);
                p.openInventory(editChances(selected));
                break;
            case 10:
                modifyChances(dropChancesList, 2, -1);
                p.openInventory(editChances(selected));
                break;
            case 2:
                modifyChances(dropChancesList, 3, 1);
                p.openInventory(editChances(selected));
                break;
            case 11:
                modifyChances(dropChancesList, 3, -1);
                p.openInventory(editChances(selected));
                break;
            case 3:
                modifyChances(dropChancesList, 4, 1);
                p.openInventory(editChances(selected));
                break;
            case 12: {
                modifyChances(dropChancesList, 4, -1);
                p.openInventory(editChances(selected));
                break;
            }
            case 17: {
                p.openInventory(getDropChancesMenu());
            }


        }

    }

    @EventHandler
    private void selectDropChancesMenu(InventoryClickEvent event) {
        if (!(event.getInventory().getName().contains("Edit Drop Chances"))) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        int slot = event.getSlot();
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        switch (slot) {
            case 10: {
                p.openInventory(editChances("common"));
                break;
            }
            case 11: {
                p.openInventory(editChances("uncommon"));
                break;
            }
            case 12: {
                p.openInventory(editChances("rare"));
                break;
            }
            case 13: {
                p.openInventory(editChances("epic"));
                break;
            }
            case 14: {
                p.openInventory(editChances("legendary"));
                break;
            }
            case 26: {
                p.closeInventory();
                break;
            }

            default:
                break;
        }
    }

}
