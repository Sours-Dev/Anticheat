package sixpack.xCheat.checks.other;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;




public class TabComplete extends Check{

	

	
	
	public TabComplete() {
		super ("tab", "Tab Complete", CheckType.OTHER, true, false, 999);	
	}
	
	public TabComplete(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
        super("tab", name, type, enabled, punishable, maxV);
    }
	
	@EventHandler
	public void TabComplete(PlayerChatTabCompleteEvent e) {
    	if(!enabled) {
    		return;
    	}
        String[] Args = e.getChatMessage().split(" ");
        Player Player2 = e.getPlayer();
        if(Args.length == 0) {
        	return;
        }
        
        if (Args[0].startsWith(".") && Args[0].substring(1, 2).equalsIgnoreCase("/")) {
            return;
        }
        
        
        if ((Args[0].startsWith(".") || Args[0].startsWith("-") || Args[0].startsWith("#") || Args[0].startsWith("*"))) {
          flag(Player2, "Message: " + e.getChatMessage()); 
        }
    }
        
	}
	

