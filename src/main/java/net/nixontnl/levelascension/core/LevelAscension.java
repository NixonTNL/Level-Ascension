package net.nixontnl.levelascension.core;

import net.fabricmc.api.ModInitializer;
import net.nixontnl.levelascension.events.SkillEventHandler;

public class LevelAscension implements ModInitializer {
    public static final String MOD_ID = "levelascension";

    @Override
    public void onInitialize() {
        SkillEventHandler.register();
        System.out.println("Level Ascension initialized!");
    }
}
