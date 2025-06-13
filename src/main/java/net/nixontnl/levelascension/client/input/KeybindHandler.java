package net.nixontnl.levelascension.client.input;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.nixontnl.levelascension.client.hud.SkillsScreen;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {

    private static KeyBinding SHOW_SKILLS_KEY;

    public static void register() {
        SHOW_SKILLS_KEY = new KeyBinding(
                "key.levelascension.show_skills",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                "category.levelascension"
        );

        // Register tick event to check key press
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (SHOW_SKILLS_KEY.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new SkillsScreen());
                }
            }
        });
    }

    public static KeyBinding getShowSkillsKey() {
        return SHOW_SKILLS_KEY;
    }
}
