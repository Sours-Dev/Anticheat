package sixpack.xCheat.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.comphenix.protocol.events.PacketEvent;



import sixpack.xCheat.purge;


public class latencyU implements Listener{
	
    public static Map<UUID, Map.Entry<Integer, Long>> packetTicks;
    public static Map<UUID, Long> lastPacket;
    private static Map<UUID, Integer> packets;
    public List<UUID> blacklist;
    private purge plugin = purge.getInstance();

    public latencyU(purge plugin) {
        this.plugin = plugin;

        packetTicks = new HashMap<>();
        lastPacket = new HashMap<>();
        blacklist = new ArrayList<>();
        packets = new HashMap<>();
    }

    public static Integer getLag(Player player) {
        if (packets.containsKey(player.getUniqueId())) {
            return packets.get(player.getUniqueId());
        }
        return 0;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        this.blacklist.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        packetTicks.remove(e.getPlayer().getUniqueId());
        lastPacket.remove(e.getPlayer().getUniqueId());
        blacklist.remove(e.getPlayer().getUniqueId());
        packets.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
        this.blacklist.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerRespawn(PlayerRespawnEvent event) {
        this.blacklist.add(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PacketPlayer(PacketEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (player.getAllowFlight() == true) return;
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (latencyU.packetTicks.containsKey(player.getUniqueId())) {
            Count = latencyU.packetTicks.get(player.getUniqueId()).getKey();
            Time = latencyU.packetTicks.get(player.getUniqueId()).getValue();
        }
        if (latencyU.lastPacket.containsKey(player.getUniqueId())) {
            long MS = System.currentTimeMillis() - latencyU.lastPacket.get(player.getUniqueId());
            if (MS >= 100L) {
                this.blacklist.add(player.getUniqueId());
            } else if ((MS > 1L)) {
                this.blacklist.remove(player.getUniqueId());
            }
        }
        if (!this.blacklist.contains(player.getUniqueId())) {
            Count++;
            if ((latencyU.packetTicks.containsKey(player.getUniqueId())) && (timeU.elapsed(Time, 1000L))) {
                packets.put(player.getUniqueId(), Count);
                Count = 0;
                Time = timeU.nowlong();
            }
        }
        latencyU.packetTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
        latencyU.lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
    }

}
