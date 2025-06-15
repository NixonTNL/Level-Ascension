package net.nixontnl.levelascension.skills;

import net.minecraft.client.MinecraftClient;
import net.nixontnl.levelascension.ui.LevelUpOverlay;
import net.nixontnl.levelascension.util.XPUtils;

public class Skill {
    private final String name;
    private int xp;

    public Skill(String name) {
        this.name = name;
        this.xp = 0;
    }

    public void addXP(int amount) {
        int oldLevel = getLevel();
        this.xp += amount;
        int newLevel = getLevel();

        if (newLevel > oldLevel && MinecraftClient.getInstance() != null) {
            MinecraftClient.getInstance().execute(() ->
                    LevelUpOverlay.show(this.name, newLevel)  // 🔥 Correct method
            );
        }
    }

    public int getXP() {
        return xp;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return XPUtils.getLevelFromXp(xp);
    }

    public String getName() {
        return name;
    }
}
