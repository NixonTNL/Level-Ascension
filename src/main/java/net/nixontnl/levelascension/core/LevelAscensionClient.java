package net.nixontnl.levelascension;

import net.fabricmc.api.ClientModInitializer;
import net.nixontnl.levelascension.client.hud.SkillXpOverlay;
import net.nixontnl.levelascension.client.input.KeybindHandler;

public class LevelAscensionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new SkillXpOverlay().onInitializeClient(); // HUD
        KeybindHandler.register(); // 👈 Register the keybind
    }
}
