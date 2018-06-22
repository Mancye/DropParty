package me.mancy.dropparty;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	File tokensFile = new File(this.getDataFolder() + "/tokens.yml");
	FileConfiguration tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);

	public File dropLocsFile = new File(this.getDataFolder() + "/droplocations.yml");
	public FileConfiguration dropLocsConfig = YamlConfiguration.loadConfiguration(dropLocsFile);

	@Override
	public void onEnable() {
		this.getCommand("drop").setExecutor(new DropCommand());
		new DropGUI(this);
		
		loadTokens();
		loadLocations();
		
		System.out.println(ChatColor.GREEN + "DropPartyAlpha Enabled!");
	}

	@Override
	public void onDisable() {
		saveTokens();
		
		System.out.println(ChatColor.RED + "DropPartyAlpha Disabled!");
	}

	public void saveTokens() {
		List<String> tokens = getConfig().getStringList("tokens");

		for (UUID player : TokenManager.tokens.keySet()) {

			if (!(tokens.contains(player.toString()))) {
				tokens.add(player.toString() + ":" + TokenManager.tokens.get(player));
			} else {
				tokens.remove(player.toString());
				tokens.add(player.toString() + ":" + TokenManager.tokens.get(player));
			}

		}
		tokensConfig.set("tokens", tokens);
		saveCustomYml(tokensConfig, tokensFile);
	}

	public void loadTokens() {
		List<String> tokens = getConfig().getStringList("tokens");

		for (String amount : tokens) {

			String[] words = amount.split(":");
			TokenManager.tokens.put(UUID.fromString(words[0]), Integer.parseInt(words[1]));

		}
		tokensConfig.set("tokens", tokens);
		saveCustomYml(tokensConfig, tokensFile);
	}

	private void loadLocations() {
		DropParty.numDropLocs = dropLocsConfig.getInt("Number Of Drop Locations");
		if (DropParty.numDropLocs > 0) {

			for (int x = 0; x <= DropParty.numDropLocs; x++) {

				double xCoord = dropLocsConfig.getDouble("Drop Locations." + x + ". X");
				double yCoord = dropLocsConfig.getDouble("Drop Locations." + x + ". Y");
				double zCoord = dropLocsConfig.getDouble("Drop Locations." + x + ". Z");
				String worldName = dropLocsConfig.getString("Drop Locations." + x + ". World");
				
				Location loc = new Location(Bukkit.getServer().getWorld(worldName), xCoord, yCoord, zCoord);
				DropParty.dropLocations.put(x, loc);
				
				dropLocsConfig.set("Drop Locations." + x + ". X", xCoord);
				dropLocsConfig.set("Drop Locations." + x + ". Y", yCoord);
				dropLocsConfig.set("Drop Locations." + x + ". Z", zCoord);
				dropLocsConfig.set("Drop Locations." + x + ". World", worldName);
				saveCustomYml(dropLocsConfig, dropLocsFile);
			}
		} else {
			dropLocsConfig.set("Drop Locations." + 0 + ". X", 0);
			dropLocsConfig.set("Drop Locations." + 0 + ". Y", 0);
			dropLocsConfig.set("Drop Locations." + 0 + ". Z", 0);
			dropLocsConfig.set("Drop Locations." + 0 + ". World", "default");
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

	public boolean onCommand(Command command, CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("testloc")) {
			for (Location loc : DropParty.dropLocations.values()) {
				p.sendMessage(loc.getX() + " " + loc.getY() + " " + loc.getZ());
				return true;
			}
		}

		return false;
	}

}
