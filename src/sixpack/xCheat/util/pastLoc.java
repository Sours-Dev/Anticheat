package sixpack.xCheat.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;


public class pastLoc {
	   private List<locationU> previousLocations = new CopyOnWriteArrayList<>();

	    public locationU getPreviousLocation(long time) {
	        return previousLocations.stream().min(Comparator.comparingLong(loc -> Math.abs(loc.getTimeStamp() - (System.currentTimeMillis() - time)))).orElse(previousLocations.get(previousLocations.size() - 1));
	    }

	    public List<locationU> getEstimatedLocation(long time, long delta) {
	        List<locationU> locs = new ArrayList<>();

	        previousLocations.stream()
	                .sorted(Comparator.comparingLong(loc -> Math.abs(loc.getTimeStamp() - (System.currentTimeMillis() - time))))
	                .filter(loc -> Math.abs(loc.getTimeStamp() - (System.currentTimeMillis() - time)) < delta)
	                .forEach(locs::add);
	        return locs;
	    }

	    public void addLocation(Location location) {
	        if (previousLocations.size() >= 20) {
	            previousLocations.remove(0);
	        }

	        previousLocations.add(new locationU(location));
	    }

	    public void addLocation(locationU location) {
	        if (previousLocations.size() >= 20) {
	            previousLocations.remove(0);
	        }

	        previousLocations.add(location);
	    }

	    public List<locationU> getPreviousLocations() {
	        return previousLocations;
	    }

}
