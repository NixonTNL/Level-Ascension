package net.nixontnl.levelascension.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class LevelUpOverlay {
    private static boolean showMessage = false;
    private static String message = "";
    private static long displayStartTime = 0;

    public static void show(String skill, int level) {
        message = "Level Up! " + skill + " is now " + level;
        displayStartTime = System.currentTimeMillis();
        showMessage = true;
    }

    public static void render(DrawContext context) {
        if (!showMessage) return;

        long elapsed = System.currentTimeMillis() - displayStartTime;
        if (elapsed > 3000) {
            showMessage = false;
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        TextRenderer textRenderer = client.textRenderer;
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        int textWidth = textRenderer.getWidth(message);

        int x = (width - textWidth) / 2;
        int y = height / 4;

        context.drawText(textRenderer, Text.literal(message), x, y, 0xFFFFFF, true);
    }
}
