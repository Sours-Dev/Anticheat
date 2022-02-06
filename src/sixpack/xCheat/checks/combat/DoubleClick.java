package sixpack.xCheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


import com.comphenix.protocol.wrappers.EnumWrappers;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.packets.events.PacketUseEntityEvent;
public class DoubleClick extends Check{

	
	public DoubleClick() {
		super("doubleclick", "Double Click", CheckType.COMBAT, true, false, 10);
	}
	
	public DoubleClick(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("doubleclick", name, type, enabled, punishable, maxV);
	}
	
	 private Map<UUID, Long[]> LastMSCPS = new HashMap<UUID, Long[]>();
	 
	 @EventHandler
	    public void UseEntity(PacketUseEntityEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
	        if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK) {
	            return;
	        }
	        if (!(e.getAttacked() instanceof Player)) {
	            return;
	        }
	        Player damager = e.getAttacker();
	        Long first = 0l;
	        Long second =  0l;
	        if (this.LastMSCPS.containsKey(damager.getUniqueId())) {
	            first = this.LastMSCPS.get(damager.getUniqueId())[0];
	            second = this.LastMSCPS.get(damager.getUniqueId())[1];
	        }
	        if (first == 0) {
	            first = System.currentTimeMillis();
	        } else if (second == 0) {
	            second = System.currentTimeMillis();
	            first = System.currentTimeMillis() - first;
	        } else {
	            second = System.currentTimeMillis() - second;
	            if (first > 50 && second == 0) {
	               flag(damager, null);
	            }
	            first = 0l;
	            second = 0l;
	        }
	        this.LastMSCPS.put(damager.getUniqueId(), new Long[]{first, second});
	    }
	 
}
