package net.nixontnl.levelascension.skills.base;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSkillDataManager {

    private static final HashMap<UUID, PlayerSkillData> skillDataMap = new HashMap<>();

    public static PlayerSkillData get(ServerPlayerEntity player) {
        return skillDataMap.computeIfAbsent(player.getUuid(), uuid -> new PlayerSkillData());
    }

    public static PlayerSkillData get(UUID uuid) {
        return skillDataMap.computeIfAbsent(uuid, id -> new PlayerSkillData());
    }

    public static void addXP(ServerPlayerEntity player, String skill, int xpAmount) {
        PlayerSkillData data = get(player);
        int oldLevel = data.getLevel(skill);
        data.addXP(skill, xpAmount);
        int newLevel = data.getLevel(skill);

        if (newLevel > oldLevel) {
            player.sendMessage(Text.literal("§6Your " + skill + " skill has leveled up to " + newLevel + "!"), false);
        }
    }

    public static int getSkillXP(UUID id, String skill) {
        return get(id).getXP(skill);
    }

    public static void setSkillXP(UUID id, String skill, int amount) {
        get(id).setXP(skill, amount);
    }

    public static int getSkillLevel(UUID id, String skill) {
        return get(id).getLevel(skill);
    }

    public static void setSkillLevel(UUID id, String skill, int level) {
        get(id).setLevel(skill, level);
    }

    public static ServerPlayerEntity getPlayerFromId(UUID id) {
        return null; // Stub: implement player tracking manually if needed
    }
}
