package sixpack.xCheat.checks.combat.killaura;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.timeU;
import sixpack.xCheat.util.packets.events.PacketUseEntityEvent;

public class Killaura4 extends Check{

	public Killaura4() {
		super("killaura4", "KillAura (4)", CheckType.COMBAT, true, false, 10);
	}
	
	public Killaura4(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("killaura4", name, type, enabled, punishable, maxV);

	}
	


	
		    
}
