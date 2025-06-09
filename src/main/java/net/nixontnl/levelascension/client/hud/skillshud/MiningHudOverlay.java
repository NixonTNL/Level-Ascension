package net.nixontnl.levelascension.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class MiningHudOverlay {
    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.player != null) {
            int x = 10;
            int y = 10;
            context.drawText(client.textRenderer, Text.literal("Mining XP Bar Placeholder"), x, y, 0xFFFFFF, true);
        }
    }
}
