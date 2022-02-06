package sixpack.xCheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.comphenix.protocol.wrappers.EnumWrappers;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.timeU;
import sixpack.xCheat.util.packets.events.PacketUseEntityEvent;
public class Autoclicker1 extends Check{

	public Autoclicker1() {
		super("autoclicker1", "AutoClicker (1)", CheckType.COMBAT, true, false, 10);
	}
	
	public Autoclicker1(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("autoclicker1", name, type, enabled, punishable, maxV);
	}
	
	private Map<UUID, Map.Entry<Integer, Long>> attackTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	
	   @EventHandler
	    public void UseEntity(PacketUseEntityEvent e) {
		   
	    	if(!this.enabled) {
	    		return;
	    	}
		   
	        if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK) {
	            return;
	        }
	        if (!(e.getAttacked() instanceof Player)) {
	            return;
	        }
	        Player player = e.getAttacker();
	        int Count = 0;
	        long Time = System.currentTimeMillis();
	        if (this.attackTicks.containsKey(player.getUniqueId())) {
	            Count = this.attackTicks.get(player.getUniqueId()).getKey();
	            Time = this.attackTicks.get(player.getUniqueId()).getValue();
	        }
	        ++Count;
	        if (this.attackTicks.containsKey(player.getUniqueId()) && timeU.elapsed(Time, 1000)) {
	            if (Count > 18) {
	                flag(player, String.valueOf(Count) + " CPS");
	            }
	            Count = 0;
	            Time = timeU.nowlong();
	        }
	        this.attackTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	    }
	 
	 
}
