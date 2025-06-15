package net.nixontnl.levelascension.ui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.util.XPUtils;

public class SkillHudOverlay implements ClientModInitializer {
    private static final Identifier ICONS = Identifier.of("minecraft", "textures/gui/icons.png");

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((ctx, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            PlayerSkillData data = SkillEventHandler.getSkillData(client.player.getUuid());
            if (data == null) return;

            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();

            int barWidth = 180;
            int barHeight = 5;
            int iconSize = 16;
            int spacing = 4;

            int totalWidth = iconSize + spacing + barWidth;
            int baseX = (screenWidth / 2) - (totalWidth / 2) - 10;
            int iconX = baseX;
            int barX = iconX + iconSize + spacing;
            int y = screenHeight - 46; // Sits just above hotbar/vanilla hearts

            // XP values
            int xp = data.getSkill(SkillType.MINING).getXp();
            int level = data.getSkill(SkillType.MINING).getLevel();
            int baseXp = XPUtils.getXpForLevel(level);
            int nextXp = XPUtils.getXpForLevel(level + 1);
            float progress = (float)(xp - baseXp) / (nextXp - baseXp);
            int fillWidth = (int)(barWidth * progress);

            // Draw border (black outline)
            ctx.fill(barX - 1, y - 1, barX + barWidth + 1, y + barHeight + 1, 0xFF000000);

            // Draw background (dark gray)
            ctx.fill(barX, y, barX + barWidth, y + barHeight, 0xFF444444);

            // Draw fill (green)
            ctx.fill(barX, y, barX + fillWidth, y + barHeight, 0xFF2ECC71);

            // Optional: draw pickaxe icon
            ItemStack icon = new ItemStack(Items.DIAMOND_PICKAXE);
            ctx.drawItem(icon, iconX, y - ((iconSize - barHeight) / 2));
        });

    }
}
