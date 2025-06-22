package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

/**
 * Custom callback for when a player breeds animals.
 */
@FunctionalInterface
public interface PlayerBreedAnimalsCallback {
    Event<PlayerBreedAnimalsCallback> EVENT = EventFactory.createArrayBacked(PlayerBreedAnimalsCallback.class,
            (listeners) -> (player, parent1, parent2, child) -> {
                for (PlayerBreedAnimalsCallback listener : listeners) {
                    ActionResult result = listener.onBreed(player, parent1, parent2, child);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult onBreed(PlayerEntity player, PassiveEntity parent1, PassiveEntity parent2, PassiveEntity child);
}
