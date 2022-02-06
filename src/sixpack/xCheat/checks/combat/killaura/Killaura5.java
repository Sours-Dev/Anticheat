package sixpack.xCheat.checks.combat.killaura;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.packets.events.PacketKillauraEvent;
import sixpack.xCheat.util.packets.events.PacketPlayerType;

public class Killaura5 extends Check{

	public Killaura5() {
		super("killaura5", "KillAura (5)", CheckType.COMBAT, true, false, 10);
	}
	
	public Killaura5(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("killaura5", name, type, enabled, punishable, maxV);
       
	}

    
    
}
