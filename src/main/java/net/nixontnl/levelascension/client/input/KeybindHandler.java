package net.nixontnl.levelascension.client.input;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.nixontnl.levelascension.client.hud.SkillsScreen;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {

    private static KeyBinding openSkillsScreen;

    public static void register() {
        openSkillsScreen = new KeyBinding(
                "key.levelascension.open_skills_screen",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                "category.levelascension"
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openSkillsScreen.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new SkillsScreen());
                }
            }
        });
    }

    public static KeyBinding getKeybind() {
        return openSkillsScreen;
    }
}
