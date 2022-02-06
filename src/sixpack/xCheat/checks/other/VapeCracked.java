package sixpack.xCheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;



public class VapeCracked extends Check{

	
	public VapeCracked() {
		super("vape", "Vape [Cracked]", CheckType.OTHER, true, true, 1);
	}
	
	public VapeCracked(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
        super("vape", name, type, enabled, punishable, maxV);
    }
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    	if(!enabled) {
    		return;
    	}
    	Player p = e.getPlayer();
        p.sendMessage("\u00a78 \u00a78 \u00a71 \u00a73 \u00a73 \u00a77 \u00a78 ");
    }
	
    
    public void onPluginMessageReceived(String string, Player p, byte[] arrby) {
    	
    	try {
    		String string2 = new String(arrby);
    	}catch(Exception exception) {
    		String string3 = "";
    	}
    	flag(p, "Cracked");
    }
    
}
