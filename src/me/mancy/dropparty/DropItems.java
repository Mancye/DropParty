package me.mancy.dropparty;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropItems implements Listener {

	private Main plugin;

	public static Inventory commonItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Common Items");
	public static Inventory uncommonItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Uncommon Items");
	public static Inventory rareItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Rare Items");
	public static Inventory epicItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Epic Items");
	public static Inventory legendaryItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Legendary Items");

	public DropItems(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static Inventory openCategoriesMenu() {
		Inventory categories = Bukkit.getServer().createInventory(null, 54, "Select Category");

		ItemStack common = new ItemStack(Material.COAL_ORE);
		ItemMeta commonMeta = common.getItemMeta();
		List<String> commonLore = new ArrayList<String>();
		commonMeta.setDisplayName(ChatColor.AQUA + "Common");
		commonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Common Item List");
		commonMeta.setLore(commonLore);
		common.setItemMeta(commonMeta);
		categories.setItem(11, common);

		ItemStack unCommon = new ItemStack(Material.IRON_BLOCK);
		ItemMeta unCommonMeta = unCommon.getItemMeta();
		List<String> unCommonLore = new ArrayList<String>();
		unCommonMeta.setDisplayName(ChatColor.AQUA + "Uncommon");
		unCommonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Uncommon Item List");
		unCommonMeta.setLore(unCommonLore);
		unCommon.setItemMeta(unCommonMeta);
		categories.setItem(12, unCommon);

		ItemStack rare = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta rareMeta = rare.getItemMeta();
		List<String> rareLore = new ArrayList<String>();
		rareMeta.setDisplayName(ChatColor.AQUA + "Rare");
		rareLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Rare Item List");
		rareMeta.setLore(rareLore);
		rare.setItemMeta(rareMeta);
		categories.setItem(13, rare);

		ItemStack epic = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta epicMeta = epic.getItemMeta();
		List<String> epicLore = new ArrayList<String>();
		epicMeta.setDisplayName(ChatColor.AQUA + "Epic");
		epicLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Epic Item List");
		epicMeta.setLore(epicLore);
		epic.setItemMeta(epicMeta);
		categories.setItem(14, epic);

		ItemStack legendary = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta legendaryMeta = legendary.getItemMeta();
		List<String> legendaryLore = new ArrayList<String>();
		legendaryMeta.setDisplayName(ChatColor.AQUA + "Legendary");
		legendaryLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Legendary Item List");
		legendaryMeta.setLore(legendaryLore);
		legendary.setItemMeta(legendaryMeta);
		categories.setItem(15, legendary);

		return categories;
	}

	@EventHandler
	private void itemsSaved(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player))
			return;
		if (!(event.getInventory().getName().contains("Items")))
			return;

		Inventory inv = event.getInventory();
		
		if (inv.getName().contains("Common Items")) {
			Main.getPlugin(Main.class).itemsConfig.set("common", InventorySerializer.InventoryToString(commonItems));
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).itemsConfig, Main.getPlugin(Main.class).itemsFile);
		} else if (inv.getName().contains("Uncommon Items")) {
			Main.getPlugin(Main.class).itemsConfig.set("uncommon", InventorySerializer.InventoryToString(uncommonItems));
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).itemsConfig, Main.getPlugin(Main.class).itemsFile);
		} else if (inv.getName().contains("Rare Items")) {
			Main.getPlugin(Main.class).itemsConfig.set("rare", InventorySerializer.InventoryToString(rareItems));
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).itemsConfig, Main.getPlugin(Main.class).itemsFile);
		} else if (inv.getName().contains("Epic Items")) {
			Main.getPlugin(Main.class).itemsConfig.set("epic", InventorySerializer.InventoryToString(epicItems));
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).itemsConfig, Main.getPlugin(Main.class).itemsFile);
		} else if (inv.getName().contains("Legendary Items")) {
			Main.getPlugin(Main.class).itemsConfig.set("legendary", InventorySerializer.InventoryToString(legendaryItems));
			Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).itemsConfig, Main.getPlugin(Main.class).itemsFile);
		}
	}

	@EventHandler
	private void handleCategoriesClicks(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player))
			return;
		if (!(event.getInventory().getName().contains("Select Category")))
			return;

		Player p = (Player) event.getWhoClicked();
		event.setCancelled(true);
		int slot = event.getSlot();

		switch (slot) {
		case 11:
			p.openInventory(commonItems);
			break;
		case 12:
			p.openInventory(uncommonItems);
			break;
		case 13:
			p.openInventory(rareItems);
			break;
		case 14:
			p.openInventory(epicItems);
			break;
		case 15:
			p.openInventory(legendaryItems);
			break;
		}

	}

}
