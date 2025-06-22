package net.nixontnl.levelascension.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.ui.LevelUpPopupManager;

public class SkillXPHelper {

    public static void addXP(ServerPlayerEntity player, String skillId, int amount) {
        SkillType type = SkillType.valueOf(skillId.toUpperCase());
        PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());

        if (data != null) {
            data.addXP(type, player, amount);
        }
    }

    public static int getLevel(ServerPlayerEntity player, String skillId) {
        SkillType type = SkillType.valueOf(skillId.toUpperCase());
        PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());

        if (data != null) {
            return data.getSkill(type).getLevel();
        }

        return 0;
    }

    public static void sendLevelUpMessage(ServerPlayerEntity player, String skillId) {
        int level = getLevel(player, skillId); // ✅ Get the level first
        LevelUpPopupManager.showMessage(skillId.toUpperCase(), level);
    }


    public static void sendLevelUpMessage(ServerPlayerEntity player, String skillId, int level) {
        LevelUpPopupManager.showMessage(skillId.toUpperCase(), level);
    }
}
