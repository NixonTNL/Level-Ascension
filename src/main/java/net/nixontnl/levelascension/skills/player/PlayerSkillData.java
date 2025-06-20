package net.nixontnl.levelascension.skills.player;

import net.nixontnl.levelascension.skills.Skill;
import net.nixontnl.levelascension.skills.SkillType;
import net.minecraft.server.network.ServerPlayerEntity;


import java.util.EnumMap;

/**
 * Stores XP and level data for each skill per player.
 */
public class PlayerSkillData {
    private final EnumMap<SkillType, Skill> skillMap = new EnumMap<>(SkillType.class);

    public PlayerSkillData() {
        for (SkillType type : SkillType.values()) {
            skillMap.put(type, new Skill(type.name()));
        }
    }

    public Skill getSkill(SkillType type) {
        return skillMap.get(type);
    }

    public void addXP(SkillType type, ServerPlayerEntity player, int amount) {
        Skill skill = skillMap.get(type);
        if (skill != null) {
            skill.addXP(player, amount);
            updateLastXpGainTime(); // 👈 Call this here
        }
    }

    private long lastXpGainTime = 0;

    public void updateLastXpGainTime() {
        this.lastXpGainTime = System.currentTimeMillis();
    }

    public boolean shouldShowXpBar() {
        return System.currentTimeMillis() - lastXpGainTime <= 3000; // 3 seconds
    }

}
