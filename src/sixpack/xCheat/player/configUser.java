package sixpack.xCheat.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import sixpack.xCheat.purge;





public class configUser {

	private purge plugin = purge.getInstance();
	
	// -----------------------------------
	//		Data Config
	// -----------------------------------
	
	// Files & Configs 
	
	public FileConfiguration DataCFG;
	public File DataFile;

	
	// creating setup
	
	
	
	public void setupData(UUID uuid) {
		
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		DataFile = new File(plugin.getDataFolder() + File.separator + "userData", uuid + ".yml");
		
		if (!DataFile.exists()) {
			try {
				DataFile.createNewFile();
				DataCFG = YamlConfiguration.loadConfiguration(DataFile);
			

			}catch(IOException e) {

			}
		}
		
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
		
		
	}
	
	

	
	public FileConfiguration getData(UUID uuid) {
		return DataCFG;
	}
	
	public void saveData(UUID uuid) {
		try{
			DataCFG.save(DataFile);
		}catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Purge] Could not save Data.yml file");
		}
	}
	
	public void reloadData() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[Purge] Data.yml file has been reloaded");
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
	}
	
	public void deleteData(UUID uuid) {
		DataFile = new File(plugin.getDataFolder() + File.separator + "userData", uuid + ".yml");
		if (DataFile.exists()) {
			try {
				DataFile.delete();
			

			}catch(Exception e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Purge] Could not find file for " + uuid);
			}
		}
		
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
	}
	
	public boolean dateExists(UUID uuid) {
		DataFile = new File(plugin.getDataFolder() + File.separator + "userData", uuid + ".yml");
		if(DataFile.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
		// -----------------------------------
		//		end of Data Config
		// -----------------------------------
	

	
}
