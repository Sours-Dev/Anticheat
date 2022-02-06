package sixpack.xCheat.util.packets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import sixpack.xCheat.purge;
import sixpack.xCheat.util.packets.events.PacketUseEntityEvent;

public class packet {
    private static final PacketType[] ENTITY_PACKETS = new PacketType[]{PacketType.Play.Server.SPAWN_ENTITY_LIVING,
            PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.ENTITY_METADATA};
    public purge anticheat;
    public Map<UUID, Integer> movePackets;
    private HashSet<EntityType> enabled;

    public packet(purge anticheat) {
    	super();
        this.anticheat = anticheat;
        enabled = new HashSet<>();
        enabled.add(EntityType.valueOf("PLAYER"));
        movePackets = new HashMap<>();

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.anticheat,
                PacketType.Play.Client.USE_ENTITY) {
            public void onPacketReceiving(final PacketEvent event) {
                final PacketContainer packet = event.getPacket();
                final Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                try {
                    Object playEntity = getNMSClass("PacketPlayInUseEntity");
                    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                    if (version.contains("1_7")) {
                        if (packet.getHandle() == playEntity) {
                            if (playEntity.getClass().getMethod("c") == null) {
                                return;
                            }
                        }
                    } else {
                        if (packet.getHandle() == playEntity) {
                            if (playEntity.getClass().getMethod("a") == null) {
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EntityUseAction type;
                try {
                    type = packet.getEntityUseActions().read(0);
                } catch (Exception ex) {
                    return;
                }

                Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);

                if (entity == null) {
                    return;
                }

                Bukkit.getServer().getPluginManager().callEvent(new PacketUseEntityEvent(type, player, entity));

            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(anticheat, ENTITY_PACKETS) {

            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Entity e = packet.getEntityModifier(event).read(0);
                if (e instanceof LivingEntity && enabled.contains(e.getType())
                        && packet.getWatchableCollectionModifier().read(0) != null
                        && e.getUniqueId() != event.getPlayer().getUniqueId()) {
                    packet = packet.deepClone();
                    event.setPacket(packet);
                    if (event.getPacket().getType() == PacketType.Play.Server.ENTITY_METADATA) {
                        WrappedDataWatcher watcher = new WrappedDataWatcher(
                                packet.getWatchableCollectionModifier().read(0));
                        this.processDataWatcher(watcher);
                        packet.getWatchableCollectionModifier().write(0,
                                watcher.getWatchableObjects());
                    }
                }
            }

            private void processDataWatcher(WrappedDataWatcher watcher) {
                if (watcher != null && watcher.getObject(6) != null && watcher.getFloat(6) != 0.0F) {
                    watcher.setObject(6, 1.0f);
                }
            }
        });



    }

    public Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
