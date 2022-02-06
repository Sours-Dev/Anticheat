package sixpack.xCheat.checks.combat.killaura;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.playerU;
import sixpack.xCheat.util.timeU;
import sixpack.xCheat.util.packets.events.PacketUseEntityEvent;

public class Killaura3 extends Check{
	
	
	public static Map<UUID, Long> LastMS;
    public static Map<UUID, List<Long>> Clicks;
    public static Map<UUID, Map.Entry<Integer, Long>> ClickTicks;

    

	public Killaura3() {
		super("killaura3", "KillAura (3)", CheckType.COMBAT, true, false, 10);
	}
	
	public Killaura3(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("killaura3", name, type, enabled, punishable, maxV);
        LastMS = new HashMap<>();
        Clicks = new HashMap<>();
        ClickTicks = new HashMap<>();
	
	}
    


    @EventHandler
    public void onLog(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        LastMS.remove(uuid);
        Clicks.remove(uuid);
        ClickTicks.remove(uuid);
        
    }

    @EventHandler
    public void UseEntity(PacketUseEntityEvent e) {
    	if(!enabled) {
    		return;
    	}
        if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
                || !((e.getAttacked()) instanceof Player)) return;

        Player damager = e.getAttacker();
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (ClickTicks.containsKey(damager.getUniqueId())) {
            Count = ClickTicks.get(damager.getUniqueId()).getKey();
            Time = ClickTicks.get(damager.getUniqueId()).getValue();
        }
        if (LastMS.containsKey(damager.getUniqueId())) {
            long MS = timeU.nowlong() - LastMS.get(damager.getUniqueId());
            if (MS > 500L || MS < 5L) {
                LastMS.put(damager.getUniqueId(), timeU.nowlong());
                return;
            }
            if (Clicks.containsKey(damager.getUniqueId())) {
                List<Long> Clicks = this.Clicks.get(damager.getUniqueId());
                if (Clicks.size() == 10) {
                	this.Clicks.remove(damager.getUniqueId());
                    Collections.sort(Clicks);
                    final long Range = Clicks.get(Clicks.size() - 1) - Clicks.get(0);
                    if (Range < 30L) {
                        ++Count;
                        Time = System.currentTimeMillis();
                        
                    }
                } else {
                    Clicks.add(MS);
                    this.Clicks.put(damager.getUniqueId(), Clicks);
                }
            } else {
                final List<Long> Clicks = new ArrayList<>();
                Clicks.add(MS);
                this.Clicks.put(damager.getUniqueId(), Clicks);
            }
        }
        if (ClickTicks.containsKey(damager.getUniqueId()) && timeU.elapsed(Time, 5000L)) {
            Count = 0;
            Time = timeU.nowlong();
        }
        if ((Count > 2 && playerU.getPing(damager) < 100)
                || (Count > 4 && playerU.getPing(damager) <= 400)) {

            Count = 0;
            flag(damager, "Click Pattern");
            ClickTicks.remove(damager.getUniqueId());
        } else if (playerU.getPing(damager) > 400) {
            flag(damager, "(Clicker Pattern) High Ping");
        }
        LastMS.put(damager.getUniqueId(), timeU.nowlong());
        ClickTicks.put(damager.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
    }

}
