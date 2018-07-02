package me.mancy.dropparty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Main extends JavaPlugin {

	public File tokensFile = new File(this.getDataFolder() + "/tokens.yml");
	public FileConfiguration tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);

	public File itemsFile = new File(this.getDataFolder() + "/itemlists.yml");
	public FileConfiguration itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);

	public File dropChances = new File(this.getDataFolder() + "/dropchances.yml");
	public FileConfiguration dropChancesConfig = YamlConfiguration.loadConfiguration(dropChances);

	public File dropLocsFile = new File(this.getDataFolder() + "/droplocations.yml");
	public FileConfiguration dropLocsConfig = YamlConfiguration.loadConfiguration(dropLocsFile);

	@Override
	public void onEnable() {
		this.getCommand("drop").setExecutor(new DropCommand(this));
		new DropGUI(this);
		new TokenManager(this);
		new DropItems(this);
		new DropParty(this);
		loadTokens();
		loadLocations();
		loadItemLists();
		loadDropChanceLists();
		loadDropModifiers();
		loadCostsAndTime();
		DropGUI.fillDropGUI();
		DropParty.dropParty.isActiveDropParty = false;
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Alpha Drops] Plugin Enabled Successfully");
	}

	@Override
	public void onDisable() {
		saveTokens();
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Alpha Drops] Plugin Disabled Successfully");
	}

	private void loadDropChanceLists() {
		if (!dropChancesConfig.contains("common")) {
			List<Double> defaultCommon = new ArrayList<>();
			defaultCommon.add(20.0);
			defaultCommon.add(20.0);
			defaultCommon.add(20.0);
			defaultCommon.add(20.0);
			dropChancesConfig.set("common", defaultCommon);
			saveCustomYml(dropChancesConfig, dropChances);
		}
		for (Double d : dropChancesConfig.getDoubleList("common")) {
			DropParty.dropParty.commonChances.add(d);
		}

		if (!dropChancesConfig.contains("uncommon")) {
			List<Double> defaultUnCommon = new ArrayList<>();
			defaultUnCommon.add(20.0);
			defaultUnCommon.add(20.0);
			defaultUnCommon.add(20.0);
			defaultUnCommon.add(20.0);
			dropChancesConfig.set("uncommon", defaultUnCommon);
			saveCustomYml(dropChancesConfig, dropChances);
		}
		for (Double d : dropChancesConfig.getDoubleList("uncommon")) {
			DropParty.dropParty.uncommonChances.add(d);
		}

		if (!dropChancesConfig.contains("rare")) {
			List<Double> defaultRare = new ArrayList<>();
			defaultRare.add(20.0);
			defaultRare.add(20.0);
			defaultRare.add(20.0);
			defaultRare.add(20.0);
			dropChancesConfig.set("rare", defaultRare);
			saveCustomYml(dropChancesConfig, dropChances);

		}
		for (Double d : dropChancesConfig.getDoubleList("rare")) {
			DropParty.dropParty.rareChances.add(d);
		}

		if (!dropChancesConfig.contains("epic")) {
			List<Double> defaultEpic = new ArrayList<>();
			defaultEpic.add(20.0);
			defaultEpic.add(20.0);
			defaultEpic.add(20.0);
			defaultEpic.add(20.0);
			dropChancesConfig.set("epic", defaultEpic);
			saveCustomYml(dropChancesConfig, dropChances);

		}
		for (Double d : dropChancesConfig.getDoubleList("epic")) {
			DropParty.dropParty.epicChances.add(d);
		}

		if (!dropChancesConfig.contains("legendary")) {
			List<Double> defaultLegendary = new ArrayList<>();
			defaultLegendary.add(20.0);
			defaultLegendary.add(20.0);
			defaultLegendary.add(20.0);
			defaultLegendary.add(20.0);
			dropChancesConfig.set("legendary", defaultLegendary);
			saveCustomYml(dropChancesConfig, dropChances);

		}
		for (Double d : dropChancesConfig.getDoubleList("legendary")) {
			DropParty.dropParty.legendaryChances.add(d);
		}

	}

	private void loadCostsAndTime() {
		if (tokensConfig.contains("tierOneCost")) {
			DropGUI.tierOneCost = tokensConfig.getInt("tierOneCost");
		} else {
			tokensConfig.set("tierOneCost", 1);
			saveCustomYml(tokensConfig, tokensFile);
			DropGUI.tierOneCost = tokensConfig.getInt("tierOneCost");
		}

		if (tokensConfig.contains("tierTwoCost")) {
			DropGUI.tierTwoCost = tokensConfig.getInt("tierTwoCost");
		} else {
			tokensConfig.set("tierTwoCost", 1);
			saveCustomYml(tokensConfig, tokensFile);
			DropGUI.tierTwoCost = tokensConfig.getInt("tierTwoCost");
		}

		if (tokensConfig.contains("tierThreeCost")) {
			DropGUI.tierThreeCost = tokensConfig.getInt("tierThreeCost");
		} else {
			tokensConfig.set("tierThreeCost", 1);
			saveCustomYml(tokensConfig, tokensFile);
			DropGUI.tierThreeCost = tokensConfig.getInt("tierThreeCost");
		}

		if (tokensConfig.contains("tierFourCost")) {
			DropGUI.tierFourCost = tokensConfig.getInt("tierFourCost");
		} else {
			tokensConfig.set("tierFourCost", 1);
			saveCustomYml(tokensConfig, tokensFile);
			DropGUI.tierFourCost = tokensConfig.getInt("tierFourCost");
		}

		if (dropLocsConfig.contains("countdowntime")) {
			DropGUI.countdownTime = dropLocsConfig.getInt("countdowntime");
		} else {
			dropLocsConfig.set("countdowntime", 10);
			saveCustomYml(dropLocsConfig, dropLocsFile);
			DropGUI.countdownTime = dropLocsConfig.getInt("countdowntime");
		}
	}

	private void loadItemLists() {
		if (itemsConfig.contains("common")) {
			if (InventorySerializer.StringToInventory(itemsConfig.getString("common")) != null) {
				DropItems.dropItems.commonItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("common")).getContents());
			}
		}
		if (itemsConfig.contains("uncommon")) {

			if (InventorySerializer.StringToInventory(itemsConfig.getString("uncommon")) != null) {
				DropItems.dropItems.uncommonItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("uncommon")).getContents());
			}
		}
		if (itemsConfig.contains("rare")) {

			if (InventorySerializer.StringToInventory(itemsConfig.getString("rare")) != null) {
				DropItems.dropItems.rareItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("rare")).getContents());
			}
		}

		if (itemsConfig.contains("epic")) {
			if (InventorySerializer.StringToInventory(itemsConfig.getString("epic")) != null) {
				DropItems.dropItems.epicItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("epic")).getContents());
			}
		}
		if (itemsConfig.contains("legendary")) {


			if (InventorySerializer.StringToInventory(itemsConfig.getString("legendary")) != null) {
				DropItems.dropItems.legendaryItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("legendary")).getContents());
			}
		}

	}

	private void saveTokens() {
		List<String> tokensOneData = new ArrayList<>();
		for(UUID uuid: TokenManager.tokensOne.keySet()) {

			String data = uuid.toString() + ":" + TokenManager.tokensOne.get(uuid);
			tokensOneData.add(data);

		}

		tokensConfig.set("tokensone", tokensOneData);
		saveCustomYml(tokensConfig, tokensFile);

		List<String> tokensTwoData = new ArrayList<>();
		for(UUID uuid: TokenManager.tokensTwo.keySet()) {

			String data = uuid.toString() + ":" + TokenManager.tokensTwo.get(uuid);
			tokensTwoData.add(data);

		}

		tokensConfig.set("tokenstwo", tokensTwoData);
		saveCustomYml(tokensConfig, tokensFile);

		List<String> tokensThreeData = new ArrayList<>();
		for(UUID uuid: TokenManager.tokensThree.keySet()) {

			String data = uuid.toString() + ":" + TokenManager.tokensThree.get(uuid);
			tokensThreeData.add(data);

		}

		tokensConfig.set("tokensthree", tokensThreeData);
		saveCustomYml(tokensConfig, tokensFile);

		List<String> tokensFourData = new ArrayList<>();
		for(UUID uuid: TokenManager.tokensFour.keySet()) {

			String data = uuid.toString() + ":" + TokenManager.tokensFour.get(uuid);
			tokensFourData.add(data);

		}

		tokensConfig.set("tokensfour", tokensFourData);
		saveCustomYml(tokensConfig, tokensFile);

	}

	private void loadTokens() {
		for(String rawData : tokensConfig.getStringList("tokensone")) {

			String[] raw = rawData.split(":");
			TokenManager.tokensOne.put(UUID.fromString(raw[0]), Integer.valueOf(raw[1]));
		}
		for(String rawData : tokensConfig.getStringList("tokenstwo")) {

			String[] raw = rawData.split(":");
			TokenManager.tokensTwo.put(UUID.fromString(raw[0]), Integer.valueOf(raw[1]));
		}
		for(String rawData : tokensConfig.getStringList("tokensthree")) {

			String[] raw = rawData.split(":");
			TokenManager.tokensThree.put(UUID.fromString(raw[0]), Integer.valueOf(raw[1]));
		}
		for(String rawData : tokensConfig.getStringList("tokensfour")) {

			String[] raw = rawData.split(":");
			TokenManager.tokensFour.put(UUID.fromString(raw[0]), Integer.valueOf(raw[1]));
		}
	}

	private void loadLocations() {
		DropParty.dropParty.numDropLocs = dropLocsConfig.getInt("Number Of Drop Locations");
		if (DropParty.dropParty.numDropLocs > 0) {

			for (int x = 1; x <= DropParty.dropParty.numDropLocs; x++) {

				double xCoord = dropLocsConfig.getDouble("Drop Locations." + x + " X");
				double yCoord = dropLocsConfig.getDouble("Drop Locations." + x + " Y");
				double zCoord = dropLocsConfig.getDouble("Drop Locations." + x + " Z");
				String worldName = dropLocsConfig.getString("Drop Locations." + x + " World");

				Location loc = new Location(Bukkit.getServer().getWorld(worldName), xCoord, yCoord, zCoord);
				DropParty.dropParty.dropLocations.put(x, loc);



			}
		}
	}

	private void loadDropModifiers() {
		if (dropLocsConfig.contains("height")) {
			DropParty.dropParty.heightToDrop = dropLocsConfig.getDouble("height");
		} else {
			DropParty.dropParty.heightToDrop = 0.0;
			dropLocsConfig.set("height", DropParty.dropParty.heightToDrop);
			saveCustomYml(dropLocsConfig, dropLocsFile);
		}
		if (dropLocsConfig.contains("radius")) {
			DropParty.dropParty.radiusToDrop = dropLocsConfig.getDouble("radius");
		} else {
			DropParty.dropParty.radiusToDrop = 0.0;
			dropLocsConfig.set("radius", DropParty.dropParty.radiusToDrop);
			saveCustomYml(dropLocsConfig, dropLocsFile);
		}

	}

	public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		try {
			ymlConfig.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD.toString() + "P" + ChatColor.RED + ChatColor.BOLD.toString() + "A" + ChatColor.DARK_GRAY + ":" + ChatColor.GRAY + "Party" + ChatColor.DARK_GRAY + "]";
		Player p = (Player) sender;

		if (args.length == 0) {

			if (label.equalsIgnoreCase("tokens")) {
				p.sendMessage(prefix + ChatColor.RED + " Tier One Tokens: " + ChatColor.GREEN + TokenManager.getTokens(p, 1));
				p.sendMessage(prefix + ChatColor.RED + " Tier Two Tokens: " + ChatColor.GREEN + TokenManager.getTokens(p, 2));
				p.sendMessage(prefix + ChatColor.RED + " Tier Three Tokens: " + ChatColor.GREEN + TokenManager.getTokens(p, 3));
				p.sendMessage(prefix + ChatColor.RED + " Tier Four Tokens: " + ChatColor.GREEN + TokenManager.getTokens(p, 4));
				return true;
			}
		} else if (args.length == 4) {
			Player online = Bukkit.getServer().getPlayer(args[0]);

			if (online == null) {
				p.sendMessage(prefix + ChatColor.RED + " Player Not Found!");
				return false;
			}
			if (p.hasPermission("dropparty.edittokens") || p.hasPermission("dropparty.*")) {
				if (args[1].equalsIgnoreCase("add")) {
					int tier = Integer.parseInt(args[2]);
					int amount = Integer.parseInt(args[3]);
					TokenManager.addTokens(online, tier, amount);
					online.sendMessage(prefix + ChatColor.GRAY + " Tier " + tier + " Tokens Balance Changed To: " + TokenManager.getTokens(online, tier));
					p.sendMessage(prefix + " " + ChatColor.GREEN + online.getDisplayName() + "'s" + ChatColor.GRAY + " Tier " + tier + " Tokens Changed To: " + TokenManager.getTokens(p, tier));
					return true;
				} else if (args[1].equalsIgnoreCase("remove")) {
					int tier = Integer.parseInt(args[2]);
					int amount = Integer.parseInt(args[3]);
					TokenManager.subtractTokens(online, tier, amount);
					online.sendMessage(prefix + " " + ChatColor.GRAY + " Tier " + tier + " Tokens Balance Changed To: " + TokenManager.getTokens(online, tier));
					p.sendMessage(prefix + ChatColor.GREEN + online.getDisplayName() + "'s" + ChatColor.GRAY + " Tier " + tier + " Tokens Changed To: " + TokenManager.getTokens(p, tier));
					return true;
				} else if (args[1].equalsIgnoreCase("set")) {
					int tier = Integer.parseInt(args[2]);
					int amount = Integer.parseInt(args[3]);
					TokenManager.setTokens(online, tier, amount);
					online.sendMessage(prefix + ChatColor.GRAY + " Tier " + tier + " Tokens Balance Changed To: " + TokenManager.getTokens(online, tier));
					p.sendMessage(prefix + " " + ChatColor.GREEN + online.getDisplayName() + "'s" + ChatColor.GRAY + " Tier " + tier + " Tokens Changed To: " + TokenManager.getTokens(p, tier));
					return true;
				} else {
					p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drop help" + ChatColor.GRAY + " To View Available Commands");
					return false;
				}
			} else {
				p.sendMessage(prefix + ChatColor.GRAY + " Sorry, you don't have permission to do this!");
				return false;
			}


		} else {
			p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drop help" + ChatColor.GRAY + " To View Available Commands");
			return false;
		}

		return false;
	}

}
