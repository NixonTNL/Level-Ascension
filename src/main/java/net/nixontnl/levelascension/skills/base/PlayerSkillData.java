package net.nixontnl.levelascension.skills.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSkillData {

    private final Map<String, Integer> xpMap = new HashMap<>();
    private final Map<String, Integer> levelMap = new HashMap<>();
    private String activeSkill = null;

    public void addXP(String skill, int amount) {
        int currentXP = getXP(skill);
        currentXP += amount;
        xpMap.put(skill, currentXP);

        int currentLevel = getLevel(skill);
        while (currentXP >= getXPForLevel(currentLevel + 1)) {
            currentLevel++;
        }
        levelMap.put(skill, currentLevel);

        activeSkill = skill;
    }

    public int getXP(String skill) {
        return xpMap.computeIfAbsent(skill, s -> 0);
    }

    public void setXP(String skill, int xp) {
        xpMap.put(skill, xp);
    }

    public int getLevel(String skill) {
        return levelMap.computeIfAbsent(skill, s -> 1);
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

    public Set<String> getAllSkills() {
        // Return known skills even if they haven't been used yet
        Set<String> allSkills = new HashSet<>();
        allSkills.add("mining");
        allSkills.add("woodcutting");
        return allSkills;
    }
}
