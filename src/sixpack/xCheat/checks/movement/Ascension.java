package sixpack.xCheat.checks.movement;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.mathU;
import sixpack.xCheat.util.playerU;



public class Ascension extends Check{

	public Ascension() {
		super("ascension1", "Ascension (1)", CheckType.MOVEMENT, true, false, 10);		
	}
	
	public Ascension(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("ascension1", name, type, enabled, punishable, maxV);
	}
	
	private Map<UUID, Map.Entry<Long, Double>> AscensionTicks = new HashMap<UUID, Map.Entry<Long, Double>>();

	@EventHandler
    public void CheckAscension(PlayerMoveEvent e) {
    	if(!enabled) {
    		return;
    	}
        Location a;
        Player player = e.getPlayer();
        if (e.getFrom().getY() >= e.getTo().getY()) {
            return;
        }

        if (player.getAllowFlight()) {
            return;
        }
        if (player.getVehicle() != null) {
            return;
        }

        long Time = System.currentTimeMillis();
        double TotalBlocks = 0.0;
        if (this.AscensionTicks.containsKey(player.getUniqueId())) {
            Time = this.AscensionTicks.get(player.getUniqueId()).getKey();
            TotalBlocks = this.AscensionTicks.get(player.getUniqueId()).getValue();
        }
        long MS = System.currentTimeMillis() - Time;
        double OffsetY = mathU.offset(mathU.getVerticalVector(e.getFrom().toVector()), mathU.getVerticalVector(e.getTo().toVector()));
        if (OffsetY > 0.0) {
            TotalBlocks += OffsetY;
        }
        if (playerU.blocksNear(player)) {
            TotalBlocks = 0.0;
        }
        if (playerU.blocksNear(a = player.getLocation().subtract(0.0, 1.0, 0.0))) {
            TotalBlocks = 0.0;
        }
        double Limit = 0.5;
        if (player.hasPotionEffect(PotionEffectType.JUMP)) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (!effect.getType().equals((Object)PotionEffectType.JUMP)) continue;
                int level = effect.getAmplifier() + 1;
                Limit += Math.pow((double)level + 4.2, 2.0) / 16.0;
                break;
            }
        }
        if (TotalBlocks > Limit) {
            if (MS > 500) {
                flag(player, null);
                Time = System.currentTimeMillis();
            }
        } else {
            Time = System.currentTimeMillis();
        }
        this.AscensionTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Long, Double>(Time, TotalBlocks));
    }
	
}
