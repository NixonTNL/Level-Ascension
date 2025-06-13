package net.nixontnl.levelascension.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.skills.base.PlayerSkillData;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;

public class SkillsScreen extends Screen {

    private static final Identifier PICKAXE_ICON = Identifier.of("minecraft", "textures/item/diamond_pickaxe.png");
    private static final Identifier AXE_ICON = Identifier.of("minecraft", "textures/item/diamond_axe.png");

    public SkillsScreen() {
        super(Text.of("Skills"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // ✅ Fully opaque black background (blocks blur shader completely)
        context.fill(0, 0, this.width, this.height, 0xFF000000);

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        PlayerSkillData data = PlayerSkillDataManager.get(client.player.getUuid());
        if (data == null) return;

        int y = 40;
        int x = this.width / 2 - 100;

        for (String skill : data.getAllSkills()) {
            int xp = data.getXP(skill);
            int level = data.getLevel(skill);
            int xpForNext = PlayerSkillData.getXPForLevel(level + 1);
            float progress = Math.min(1.0f, xp / (float) xpForNext);

            Identifier icon = skill.equalsIgnoreCase("woodcutting") ? AXE_ICON : PICKAXE_ICON;

            context.drawTexture(icon, x, y + 4, 0, 0, 16, 16, 16, 16);
            context.drawText(client.textRenderer, skill.toUpperCase() + " (Lvl " + level + ")", x + 22, y, 0xFFFFFF, false);
            context.fill(x + 22, y + 14, x + 182, y + 22, 0xFF333333); // bar background
            context.fill(x + 22, y + 14, x + 22 + (int)(160 * progress), y + 22, 0xFFB89E00); // bar fill

            y += 36;
        }

        // ❌ DO NOT call super.render() — it brings back blur
        // super.render(context, mouseX, mouseY, delta);
    }

    // ✅ Prevent game from pausing — avoids blur in some versions
    @Override
    public boolean shouldPause() {
        return false;
    }

    // ✅ Used in 1.20+ mappings to prevent blur logic
    public boolean isPauseScreen() {
        return false;
    }
}
