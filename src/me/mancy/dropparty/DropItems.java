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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropItems implements Listener {

	private Main plugin;
	
	private enum ItemType {COMMON, UNCOMMON, RARE, EPIC, LEGENDARY};
	
	public DropItems(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	
	public Inventory openCategoriesMenu() {
		Inventory categories = Bukkit.getServer().createInventory(null, 64, "Edit Items");
		
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
		unCommonLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click Edit Uncommon Item List");
		unCommonMeta.setLore(unCommonLore);
		unCommon.setItemMeta(unCommonMeta);
		categories.setItem(12, unCommon);

		ItemStack rare = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta rareMeta = rare.getItemMeta();
		List<String> rareLore = new ArrayList<String>();
		rareMeta.setDisplayName(ChatColor.AQUA + "Rare");
		rareLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click Edit Rare Item List");
		rareMeta.setLore(rareLore);
		rare.setItemMeta(rareMeta);
		categories.setItem(13, rare);

		ItemStack epic = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta epicMeta = epic.getItemMeta();
		List<String> epicLore = new ArrayList<String>();
		epicMeta.setDisplayName(ChatColor.AQUA + "Epic");
		epicLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click Edit Epic Item List");
		epicMeta.setLore(epicLore);
		epic.setItemMeta(epicMeta);
		categories.setItem(14, epic);
		
		ItemStack legendary = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta legendaryMeta = legendary.getItemMeta();
		List<String> legendaryLore = new ArrayList<String>();
		legendaryMeta.setDisplayName(ChatColor.AQUA + "Legendary");
		legendaryLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click Edit Legendary Item List");
		legendaryMeta.setLore(legendaryLore);
		legendary.setItemMeta(legendaryMeta);
		categories.setItem(15, legendary);
		
		return categories;
	}
	
	public void editItems(ItemType type) {
		
	}
	
	@EventHandler
	private void handleCategoriesClicks(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player p = (Player) event.getWhoClicked();
		
		int slot = event.getSlot();
		
		switch (slot) {
		case 11:
			
		case 12:
		case 13:
		case 14:
		case 15:
		}
		
	}
	
	
	
}
