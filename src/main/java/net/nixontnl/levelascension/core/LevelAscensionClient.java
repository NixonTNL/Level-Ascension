package net.nixontnl.levelascension.core;

import net.fabricmc.api.ClientModInitializer;
import net.nixontnl.levelascension.ui.SkillHudOverlay;

public class LevelAscensionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new SkillHudOverlay().onInitializeClient();
    }
}
