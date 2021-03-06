package me.mancy.dropparty.menus;

import me.mancy.dropparty.main.Main;
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

import java.util.ArrayList;
import java.util.List;

public class EditItems implements Listener {
	public static EditItems editItems;
	
	private Main plugin;

	public Inventory commonItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Common Items");
	public Inventory uncommonItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Uncommon Items");
	public Inventory rareItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Rare Items");
	public Inventory epicItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Epic Items");
	public Inventory legendaryItems = Bukkit.createInventory(null, 54, ChatColor.RED + "Legendary Items");

	
	public EditItems(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		editItems = this;
	}

	

	public Inventory openCategoriesMenu() {
		Inventory categories = Bukkit.getServer().createInventory(null, 27, "Select Category");

		ItemStack common = new ItemStack(Material.COAL_ORE);
		ItemMeta commonMeta = common.getItemMeta();
		List<String> commonLore = new ArrayList<>();
		commonMeta.setDisplayName(ChatColor.AQUA + "Common");
		commonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Common Item List");
		commonMeta.setLore(commonLore);
		common.setItemMeta(commonMeta);
		categories.setItem(11, common);

		ItemStack unCommon = new ItemStack(Material.IRON_BLOCK);
		ItemMeta unCommonMeta = unCommon.getItemMeta();
		List<String> unCommonLore = new ArrayList<>();
		unCommonMeta.setDisplayName(ChatColor.AQUA + "Uncommon");
		unCommonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Uncommon Item List");
		unCommonMeta.setLore(unCommonLore);
		unCommon.setItemMeta(unCommonMeta);
		categories.setItem(12, unCommon);

		ItemStack rare = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta rareMeta = rare.getItemMeta();
		List<String> rareLore = new ArrayList<>();
		rareMeta.setDisplayName(ChatColor.AQUA + "Rare");
		rareLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Rare Item List");
		rareMeta.setLore(rareLore);
		rare.setItemMeta(rareMeta);
		categories.setItem(13, rare);

		ItemStack epic = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta epicMeta = epic.getItemMeta();
		List<String> epicLore = new ArrayList<>();
		epicMeta.setDisplayName(ChatColor.AQUA + "Epic");
		epicLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Epic Item List");
		epicMeta.setLore(epicLore);
		epic.setItemMeta(epicMeta);
		categories.setItem(14, epic);

		ItemStack legendary = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta legendaryMeta = legendary.getItemMeta();
		List<String> legendaryLore = new ArrayList<>();
		legendaryMeta.setDisplayName(ChatColor.AQUA + "Legendary");
		legendaryLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Edit Legendary Item List");
		legendaryMeta.setLore(legendaryLore);
		legendary.setItemMeta(legendaryMeta);
		categories.setItem(15, legendary);

		ItemStack exit = new ItemStack(Material.BARRIER);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName(ChatColor.RED + "Exit");
		exit.setItemMeta(exitMeta);
		categories.setItem(26, exit);

		return categories;
	}

	@EventHandler
	private void editItemsBackButton(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player))
			return;
		if (event.getInventory().equals(commonItems) || event.getInventory().equals(uncommonItems) || event.getInventory().equals(rareItems) || event.getInventory().equals(epicItems) || event.getInventory().equals(legendaryItems)) {
			switch (event.getSlot()) {
				case 53: {
					event.getWhoClicked().openInventory(openCategoriesMenu());
					break;
				}
			}
		} else {
			return;
		}
	}

	@EventHandler
	private void itemsSaved(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player))
			return;
		if (!(event.getInventory().getName().contains("Items")))
			return;

		Inventory inv = event.getInventory();
		
		if (inv.getName().contains("Common Items")) {
			int x = 0;
			for (ItemStack i : inv.getContents()) {
				if (i != null && !(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back"))) {
					plugin.itemsConfig.set("common. " + x, i);
					plugin.saveCustomYml(plugin.itemsConfig, plugin.itemsFile);
					x++;
				}
			}

		} else if (inv.getName().contains("Uncommon Items")) {
			int x = 0;
			for (ItemStack i : inv.getContents()) {
				if (i != null && !(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back"))) {
					plugin.itemsConfig.set("uncommon. " + x, i);
					plugin.saveCustomYml(plugin.itemsConfig, plugin.itemsFile);
					x++;
				}
			}
		} else if (inv.getName().contains("Rare Items")) {
			int x = 0;
			for (ItemStack i : inv.getContents()) {
				if (i != null && !(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back"))) {
					plugin.itemsConfig.set("rare. " + x, i);
					plugin.saveCustomYml(plugin.itemsConfig, plugin.itemsFile);
					x++;
				}
			}
		} else if (inv.getName().contains("Epic Items")) {
			int x = 0;
			for (ItemStack i : inv.getContents()) {
				if (i != null && !(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back"))) {
					plugin.itemsConfig.set("epic. " + x, i);
					plugin.saveCustomYml(plugin.itemsConfig, plugin.itemsFile);
					x++;
				}
			}
		} else if (inv.getName().contains("Legendary Items")) {
			int x = 0;
			for (ItemStack i : inv.getContents()) {
				if (i != null && !(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back"))) {
					plugin.itemsConfig.set("legendary. " + x, i);
					plugin.saveCustomYml(plugin.itemsConfig, plugin.itemsFile);
					x++;
				}
			}
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



		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.RED + "Back");
		back.setItemMeta(backMeta);

		switch (slot) {
		case 11:
			commonItems.setItem(53, back);
			p.openInventory(commonItems);
			break;
		case 12:
			uncommonItems.setItem(53, back);
			p.openInventory(uncommonItems);
			break;
		case 13:
			rareItems.setItem(53, back);
			p.openInventory(rareItems);
			break;
		case 14:
			epicItems.setItem(53, back);
			p.openInventory(epicItems);
			break;
		case 15: {
			legendaryItems.setItem(53, back);
			p.openInventory(legendaryItems);
			break;
		}
		case 26: {
			p.closeInventory();
			break; }
		}

	}

}
