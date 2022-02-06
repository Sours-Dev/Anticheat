package sixpack.xCheat.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DataManager {
	
	
	
	private Set<DataPlayer> dataSet = new HashSet<>();
	
	
	public DataManager() {
		for(Player players : Bukkit.getOnlinePlayers()) {
			add(players);
		}

	}
	
	
    public DataPlayer getDataPlayer(Player p) {
        return dataSet.stream().filter(dataPlayer -> dataPlayer.p == p).findFirst().orElse(null);
    }

    public void add(Player p) {
        dataSet.add(new DataPlayer(p));
    }

    public void remove(Player p) {
        dataSet.removeIf(dataPlayer -> dataPlayer.p == p);
    }

}
