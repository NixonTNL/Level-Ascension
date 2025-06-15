package net.nixontnl.levelascension.skills.player;

import net.nixontnl.levelascension.skills.Skill;
import net.nixontnl.levelascension.skills.SkillType;

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

    public void addXp(SkillType type, int amount) {
        skillMap.get(type).addXP(amount);
    }
}
