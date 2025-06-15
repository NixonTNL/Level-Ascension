package net.nixontnl.levelascension.ui;

import net.minecraft.text.Text;

public class LevelUpPopupManager {
    private static Text message = null;
    private static int timer = 0;
    private static final int DISPLAY_TICKS = 60; // 3 seconds

    public static void showMessage(String skillName, int newLevel) {
        message = Text.literal("You reached " + skillName + " level " + newLevel + "!");
        timer = DISPLAY_TICKS;
    }

    public static Text getMessage() {
        return timer > 0 ? message : null;
    }

    public static void tick() {
        if (timer > 0) {
            timer--;
        }
    }
}
