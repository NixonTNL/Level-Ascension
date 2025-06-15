package net.nixontnl.levelascension.skills;

/**
 * Enum representing all possible skills. Right now we only have Mining.
 */
public enum SkillType {
    MINING("mining");

    private final String name;

    SkillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
