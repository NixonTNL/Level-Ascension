package net.nixontnl.levelascension.util;

import net.minecraft.util.math.BlockPos;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SmeltingContextTracker {
    private static final ConcurrentHashMap<UUID, BlockPos> contextMap = new ConcurrentHashMap<>();

    public static void setContext(UUID playerId, BlockPos pos) {
        contextMap.put(playerId, pos);
    }

    public static BlockPos getContext(UUID playerId) {
        return contextMap.get(playerId);
    }

    public static void clear(UUID playerId) {
        contextMap.remove(playerId);
    }
}
