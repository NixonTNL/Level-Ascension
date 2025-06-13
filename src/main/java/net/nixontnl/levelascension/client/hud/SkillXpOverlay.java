package net.nixontnl.levelascension.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.skills.base.PlayerSkillData;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;

public class SkillXpOverlay {

    private long lastXpGainTime = 0;
    private int lastXpValue = -1;

    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((context, ignored) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;

            PlayerSkillData data = PlayerSkillDataManager.get(client.player.getUuid());
            if (data == null || !data.hasActiveSkill()) return;

            int xp = data.getXP(data.getActiveSkill());
            long currentTime = System.currentTimeMillis();

            if (xp != lastXpValue) {
                lastXpGainTime = currentTime;
                lastXpValue = xp;
            }

            if (currentTime - lastXpGainTime > 3000) {
                return;
            }

            int x = context.getScaledWindowWidth() / 2 - 91;
            int y = context.getScaledWindowHeight() - 32;

            int level = data.getLevel(data.getActiveSkill());
            int xpForNext = PlayerSkillData.getXPForLevel(level + 1);
            float progress = Math.min(1.0f, xp / (float) xpForNext);

            context.fill(x, y, x + 182, y + 5, 0xFF000000);
            context.fill(x, y, x + (int)(182 * progress), y + 5, 0xFFB89E00);

            // 🔍 Detect and apply correct icon
            String activeSkill = data.getActiveSkill().toLowerCase(); // normalize casing
            System.out.println("Active skill: " + activeSkill); // for debugging

            Identifier icon;
            if (activeSkill.equals("mining")) {
                icon = Identifier.of("minecraft", "textures/item/diamond_pickaxe.png");
            } else if (activeSkill.equals("woodcutting")) {
                icon = Identifier.of("minecraft", "textures/item/diamond_axe.png");
            } else {
                icon = Identifier.of("minecraft", "textures/item/book.png"); // fallback
            }

            context.drawTexture(icon, x - 20, y - 8, 0, 0, 16, 16, 16, 16);
        });
    }
}
