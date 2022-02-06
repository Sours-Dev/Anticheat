package sixpack.xCheat.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sixpack.xCheat.purge;
import sixpack.xCheat.config.configLang;
import sixpack.xCheat.events.inv.lookupEVNT;
import sixpack.xCheat.player.bannedPlayer;
import sixpack.xCheat.util.c;

public class lookupCMD implements CommandExecutor{

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
		if(!p.hasPermission("purge.lookup")) {
			p.sendMessage(c.f(lang.getLang().getString("no-perms")));
			return true;
		}
		
		
		if(args.length != 1) {
			p.sendMessage(c.f(lang.getLang().getString("lookup.usage")));
			return true;
		}
		
		if(Bukkit.getPlayer(args[0]) == null) {
			bannedPlayer bP = new bannedPlayer();
			OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
			if(bP.dateStored(op.getUniqueId())) {
				String msg = lang.getLang().getString("lookup.use");
				msg = msg.replaceAll("%player%", args[0]);
				p.sendMessage(c.f(msg));
				lookupEVNT loopupEVNT = new lookupEVNT();
				loopupEVNT.openInvBanned(p, args[0]);
				return true;
			}
			
			p.sendMessage(c.f(lang.getLang().getString("invalidPlayer")));
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);
		String msg = lang.getLang().getString("lookup.use");
		msg = msg.replaceAll("%player%", target.getName());
		p.sendMessage(c.f(msg));
		lookupEVNT loopupEVNT = new lookupEVNT();
		loopupEVNT.openInv(p, target);
		
		return true;
	}

}
