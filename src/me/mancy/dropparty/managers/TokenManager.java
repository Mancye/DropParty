package me.mancy.dropparty.managers;

import me.mancy.dropparty.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenManager implements Listener, Runnable {

	private Main plugin;
	
	public static Map<UUID, Integer> tokensOne = new HashMap<>();
	public static Map<UUID, Integer> tokensTwo = new HashMap<>();
	public static Map<UUID, Integer> tokensThree = new HashMap<>();
	public static Map<UUID, Integer> tokensFour = new HashMap<>();

	public TokenManager(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	private void setDefaultBalance(PlayerJoinEvent event) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				Player p = event.getPlayer();
				
				if (!tokensOne.containsKey(p.getUniqueId())) {
				  setTokens(p, 1, 0);
				}
				if (!(tokensTwo.containsKey(p.getUniqueId()))) {
					setTokens(p, 2, 0);
				}
				if (!(tokensThree.containsKey(p.getUniqueId()))) {
					setTokens(p, 3, 0);
				}
				if (!(tokensFour.containsKey(p.getUniqueId()))) {
					setTokens(p, 4, 0);
				}
			}
			
		}, 20L);
	}
	
	public static int getTokens(Player p, int tier) {
		switch (tier) {
			case 1: {
				return tokensOne.get(p.getUniqueId());
			}
			case 2: {
				return tokensTwo.get(p.getUniqueId());
			}
			case 3: {
				return tokensThree.get(p.getUniqueId());
			}
			case 4: {
				return tokensFour.get(p.getUniqueId());
			}
		}
		return -1;
	}
	
	public static void addTokens(Player p, int tier, int amount) {
		switch (tier) {
			case 1: {
				tokensOne.put(p.getUniqueId(), getTokens(p, 1) + amount);
				break;
			}
			case 2: {
				tokensTwo.put(p.getUniqueId(), getTokens(p, 2) + amount);
				break;
			}
			case 3: {
				tokensThree.put(p.getUniqueId(), getTokens(p, 3) + amount);
				break;
			}
			case 4: {
				tokensFour.put(p.getUniqueId(), getTokens(p, 4) + amount);
				break;
			}
		}

	}
	
	public static void setTokens(Player p, int tier, int amount) {
		switch (tier) {
			case 1: {
				tokensOne.put(p.getUniqueId(), amount);
				break;
			}
			case 2: {
				tokensTwo.put(p.getUniqueId(), amount);
				break;
			}
			case 3: {
				tokensThree.put(p.getUniqueId(), amount);
				break;
			}
			case 4: {
				tokensFour.put(p.getUniqueId(), amount);
				break;
			}
		}

	}
	
	public static void subtractTokens(Player p, int tier, int amount) {
		switch (tier) {
			case 1: {
				tokensOne.put(p.getUniqueId(), getTokens(p, 1) - amount);
				break;
			}
			case 2: {
				tokensTwo.put(p.getUniqueId(), getTokens(p, 2) - amount);
				break;
			}
			case 3: {
				tokensThree.put(p.getUniqueId(), getTokens(p, 3) - amount);
				break;
			}
			case 4: {
				tokensFour.put(p.getUniqueId(), getTokens(p, 4) - amount);
				break;
			}
		}


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
