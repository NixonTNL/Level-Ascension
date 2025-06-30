package net.nixontnl.levelascension.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreshBrushTracker {

    private static final Map<BlockPos, UUID> freshlyBrushed = new HashMap<>();

    public static void mark(World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            freshlyBrushed.put(pos.toImmutable(), player.getUuid());
        }
    }

    public static boolean wasFreshlyBrushed(World world, BlockPos pos) {
        return freshlyBrushed.containsKey(pos.toImmutable());
    }

    public static PlayerEntity getLastPlayer(World world, BlockPos pos) {
        UUID uuid = freshlyBrushed.get(pos.toImmutable());
        return uuid != null ? world.getPlayerByUuid(uuid) : null;
    }

    public static void clear(World world, BlockPos pos) {
        freshlyBrushed.remove(pos.toImmutable());
    }
}
