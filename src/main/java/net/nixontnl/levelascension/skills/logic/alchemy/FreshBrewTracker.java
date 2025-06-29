package net.nixontnl.levelascension.skills.logic.alchemy;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class FreshBrewTracker {
    public static class UpgradeFlags {
        public final boolean isStrong;
        public final boolean isExtended;

        public UpgradeFlags(boolean isStrong, boolean isExtended) {
            this.isStrong = isStrong;
            this.isExtended = isExtended;
        }
    }

    private static final Map<String, UpgradeFlags> brewedSlots = new HashMap<>();

    // Main method used by mixin to record brewed slot with upgrade info
    public static void markBrewedWithUpgrade(World world, BlockPos pos, int slot, boolean isStrong, boolean isExtended) {
        brewedSlots.put(getKey(world, pos, slot), new UpgradeFlags(isStrong, isExtended));
    }

    // Compatibility: basic markBrewed call just marks with false flags
    public static void markBrewed(World world, BlockPos pos, int slot) {
        markBrewedWithUpgrade(world, pos, slot, false, false);
    }

    // Access upgrade flags AND consume the slot
    public static UpgradeFlags consumeBrewInfo(World world, BlockPos pos, int slot) {
        return brewedSlots.remove(getKey(world, pos, slot));
    }

    private static String getKey(World world, BlockPos pos, int slot) {
        return world.getRegistryKey().getValue() + ":" + pos.toShortString() + ":" + slot;
    }
}
