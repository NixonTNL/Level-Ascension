package net.nixontnl.levelascension.util;

/**
 * Handles XP curve like RuneScape: fast at early levels, very slow at high ones.
 */
public class XPUtils {
    public static int getXpForLevel(int level) {
        int xp = 0;
        for (int i = 1; i < level; i++) {
            xp += Math.floor(i + 300 * Math.pow(2, i / 7.0));
        }
        return xp / 4;
    }

    public static int getLevelFromXp(int xp) {
        for (int level = 1; level <= 99; level++) {
            if (xp < getXpForLevel(level + 1)) {
                return level;
            }
        }
        return 99;
    }
}
