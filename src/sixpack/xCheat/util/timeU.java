package sixpack.xCheat.util;

public class timeU {

    public static long nowlong() {
        return System.currentTimeMillis();
    }
	
    public static boolean elapsed(long from, long required) {
        if (System.currentTimeMillis() - from > required) {
            return true;
        }
        return false;
    }

    public static long elapsed(long starttime) {
        return System.currentTimeMillis() - starttime;
    }
	
}
