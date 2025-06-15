package net.nixontnl.levelascension.skills;

import net.nixontnl.levelascension.util.XPUtils;

/**
 * Represents a skill with XP and level.
 */
public class Skill {
    private int xp;
    private int level;

    public Skill() {
        this.xp = 0;
        this.level = 1;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public void addXp(int amount) {
        this.xp += amount;
        int newLevel = XPUtils.getLevelFromXp(this.xp);
        if (newLevel > this.level) {
            this.level = newLevel;
            // You can trigger level up toast later here
        }
    }
}
