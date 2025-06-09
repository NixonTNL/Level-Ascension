package net.nixontnl.levelascension.core;

import net.fabricmc.api.ClientModInitializer;
import net.nixontnl.levelascension.client.hud.SkillXpOverlay;

public class LevelAscensionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new SkillXpOverlay().onInitializeClient();
    }
}
