package sixpack.xCheat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sixpack.xCheat.purge;
import sixpack.xCheat.config.configLang;
import sixpack.xCheat.events.inv.lookupEVNT;
import sixpack.xCheat.events.inv.menuEVNT;
import sixpack.xCheat.util.c;

public class menuCMD implements CommandExecutor{

	private purge plugin = purge.getInstance();
	private configLang lang = new configLang();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(c.f("&a[Purge] &fThis command is for players only."));
			return true;
		}
		
		Player p = (Player) sender;
		lang.setupLang();
		if(!p.hasPermission("purge.menu")) {
			p.sendMessage(c.f(lang.getLang().getString("no-perms")));
			return true;
		}
		

		
		p.sendMessage(c.f(lang.getLang().getString("menu.use")));
		menuEVNT menu = new menuEVNT();
		menu.openInvMenu(p);
		
		return true;
	}

}
