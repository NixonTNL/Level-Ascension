package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.LinkedList;

public class CampfireCookingEntityListener {

    private static final LinkedList<TrackedItem> trackedItems = new LinkedList<>();
    private static final int MAX_TRACK_TICKS = 10;

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            long currentTick = world.getTime();

            // Check tracked items
            Iterator<TrackedItem> iterator = trackedItems.iterator();
            while (iterator.hasNext()) {
                TrackedItem tracked = iterator.next();
                ItemEntity entity = tracked.entity;

                if (!entity.isAlive() || currentTick - tracked.tickSeen > MAX_TRACK_TICKS) {
                    iterator.remove();
                    continue;
                }

                if (tracked.xpGranted) continue;

                BlockPos itemPos = entity.getBlockPos();
                boolean nearCampfire = false;

                // Check for nearby lit campfire blocks
                outer:
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        BlockPos checkPos = itemPos.down().add(dx, 0, dz);
                        BlockState state = world.getBlockState(checkPos);
                        if (state.isOf(Blocks.CAMPFIRE) && state.get(CampfireBlock.LIT)) {
                            nearCampfire = true;
                            break outer;
                        }
                    }
                }

                if (nearCampfire) {
                    ItemStack stack = entity.getStack();

                    if (!(stack.getItem().getMaxCount() == 64)) return; // Optional: crude filter

                    PlayerEntity nearest = world.getClosestPlayer(entity, 20);
                    if (nearest instanceof ServerPlayerEntity serverPlayer) {
                        System.out.println("🔥 Campfire XP granted for: " + stack.getItem());
                        SkillEventHandler.handleCookingXp(serverPlayer, stack.copy(), "minecraft:campfire");
                        tracked.xpGranted = true;
                    }
                }
            }

            // Track new item entities
            for (Entity e : world.iterateEntities()) {
                if (e instanceof ItemEntity itemEntity && !isAlreadyTracked(itemEntity)) {
                    trackedItems.add(new TrackedItem(itemEntity, currentTick));
                }
            }

            // Prevent memory overload
            if (trackedItems.size() > 1000) trackedItems.clear();
        });
    }

    private static boolean isAlreadyTracked(ItemEntity entity) {
        for (TrackedItem tracked : trackedItems) {
            if (tracked.entity == entity) return true;
        }
        return false;
    }

    private static class TrackedItem {
        public final ItemEntity entity;
        public final long tickSeen;
        public boolean xpGranted = false;

        public TrackedItem(ItemEntity entity, long tickSeen) {
            this.entity = entity;
            this.tickSeen = tickSeen;
        }
    }
}
