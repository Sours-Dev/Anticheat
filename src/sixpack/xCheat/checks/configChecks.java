package sixpack.xCheat.checks;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;
import sixpack.xCheat.purge;

public class configChecks {




		// -----------------------------------
		// checks Config
		// -----------------------------------

		// Files & Configs

		public FileConfiguration checksCFG;
		public File checksFile;
		private purge plugin = purge.getInstance();
		// creating setup

		public void setupchecks() {

			if (!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}

			checksFile = new File(plugin.getDataFolder(), "checks.yml");

			if (!checksFile.exists()) {
				try {
					checksFile.createNewFile();
					Bukkit.getServer().getConsoleSender()
							.sendMessage(ChatColor.GREEN + "[Purge] checks.yml file has been created");
					checksCFG = YamlConfiguration.loadConfiguration(checksFile);

				} catch (IOException e) {
					Bukkit.getServer().getConsoleSender()
							.sendMessage(ChatColor.RED + "[Purge] Could not create checks.yml file");
				}
			}

			checksCFG = YamlConfiguration.loadConfiguration(checksFile);

		}

		public FileConfiguration getchecks() {
			return checksCFG;
		}

		public void savechecks() {
			try {
				checksCFG.save(checksFile);
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Purge] Could not save checks.yml file");
			}
		}

		public void reloadchecks() {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Purge] checks.yml file has been reloaded");
			checksCFG = YamlConfiguration.loadConfiguration(checksFile);
		}

		// -----------------------------------
		// end of checks Config
		// -----------------------------------
	
}
