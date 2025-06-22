package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.*;
import net.nixontnl.levelascension.skills.logic.melee.MeleeSkillManager;

public class CombatEventHandler {
    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            if (!(killedEntity instanceof LivingEntity livingTarget)) return;
            if (!isWeapon(player.getMainHandStack().getItem())) return;

            MeleeSkillManager.handleMeleeXp(player, livingTarget);
        });
    }

    private static boolean isWeapon(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem || item.getTranslationKey().contains("mace");
    }
}
