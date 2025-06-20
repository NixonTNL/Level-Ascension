package net.nixontnl.levelascension.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class LevelUpPopupManager {
    private static boolean showMessage = false;
    private static String message = "";
    private static long displayStartTime = 0;

    public static void showMessage(String skill, int level) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client != null && client.player != null) {
            client.execute(() -> {
                System.out.println("Playing level up sound!");
                LevelUpOverlay.show(skill, level);
                client.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            });
        }
    }

    public static void render(DrawContext context, float tickDelta) {
        if (!showMessage) return;

        long elapsed = System.currentTimeMillis() - displayStartTime;
        if (elapsed > 3000) {
            showMessage = false;
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        TextRenderer textRenderer = client.textRenderer;
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        int textWidth = textRenderer.getWidth(message);
        int x = (screenWidth - textWidth) / 2;
        int y = screenHeight / 4;

        context.drawText(textRenderer, Text.literal(message), x, y, 0xFFFFFF, true);
    }
}
