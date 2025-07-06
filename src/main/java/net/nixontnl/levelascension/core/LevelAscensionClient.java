package net.nixontnl.levelascension.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.nixontnl.levelascension.ui.LevelUpOverlay;
import net.nixontnl.levelascension.ui.SkillsScreen; // make sure this class exists
import org.lwjgl.glfw.GLFW;

public class LevelAscensionClient implements ClientModInitializer {

    private static KeyBinding OPEN_SKILLS;

    @Override
    public void onInitializeClient() {
        // Register overlay render
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            LevelUpOverlay.render(drawContext);
        });

        // Register keybind
        OPEN_SKILLS = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.levelascension.open_skills",            // Translation key
                InputUtil.Type.KEYSYM,                       // Keyboard keys
                GLFW.GLFW_KEY_K,                             // Default = K
                "category.levelascension"                    // Translation category
        ));

        // Listen for key press
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_SKILLS.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new SkillsScreen());
                }
            }
        });
    }
}
