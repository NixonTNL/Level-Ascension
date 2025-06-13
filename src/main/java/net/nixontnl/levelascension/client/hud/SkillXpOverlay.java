package net.nixontnl.levelascension.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.skills.base.PlayerSkillData;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;

public class SkillXpOverlay {

    private static final Identifier PICKAXE_ICON = Identifier.of("minecraft", "textures/item/diamond_pickaxe.png");
    private static final Identifier AXE_ICON = Identifier.of("minecraft", "textures/item/diamond_axe.png");

    private long lastXpGainTime = 0;
    private int lastXpValue = -1;

    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((context, ignored) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;

            PlayerSkillData data = PlayerSkillDataManager.get(client.player.getUuid());
            if (data == null || !data.hasActiveSkill()) return;

            String skill = data.getActiveSkill();
            int totalXp = data.getXP(skill);

            // Track XP gain and timestamp
            if (totalXp != lastXpValue) {
                lastXpGainTime = System.currentTimeMillis();
                lastXpValue = totalXp;
            }

            // Only show for 3 seconds after XP gain
            if (System.currentTimeMillis() - lastXpGainTime > 3000) return;

            int level = data.getLevel(skill);
            int xpAtCurrentLevel = PlayerSkillData.getXPForLevel(level);
            int xpAtNextLevel = PlayerSkillData.getXPForLevel(level + 1);

            int xpThisLevel = Math.max(0, totalXp - xpAtCurrentLevel);
            int xpNeeded = Math.max(1, xpAtNextLevel - xpAtCurrentLevel);
            float progress = Math.min(1.0f, xpThisLevel / (float) xpNeeded);

            Window window = client.getWindow();
            int x = window.getScaledWidth() / 2 - 91;
            int y = window.getScaledHeight() - 32;

            // Draw background
            context.fill(x, y, x + 182, y + 5, 0xFF000000);

            // Draw XP progress
            context.fill(x, y, x + (int)(182 * progress), y + 5, 0xFFB89E00);

            // Choose icon
            Identifier icon = PICKAXE_ICON; // default
            if (skill.equalsIgnoreCase("woodcutting")) icon = AXE_ICON;

            context.drawTexture(icon, x - 20, y - 8, 0, 0, 16, 16, 16, 16);
        });
    }
}
