package net.nixontnl.levelascension.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.nixontnl.levelascension.ui.LevelUpOverlay;

public class LevelAscensionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            LevelUpOverlay.render(drawContext);
        });
    }
}
