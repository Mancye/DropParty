package me.mancy.dropparty;

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
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class DropGUI implements Listener, Runnable {
    private static Main plugin;
    public static DropGUI dropgui;
    public static Inventory dropGUI;

    public static int countdownTime = 1;

    public static int tierOneCost;
    public static int tierTwoCost;
    public static int tierThreeCost;
    public static int tierFourCost;

    String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD.toString() + "P" + ChatColor.RED + ChatColor.BOLD.toString() + "A" + ChatColor.DARK_GRAY + ":" + ChatColor.GRAY + "Events" + ChatColor.DARK_GRAY + "]";

    int time;
    int taskID;

    public boolean isActiveDropParty = false;

    public DropGUI() {

    }

    public DropGUI(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        dropgui = this;
    }


    public static void fillDropGUI() {
        tierOneCost = plugin.tokensConfig.getInt("tierOneCost");
        tierTwoCost = plugin.tokensConfig.getInt("tierTwoCost");
        tierThreeCost = plugin.tokensConfig.getInt("tierThreeCost");
        tierFourCost = plugin.tokensConfig.getInt("tierFourCost");

        dropGUI = Bukkit.createInventory(null, 27, ChatColor.RED + "Drop Menu");
        ItemStack tierOne = new ItemStack(Material.COAL_ORE);
        ItemMeta tierOneMeta = tierOne.getItemMeta();
        List<String> tierOneLore = new ArrayList<>();
        tierOneMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 1");
        tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier One Drop Party!");
        tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierOneCost + " Token(s)");
        tierOneMeta.setLore(tierOneLore);
        tierOne.setItemMeta(tierOneMeta);
        dropGUI.setItem(10, tierOne);

        ItemStack tierTwo = new ItemStack(Material.IRON_BLOCK);
        ItemMeta tierTwoMeta = tierTwo.getItemMeta();
        List<String> tierTwoLore = new ArrayList<>();
        tierTwoMeta.setDisplayName(ChatColor.GRAY + "Tier 2");
        tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Two Drop Party!");
        tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierTwoCost + " Token(s)");
        tierTwoMeta.setLore(tierTwoLore);
        tierTwo.setItemMeta(tierTwoMeta);
        dropGUI.setItem(12, tierTwo);

        ItemStack tierThree = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta tierThreeMeta = tierThree.getItemMeta();
        List<String> tierThreeLore = new ArrayList<>();
        tierThreeMeta.setDisplayName(ChatColor.GOLD + "Tier 3");
        tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Three Drop Party!");
        tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierThreeCost + " Token(s)");
        tierThreeMeta.setLore(tierThreeLore);
        tierThree.setItemMeta(tierThreeMeta);
        dropGUI.setItem(14, tierThree);

        ItemStack tierFour = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta tierFourMeta = tierFour.getItemMeta();
        List<String> tierFourLore = new ArrayList<>();
        tierFourMeta.setDisplayName(ChatColor.AQUA + "Tier 4");
        tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Four Drop Party!");
        tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierFourCost + " Token(s)");
        tierFourMeta.setLore(tierFourLore);
        tierFour.setItemMeta(tierFourMeta);
        dropGUI.setItem(16, tierFour);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "Exit");
        exit.setItemMeta(exitMeta);
        dropGUI.setItem(18, exit);

    }

    public Inventory getDropChancesMenu() {
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
                increaseOneLore.add("Current: " + DropParty.dropParty.commonChances.get(0));
                decreaseOneLore.add("Current: " + DropParty.dropParty.commonChances.get(0));

                increaseTwoLore.add("Current: " + DropParty.dropParty.commonChances.get(1));
                decreaseTwoLore.add("Current: " + DropParty.dropParty.commonChances.get(1));

                increaseThreeLore.add("Current: " + DropParty.dropParty.commonChances.get(2));
                decreaseThreeLore.add("Current: " + DropParty.dropParty.commonChances.get(2));

                increaseFourLore.add("Current: " + DropParty.dropParty.commonChances.get(3));
                decreaseFourLore.add("Current: " + DropParty.dropParty.commonChances.get(3));
                break;
            case "uncommon":
                increaseOneLore.add("Current: " + DropParty.dropParty.uncommonChances.get(0));
                decreaseOneLore.add("Current: " + DropParty.dropParty.uncommonChances.get(0));

                increaseTwoLore.add("Current: " + DropParty.dropParty.uncommonChances.get(1));
                decreaseTwoLore.add("Current: " + DropParty.dropParty.uncommonChances.get(1));

                increaseThreeLore.add("Current: " + DropParty.dropParty.uncommonChances.get(2));
                decreaseThreeLore.add("Current: " + DropParty.dropParty.uncommonChances.get(2));

                increaseFourLore.add("Current: " + DropParty.dropParty.uncommonChances.get(3));
                decreaseFourLore.add("Current: " + DropParty.dropParty.uncommonChances.get(3));
                break;

            case "rare":
                increaseOneLore.add("Current: " + DropParty.dropParty.rareChances.get(0));
                decreaseOneLore.add("Current: " + DropParty.dropParty.rareChances.get(0));

                increaseTwoLore.add("Current: " + DropParty.dropParty.rareChances.get(1));
                decreaseTwoLore.add("Current: " + DropParty.dropParty.rareChances.get(1));

                increaseThreeLore.add("Current: " + DropParty.dropParty.rareChances.get(2));
                decreaseThreeLore.add("Current: " + DropParty.dropParty.rareChances.get(2));

                increaseFourLore.add("Current: " + DropParty.dropParty.rareChances.get(3));
                decreaseFourLore.add("Current: " + DropParty.dropParty.rareChances.get(3));
                break;

            case "epic":
                increaseOneLore.add("Current: " + DropParty.dropParty.epicChances.get(0));
                decreaseOneLore.add("Current: " + DropParty.dropParty.epicChances.get(0));

                increaseTwoLore.add("Current: " + DropParty.dropParty.epicChances.get(1));
                decreaseTwoLore.add("Current: " + DropParty.dropParty.epicChances.get(1));

                increaseThreeLore.add("Current: " + DropParty.dropParty.epicChances.get(2));
                decreaseThreeLore.add("Current: " + DropParty.dropParty.epicChances.get(2));

                increaseFourLore.add("Current: " + DropParty.dropParty.epicChances.get(3));
                decreaseFourLore.add("Current: " + DropParty.dropParty.epicChances.get(3));
                break;

            case "legendary":
                increaseOneLore.add("Current: " + DropParty.dropParty.legendaryChances.get(0));
                decreaseOneLore.add("Current: " + DropParty.dropParty.legendaryChances.get(0));

                increaseTwoLore.add("Current: " + DropParty.dropParty.legendaryChances.get(1));
                decreaseTwoLore.add("Current: " + DropParty.dropParty.legendaryChances.get(1));

                increaseThreeLore.add("Current: " + DropParty.dropParty.legendaryChances.get(2));
                decreaseThreeLore.add("Current: " + DropParty.dropParty.legendaryChances.get(2));

                increaseFourLore.add("Current: " + DropParty.dropParty.legendaryChances.get(3));
                decreaseFourLore.add("Current: " + DropParty.dropParty.legendaryChances.get(3));
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

    @EventHandler
    private void editDropChances(InventoryClickEvent event) {
        List<Double> dropChancesList = null;
        String selected = null;

        if (!(event.getWhoClicked() instanceof Player)) return;
        Inventory inv = event.getInventory();

        if (inv.getName().contains("COMMON DROP CHANCES") && !inv.getName().contains("UNCOMMON")) {
            dropChancesList = DropParty.dropParty.commonChances;
            selected = "common";
        } else if (inv.getName().contains("UNCOMMON DROP CHANCES")) {
            dropChancesList = DropParty.dropParty.uncommonChances;
            selected = "uncommon";
        } else if (inv.getName().contains("RARE DROP CHANCES")) {
            dropChancesList = DropParty.dropParty.rareChances;
            selected = "rare";
        } else if (inv.getName().contains("EPIC DROP CHANCES")) {
            dropChancesList = DropParty.dropParty.epicChances;
            selected = "epic";
        } else if (inv.getName().contains("LEGENDARY DROP CHANCES")) {
            dropChancesList = DropParty.dropParty.legendaryChances;
            selected = "legendary";
        } else {
            return;
        }


        Player p = (Player) event.getWhoClicked();


        int slot = event.getSlot();
        event.setCancelled(true);
        switch (slot) {
            case 0:
                DropParty.dropParty.modifyChances(dropChancesList, 1, 1);
                p.openInventory(editChances(selected));
                break;
            case 9:
                DropParty.dropParty.modifyChances(dropChancesList, 1, -1);
                p.openInventory(editChances(selected));
                break;
            case 1:
                DropParty.dropParty.modifyChances(dropChancesList, 2, 1);
                p.openInventory(editChances(selected));
                break;
            case 10:
                DropParty.dropParty.modifyChances(dropChancesList, 2, -1);
                p.openInventory(editChances(selected));
                break;
            case 2:
                DropParty.dropParty.modifyChances(dropChancesList, 3, 1);
                p.openInventory(editChances(selected));
                break;
            case 11:
                DropParty.dropParty.modifyChances(dropChancesList, 3, -1);
                p.openInventory(editChances(selected));
                break;
            case 3:
                DropParty.dropParty.modifyChances(dropChancesList, 4, 1);
                p.closeInventory();
                p.openInventory(editChances(selected));
                break;
            case 12: {
                DropParty.dropParty.modifyChances(dropChancesList, 4, -1);
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

    public void updateDisplayWithTokens(Player p) {

        ItemStack tokens = new ItemStack(Material.BOOK);
        ItemMeta tokensMeta = tokens.getItemMeta();
        List<String> tokensLore = new ArrayList<>();
        tokensMeta.setDisplayName(ChatColor.GRAY + "Your Tokens:");
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier One: " + ChatColor.GREEN + TokenManager.getTokens(p, 1));
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier Two: " + ChatColor.GREEN + TokenManager.getTokens(p, 2));
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier Three: " + ChatColor.GREEN + TokenManager.getTokens(p, 3));
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier Four: " + ChatColor.GREEN + TokenManager.getTokens(p, 4));
        tokensMeta.setLore(tokensLore);
        tokens.setItemMeta(tokensMeta);
        dropGUI.setItem(26, tokens);

    }


    private void setTimer(int amount) {
        time = amount;
    }

    private void startTimer(int dropTier) {
        isActiveDropParty = true;
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (time == 0) {
                    Bukkit.broadcastMessage("          --DROP PARTY--          ");
                    Bukkit.broadcastMessage(ChatColor.GREEN + "DROP PARTY HAS BEGUN! Look for activated totems!");
                    Bukkit.broadcastMessage("                                   ");
                    DropParty.dropParty.startDropParty(dropTier);
                    stopTimer();
                    return;
                }
                if (time % 5 == 0 && time <= 20) {
                    Bukkit.broadcastMessage(prefix + ChatColor.GRAY + " Time Until Drop Party" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + time + " Seconds!");
                }


                time -= 1;
            }
        }, 0L, 20L);
    }

    public void stopTimer() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    @EventHandler
    private void handleClicks(InventoryClickEvent event) {

        if (event.getInventory().getName().contains("Drop Menu")) {

            if (event.getWhoClicked() instanceof Player) {

                Player p = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                event.setCancelled(true);
                if (DropParty.validDropLocations.size() == 0) {
                    p.sendMessage(prefix + ChatColor.RED + " No valid drop locations set");
                    return;
                }
                if (DropItems.dropItems.commonItems.getContents().length == 0
                        && DropItems.dropItems.uncommonItems.getContents().length == 0
                        &&DropItems.dropItems.rareItems.getContents().length == 0
                        && DropItems.dropItems.epicItems.getContents().length == 0
                        && DropItems.dropItems.legendaryItems.getContents().length == 0 ) {
                    p.sendMessage(prefix + ChatColor.RED + " No items to drop");
                    return;
                }
                switch (slot) {

                    case 10:

                        if (!isActiveDropParty) {
                            if (TokenManager.getTokens(p, 1) >= tierOneCost) {
                                TokenManager.subtractTokens(p, 1, tierOneCost);
                                p.sendMessage(prefix + ChatColor.GRAY + " You have started a" + ChatColor.GREEN + " tier 1 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(1);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 1 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }
                            } else {
                                p.sendMessage(prefix + ChatColor.GRAY + " You do not have enough " + ChatColor.GREEN + "tier 1" + ChatColor.GRAY + " tokens for this! Your tokens: " + ChatColor.RED + TokenManager.getTokens(p, 1));
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.GRAY + " There is already an active drop party, please wait for it to finish");
                        }
                        break;
                    case 12:
                        if (!isActiveDropParty) {
                            if (TokenManager.getTokens(p, 2) >= tierTwoCost) {
                                TokenManager.subtractTokens(p, 2, tierTwoCost);
                                p.sendMessage(prefix + ChatColor.GRAY + " You have started a" + ChatColor.GREEN + " tier 2 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(2);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 2 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }
                            } else {
                                p.sendMessage(prefix + ChatColor.GRAY + " You do not have enough " + ChatColor.GREEN + "tier 2" + ChatColor.GRAY + " tokens for this! Your tokens: " + ChatColor.RED + TokenManager.getTokens(p, 2));
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.GRAY + " There is already an active drop party, please wait for it to finish");
                        }
                        break;
                    case 14:
                        if (!isActiveDropParty) {
                            if (TokenManager.getTokens(p, 3) >= tierThreeCost) {
                                TokenManager.subtractTokens(p, 3, tierThreeCost);
                                p.sendMessage(prefix + ChatColor.GRAY + " You have started a" + ChatColor.GREEN + " tier 3 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(3);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 3 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }
                            } else {
                                p.sendMessage(prefix + ChatColor.GRAY + " You do not have enough " + ChatColor.GREEN + "tier 3" + ChatColor.GRAY + " tokens for this! Your tokens: " + ChatColor.RED + TokenManager.getTokens(p, 3));
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.GRAY + " There is already an active drop party, please wait for it to finish");
                        }
                        break;
                    case 16: {
                        if (!isActiveDropParty) {
                            if (TokenManager.getTokens(p, 4) >= tierFourCost) {
                                TokenManager.subtractTokens(p, 4, tierFourCost);
                                p.sendMessage(prefix + ChatColor.GRAY + " You have started a" + ChatColor.GREEN + " tier 4 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(4);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 4 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }

                            } else {
                                p.sendMessage(prefix + ChatColor.GRAY + " You do not have enough " + ChatColor.GREEN + "tier 4" + ChatColor.GRAY + " tokens for this! Your tokens: " + ChatColor.RED + TokenManager.getTokens(p, 4));
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.GRAY + " There is already an active drop party, please wait for it to finish");
                        }
                        break;
                    }

                    case 18: {
                        p.closeInventory();
                        break;
                    }


                }

            }


        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
