package sixpack.xCheat.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



import net.md_5.bungee.api.ChatColor;
import sixpack.xCheat.purge;
import sixpack.xCheat.config.configLang;
import sixpack.xCheat.player.userManager;
import sixpack.xCheat.util.c;

public class alertsCMD implements CommandExecutor{

	private purge plugin = purge.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(c.f("&a[Purge] &fThis command is for players only."));
			return true;
		}
		
		Player p = (Player) sender;
		configLang lang = new configLang();
		lang.setupLang();
		if(!p.hasPermission("purge.alerts")) {
			p.sendMessage(c.f(lang.getLang().getString("no-perms")));
			return true;
		}
		
		

		userManager uMan = plugin.userMangerHashMap.get(p);
		String msg = lang.getLang().getString("ToggleAlerts");
		String status = "&cOFF";
		if(uMan.isAlerts()) {
			msg = msg.replaceAll("%status%", status);
			uMan.setAlerts(false);
			p.sendMessage(c.f(msg));
			return true;
		}
		uMan.setAlerts(true);
		status = "&aON";
		msg = msg.replaceAll("%status%", status);
		p.sendMessage(c.f(msg));	
		return true;
	}
}
