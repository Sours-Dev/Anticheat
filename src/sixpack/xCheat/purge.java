package sixpack.xCheat;

import java.sql.Connection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckManager;
import sixpack.xCheat.commands.alertsCMD;
import sixpack.xCheat.commands.lookupCMD;
import sixpack.xCheat.commands.menuCMD;
import sixpack.xCheat.config.configLang;
import sixpack.xCheat.events.inv.lookupEVNT;
import sixpack.xCheat.events.inv.menuEVNT;
import sixpack.xCheat.player.DataManager;
import sixpack.xCheat.player.DataPlayer;
import sixpack.xCheat.player.userEVNT;
import sixpack.xCheat.player.userManager;
import sixpack.xCheat.util.latencyU;
import sixpack.xCheat.util.packets.packet;

public class purge extends JavaPlugin implements Listener{

	public HashMap<Player, userManager> userMangerHashMap;
	private static purge instance;
	private configLang lang;
	private  CheckManager checkManager;
	private packet packet;
	private DataManager dataManager;
	
	
    public purge() {
    	instance = this;
    }
	
	public void onEnable() {
		Events();
		Commands();
		instance = this;
		dataManager = new DataManager();
		checkManager = new CheckManager();
		loadLang();
		this.userMangerHashMap = new HashMap<>();
		for(Player players : Bukkit.getOnlinePlayers()) {
			userEVNT user = new userEVNT();
			user.setUp(players);
		}
		this.packet = new packet(this);
		this.RegisterListener(this);
		this.saveDefaultConfig();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			getDataManager().add(player);
		}
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Purge] Purge has been enabled");
	}
	
	public void onDisable() {
		loadLang();
	
		this.userMangerHashMap.clear();
		
		this.saveDefaultConfig();	
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Purge] Purge has been disabled");
	}
	
	public static purge getInstance() {
		return instance;
	}
	
	public CheckManager getCheckManager() {
		return checkManager;
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}
	
	
	
	private void loadLang() {
		lang = new configLang();
		lang.setupLang();
		lang.saveLang();
	}

	
	private void RegisterListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }	
	
	private void Commands() {
		getCommand("alerts").setExecutor(new alertsCMD());
		getCommand("lookup").setExecutor(new lookupCMD());
		getCommand("menu").setExecutor(new menuCMD());
	}
	
	
	private void Events() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new userEVNT(), this);
		pm.registerEvents(new lookupEVNT(), this);
		pm.registerEvents(new menuEVNT(), this);
		pm.registerEvents(new latencyU(this), this);

	}
	
}
