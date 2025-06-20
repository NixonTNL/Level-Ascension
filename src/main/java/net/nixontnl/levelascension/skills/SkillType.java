package net.nixontnl.levelascension.skills;

import java.util.jar.Attributes;

/**
 * Enum representing all possible skills. Right now we only have Mining.
 */
public enum SkillType {
    MINING("mining"),
    WOODCUTTING("woodcutting");

    private final String name;

    SkillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
