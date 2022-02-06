package sixpack.xCheat.checks;



import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import sixpack.xCheat.purge;
import sixpack.xCheat.config.configLang;
import sixpack.xCheat.player.bannedPlayer;
import sixpack.xCheat.player.userManager;
import sixpack.xCheat.util.c;
import sixpack.xCheat.util.playerU;

public class Check implements Listener{
	
	private purge plugin = purge.getInstance();
	private bannedPlayer bP = new bannedPlayer();
	public String id;
	public String name;
	public CheckType type;
	public boolean enabled;
	public boolean punishable;
	public int maxV;

	
	public Check(String id, String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.enabled = enabled;
		this.punishable = punishable;
		this.maxV = maxV;
		
		Bukkit.getPluginManager().registerEvents(this,  plugin);
	}
	
	private userManager uMan;
	
	protected void flag(Player p, @Nullable String info) {
		configLang lang = new configLang();
		lang.setupLang();
			
		
		if(playerU.getPing(p) > 250) {
			return;
		}
		
		uMan = plugin.userMangerHashMap.get(p);
		int tempV = uMan.getViolations(name) + 1;
		uMan.setViolations(name, tempV);
		
		if(type == CheckType.COMBAT) {
			tempV = uMan.getViolations("Combat") + 1;
			uMan.setViolations("Combat", tempV);
		}
		if(type == CheckType.OTHER) {
			tempV = uMan.getViolations("Other") + 1;
			uMan.setViolations("Other", tempV);
		}
		if(type == CheckType.MOVEMENT) {
			tempV = uMan.getViolations("Movement") + 1;
			uMan.setViolations("Movement", tempV);
		}
		
		if(punishable == true && uMan.getViolations(name) >= maxV) {
			banEVNT(p);
		}
		
		
		if(info == null) {
    		String alert = lang.getLang().getString("alert.no-notes");
    		alert = alert.replaceAll("%player%", p.getName()).replaceAll("%hack%", name).replaceAll("%violations%", String.valueOf(uMan.getViolations(name)));
    				
    		
    		TextComponent jmsg = new TextComponent(c.f(alert));
    		jmsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(c.f("&4&lClick to teleport to " + p.getName())).create()));
    		jmsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName()));
    		
    		for(Player staff: Bukkit.getOnlinePlayers()) {	
    			userManager uMan = plugin.userMangerHashMap.get(staff);
    			if (uMan.isAlerts()) {
                        	staff.spigot().sendMessage(jmsg);  				
    			}
    		}
    		
    		
				return;
			}
    	
		String alert = lang.getLang().getString("alert.notes");
		alert = alert.replaceAll("%player%", p.getName()).replaceAll("%hack%", name)
				.replaceAll("%notes%", String.valueOf(info)).replaceAll("%violations%", String.valueOf(uMan.getViolations(name)));
		
		TextComponent jmsg = new TextComponent(c.f(alert));
		jmsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(c.f("&4&lClick to teleport to " + p.getName() + "\n&7" + info)).create()));
		jmsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName()));
		
		for(Player staff: Bukkit.getOnlinePlayers()) {				
			userManager uMan = plugin.userMangerHashMap.get(staff);
			if (uMan.isAlerts()) {
                    	staff.spigot().sendMessage(jmsg);  	
			}
		}
		

	}
	
	
	private void banEVNT(Player p) {
		configLang lang = new configLang();
		lang.setupLang();
		
		List<String> msg = lang.getLang().getStringList("ban.message");
		for(String line : msg) {
			Bukkit.broadcastMessage(c.f(line.replaceAll("%player%", p.getName())));
		}
		
		bP.addBanInfo(p);
		
		String cmd = plugin.getConfig().getString("Ban.Command");
		cmd = cmd.replaceAll("%player%", p.getName());	
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		return;
		
		
	}
	
	
}
