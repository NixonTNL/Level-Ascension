package net.nixontnl.levelascension.skills.woodcutting;

import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;
import net.nixontnl.levelascension.utilities.XPUtils;

import java.util.UUID;

public class WoodcuttingSkillManager {
    public static void addXP(UUID playerId, int amount) {
        int currentXP = PlayerSkillDataManager.getSkillXP(playerId, "woodcutting");
        int newXP = currentXP + amount;
        PlayerSkillDataManager.setSkillXP(playerId, "woodcutting", newXP);

        int currentLevel = PlayerSkillDataManager.getSkillLevel(playerId, "woodcutting");
        int requiredXP = getXPForLevel(currentLevel + 1);

        if (newXP >= requiredXP) {
            PlayerSkillDataManager.setSkillLevel(playerId, "woodcutting", currentLevel + 1);
            PlayerSkillDataManager.setSkillXP(playerId, "woodcutting", 0);

            ServerPlayerEntity player = PlayerSkillDataManager.getPlayerFromId(playerId);
            if (player != null) {
                XPUtils.sendLevelUpMessage(player, "Woodcutting", currentLevel + 1);
            }
        } else {
            ServerPlayerEntity player = PlayerSkillDataManager.getPlayerFromId(playerId);
            if (player != null) {
                XPUtils.sendXPMessage(player, "Woodcutting", amount);
            }
        }
    }

    public static int getXPForLevel(int level) {
        return 100 + (level - 1) * 50;
    }

    public static void setXP(UUID playerId, int amount) {
        PlayerSkillDataManager.setSkillXP(playerId, "woodcutting", amount);
    }
}
