package net.nixontnl.levelascension.skills.base;

import java.util.HashMap;
import java.util.Map;

public class PlayerSkillData {

    private final Map<String, Integer> xpMap = new HashMap<>();
    private final Map<String, Integer> levelMap = new HashMap<>();
    private String activeSkill = null;

    public void addXP(String skill, int amount) {
        int currentXP = xpMap.getOrDefault(skill, 0);
        currentXP += amount;
        xpMap.put(skill, currentXP);

        int currentLevel = levelMap.getOrDefault(skill, 1);
        while (currentXP >= getXPForLevel(currentLevel + 1)) {
            currentLevel++;
        }
        levelMap.put(skill, currentLevel);

        activeSkill = skill;
    }

    public int getXP(String skill) {
        return xpMap.getOrDefault(skill, 0);
    }

    public void setXP(String skill, int xp) {
        xpMap.put(skill, xp);
    }

    public int getLevel(String skill) {
        return levelMap.getOrDefault(skill, 1);
    }

    public void setLevel(String skill, int level) {
        levelMap.put(skill, level);
    }

    public static int getXPForLevel(int level) {
        return level * level * 10;
    }

    public boolean hasActiveSkill() {
        return activeSkill != null;
    }

    public String getActiveSkill() {
        return activeSkill;
    }
}
