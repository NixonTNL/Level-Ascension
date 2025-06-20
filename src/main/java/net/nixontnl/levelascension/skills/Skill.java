package net.nixontnl.levelascension.skills;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.nixontnl.levelascension.ui.LevelUpOverlay;
import net.nixontnl.levelascension.util.XPUtils;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.nixontnl.levelascension.ui.LevelUpPopupManager;

public class Skill {
    private final String name;
    private int xp;

    public Skill(String name) {
        this.name = name;
        this.xp = 0;
    }

    public void addXP(ServerPlayerEntity player, int amount) {
        int oldLevel = getLevel();
        this.xp += amount;
        int newLevel = getLevel();

        if (newLevel > oldLevel && MinecraftClient.getInstance() != null) {
            // Show popup on client
            MinecraftClient.getInstance().execute(() ->
                    LevelUpPopupManager.showMessage(this.name, newLevel)
            );

            // Spawn particles
            ServerWorld world = player.getServerWorld();
            world.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    player.getX(), player.getY() + 1.5, player.getZ(),
                    20, 0.2, 0.5, 0.2, 0.05
            );
        }
    }

    public int getXP() {
        return xp;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return XPUtils.getLevelFromXp(xp);
    }

    public String getName() {
        return name;
    }
}

