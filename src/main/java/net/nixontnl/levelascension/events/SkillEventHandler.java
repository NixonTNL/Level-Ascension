package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.AxeItem;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.LivingEntity;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.cooking.CookingSkillManager;
import net.nixontnl.levelascension.skills.logic.excavation.ExcavationSkillManager;
import net.nixontnl.levelascension.skills.logic.farming.FarmingSkillManager;
import net.nixontnl.levelascension.skills.logic.mining.MiningSkillManager;
import net.nixontnl.levelascension.skills.logic.woodcutting.WoodcuttingSkillManager;
import net.nixontnl.levelascension.skills.logic.melee.MeleeSkillManager;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

import java.util.HashMap;
import java.util.UUID;

public class SkillEventHandler {

    public static final HashMap<UUID, PlayerSkillData> playerSkillDataMap = new HashMap<>();

    public static void register() {

        // Block breaking (Mining, Woodcutting, Excavation, Farming)
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

            UUID id = player.getUuid();
            playerSkillDataMap.putIfAbsent(id, new PlayerSkillData());
            PlayerSkillData data = playerSkillDataMap.get(id);

            Block brokenBlock = state.getBlock();

            // Mining
            int miningXp = MiningSkillManager.getXpForBlock(brokenBlock);
            if (miningXp > 0) {
                data.addXP(SkillType.MINING, serverPlayer, miningXp);
            }

            // Woodcutting
            int woodcuttingXp = WoodcuttingSkillManager.getXpForBlock(brokenBlock);
            if (woodcuttingXp > 0) {
                data.addXP(SkillType.WOODCUTTING, serverPlayer, woodcuttingXp);
            }

            // Excavation
            int excavationXp = ExcavationSkillManager.getXpForBlock(brokenBlock);
            if (excavationXp > 0) {
                data.addXP(SkillType.EXCAVATION, serverPlayer, excavationXp);
            }

            // Farming (only fully grown crops)
            if (brokenBlock instanceof CropBlock crop && crop.isMature(state)) {
                int farmingXp = FarmingSkillManager.getXpForBlock(brokenBlock);
                if (farmingXp > 0) {
                    data.addXP(SkillType.FARMING, serverPlayer, farmingXp);
                }
            }
        });

        // Tilling (Farming XP)
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemStack stack = player.getStackInHand(hand);

            if (stack.getItem() instanceof HoeItem &&
                    (state.isOf(Blocks.DIRT) || state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.COARSE_DIRT))) {

                PlayerSkillData data = getSkillData(player.getUuid());
                data.addXP(SkillType.FARMING, serverPlayer, 1); // 1 XP for tilling
            }

            return ActionResult.PASS;
        });

        // Planting (Farming XP)
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;

            ItemStack stack = player.getStackInHand(hand);
            Identifier itemId = Registries.ITEM.getId(stack.getItem());

            if (itemId.getNamespace().equals("minecraft") && (
                    itemId.getPath().contains("seeds") ||
                            itemId.getPath().contains("carrot") ||
                            itemId.getPath().contains("potato") ||
                            itemId.getPath().contains("beetroot") ||
                            itemId.getPath().contains("nether_wart")
            )) {
                PlayerSkillData data = getSkillData(player.getUuid());
                data.addXP(SkillType.FARMING, serverPlayer, 1); // 1 XP for planting
            }

            return ActionResult.PASS;
        });

        // Melee Combat (Melee XP)
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            if (!(killedEntity instanceof LivingEntity livingTarget)) return;

            Item heldItem = player.getMainHandStack().getItem();

            boolean isWeapon = heldItem instanceof SwordItem || heldItem instanceof AxeItem ||
                    heldItem.getTranslationKey().contains("mace"); // Optional: Add more logic here

            if (isWeapon) {
                MeleeSkillManager.handleMeleeXp(player, livingTarget);
            }
        });
    }

    public static void handleCookingXp(ServerPlayerEntity player, ItemStack stack, String sourceId) {
        PlayerSkillData data = getSkillData(player.getUuid());

        int xp = CookingSkillManager.getXpForCookedItem(stack.getItem(), sourceId);
        if (xp > 0) {
            data.addXP(SkillType.COOKING, player, xp);
            System.out.println("Gave " + xp + " Cooking XP to " + player.getName().getString());
        }
    }

    public static int getCookingXpPreview(ItemStack stack) {
        return CookingSkillManager.getXpForCookedItem(stack.getItem(), "minecraft:campfire");
    }

    public static PlayerSkillData getSkillData(UUID playerId) {
        playerSkillDataMap.putIfAbsent(playerId, new PlayerSkillData());
        return playerSkillDataMap.get(playerId);
    }
}
