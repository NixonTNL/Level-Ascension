package net.nixontnl.levelascension.skills.mining;

import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;
import net.nixontnl.levelascension.utilities.XPUtils;

import java.util.UUID;

public class MiningSkillManager {
    public static void addXP(UUID playerId, int amount) {
        int currentXP = PlayerSkillDataManager.getSkillXP(playerId, "mining");
        int newXP = currentXP + amount;
        PlayerSkillDataManager.setSkillXP(playerId, "mining", newXP);

        int currentLevel = PlayerSkillDataManager.getSkillLevel(playerId, "mining");
        int requiredXP = getXPForLevel(currentLevel + 1);

        if (newXP >= requiredXP) {
            PlayerSkillDataManager.setSkillLevel(playerId, "mining", currentLevel + 1);
            PlayerSkillDataManager.setSkillXP(playerId, "mining", 0);

            ServerPlayerEntity player = PlayerSkillDataManager.getPlayerFromId(playerId);
            if (player != null) {
                XPUtils.sendLevelUpMessage(player, "Mining", currentLevel + 1);
            }
        } else {
            ServerPlayerEntity player = PlayerSkillDataManager.getPlayerFromId(playerId);
            if (player != null) {
                XPUtils.sendXPMessage(player, "Mining", amount);
            }
        }
    }

    public static int getXPForLevel(int level) {
        return 100 + (level - 1) * 50;
    }

    public static void setXP(UUID playerId, int amount) {
        PlayerSkillDataManager.setSkillXP(playerId, "mining", amount);
    }
}
