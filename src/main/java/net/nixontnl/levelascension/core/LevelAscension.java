package net.nixontnl.levelascension.core;

import net.fabricmc.api.ModInitializer;
import net.nixontnl.levelascension.skills.mining.MiningXPHandler;
import net.nixontnl.levelascension.skills.woodcutting.WoodcuttingXPHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelAscension implements ModInitializer {
    public static final String MOD_ID = "levelascension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Level Ascension has loaded!");
        MiningXPHandler.register();
        WoodcuttingXPHandler.register();
    }
}
