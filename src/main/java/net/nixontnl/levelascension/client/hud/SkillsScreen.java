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
            int xpCurrentLevel = PlayerSkillData.getXPForLevel(level);
            int xpNextLevel = PlayerSkillData.getXPForLevel(level + 1);
            int xpIntoCurrent = xp - xpCurrentLevel;
            int xpNeeded = xpNextLevel - xpCurrentLevel;
            float progress = Math.min(1.0f, xpIntoCurrent / (float) xpNeeded);

            Identifier icon = skill.equalsIgnoreCase("woodcutting") ? AXE_ICON : PICKAXE_ICON;

            context.drawTexture(icon, x, y + 4, 0, 0, 16, 16, 16, 16);
            context.drawText(client.textRenderer, skill.toUpperCase() + " (Lvl " + level + ")", x + 22, y, 0xFFFFFF, false);
            context.fill(x + 22, y + 14, x + 182, y + 22, 0xFF333333);
            context.fill(x + 22, y + 14, x + 22 + (int)(160 * progress), y + 22, 0xFFB89E00);

            y += 36;
        }
    }

    public boolean shouldPause() {
        return false;
    }

    public boolean isPauseScreen() {
        return false;
    }
}
