package net.nixontnl.levelascension.core;

import net.fabricmc.api.ClientModInitializer;
import net.nixontnl.levelascension.ui.LevelUpPopupManager;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.ModInitializer;
import net.nixontnl.levelascension.events.SkillEventHandler;



public class LevelAscension implements ModInitializer {
    public static final String MOD_ID = "levelascension";

    @Override
    public void onInitialize() {
        if (MinecraftClient.getInstance() != null) {
            MinecraftClient.getInstance().execute(() ->
                    net.nixontnl.levelascension.ui.LevelUpPopupManager.showMessage("Mining", 5)
            );
        }
        SkillEventHandler.register();
        System.out.println("Level Ascension initialized!");
    }
}
