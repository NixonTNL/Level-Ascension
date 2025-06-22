package net.nixontnl.levelascension.skills.logic.ranged;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.events.SkillEventHandler;

import java.util.HashMap;
import java.util.Map;

public class RangedSkillManager {

    private static final Map<String, Integer> MOB_XP_MAP = new HashMap<>();

    static {
        // Same XP values as Melee for consistency
        MOB_XP_MAP.put("minecraft:zombie", 10);
        MOB_XP_MAP.put("minecraft:skeleton", 10);
        MOB_XP_MAP.put("minecraft:creeper", 10);
        MOB_XP_MAP.put("minecraft:spider", 10);
        MOB_XP_MAP.put("minecraft:drowned", 12);
        MOB_XP_MAP.put("minecraft:husk", 12);
        MOB_XP_MAP.put("minecraft:stray", 12);
        MOB_XP_MAP.put("minecraft:witch", 25);
        MOB_XP_MAP.put("minecraft:pillager", 15);
        MOB_XP_MAP.put("minecraft:vindicator", 20);
        MOB_XP_MAP.put("minecraft:evoker", 30);
        MOB_XP_MAP.put("minecraft:illusioner", 30);
        MOB_XP_MAP.put("minecraft:ravager", 40);
        MOB_XP_MAP.put("minecraft:phantom", 15);
        MOB_XP_MAP.put("minecraft:silverfish", 5);

        MOB_XP_MAP.put("minecraft:blaze", 20);
        MOB_XP_MAP.put("minecraft:magma_cube", 15);
        MOB_XP_MAP.put("minecraft:wither_skeleton", 25);
        MOB_XP_MAP.put("minecraft:ghast", 20);
        MOB_XP_MAP.put("minecraft:piglin", 15);
        MOB_XP_MAP.put("minecraft:piglin_brute", 30);
        MOB_XP_MAP.put("minecraft:zombified_piglin", 10);
        MOB_XP_MAP.put("minecraft:hoglin", 18);
        MOB_XP_MAP.put("minecraft:zoglin", 20);

        MOB_XP_MAP.put("minecraft:enderman", 15);
        MOB_XP_MAP.put("minecraft:shulker", 20);
        MOB_XP_MAP.put("minecraft:endermite", 5);

        MOB_XP_MAP.put("minecraft:wither", 100);
        MOB_XP_MAP.put("minecraft:ender_dragon", 200);
    }

    public static void handleRangedXp(ServerPlayerEntity player, LivingEntity target) {
        PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
        if (data == null) return;

        int xp = getXpForMob(target);
        if (xp > 0) {
            data.addXP(SkillType.RANGED, player, xp);
        }
    }

    private static int getXpForMob(LivingEntity entity) {
        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        return MOB_XP_MAP.getOrDefault(id.toString(), 0);
    }
}
