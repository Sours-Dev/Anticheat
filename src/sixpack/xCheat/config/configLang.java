package sixpack.xCheat.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


import net.md_5.bungee.api.ChatColor;
import sixpack.xCheat.purge;

public class configLang {

	// Default Lang
	private void defaultLang() {

		setupLang();
		getLang().set("no-perms", "&cNo Permissions.");
		getLang().set("invalidPlayer", "&cInvalid Player");
		getLang().set("ToggleAlerts", "&c[Purge] &fYou have toggled alerts %status%");
		getLang().set("alert.notes", "&c[Purge] %player% &fhas been detected with &c%hack%&7[%notes%] &c(%violations%)");
		getLang().set("alert.no-notes", "&c[Purge] %player% &fhas been detected with &c%hack% (%violations%)");
		getLang().set("alert.banned", "&c[Purge] %player% &fhas been banned for using &c%cheat%");
		getLang().set("lookup.usage", "&c[Purge] &fUsage: /lookup <player>");
		getLang().set("lookup.use", "&c[Purge]&f Looking up &c%player%");
		getLang().set("menu.use", "&c[Purge]&f Opening menu.");
		
		List<String> banMSG = new ArrayList<String>();
		banMSG.add("");
		banMSG.add(" &c&l%player% has been eliminated by the purge");
		banMSG.add("");
		
		getLang().set("ban.message", banMSG);
		
		saveLang();
	}



	// -----------------------------------
	// Lang Config
	// -----------------------------------

	// Files & Configs

	public FileConfiguration LangCFG;
	public File LangFile;
	private purge plugin = purge.getInstance();
	// creating setup

	public void setupLang() {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		LangFile = new File(plugin.getDataFolder(), "Langs.yml");

		if (!LangFile.exists()) {
			try {
				LangFile.createNewFile();
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "[xCheat] Lang.yml file has been created");
				LangCFG = YamlConfiguration.loadConfiguration(LangFile);

				defaultLang();

			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "[xCheat] Could not create Lang.yml file");
			}
		}

		LangCFG = YamlConfiguration.loadConfiguration(LangFile);

	}

	public FileConfiguration getLang() {
		return LangCFG;
	}

	public void saveLang() {
		try {
			LangCFG.save(LangFile);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[xCheat] Could not save Lang.yml file");
		}
	}

	public void reloadLang() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[xCheat] Lang.yml file has been reloaded");
		LangCFG = YamlConfiguration.loadConfiguration(LangFile);
	}

	// -----------------------------------
	// end of Lang Config
	// -----------------------------------

}
