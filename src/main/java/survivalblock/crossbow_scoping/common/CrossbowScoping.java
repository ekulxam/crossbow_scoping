package survivalblock.crossbow_scoping.common;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.crossbow_scoping.client.networking.ScopedCrossbowC2SPayload;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.function.Predicate;

public class CrossbowScoping implements ModInitializer {

	public static final String MOD_ID = "crossbow_scoping";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CrossbowScopingDataComponentTypes.init();
		CrossbowScopingGameRules.init();

		PayloadTypeRegistry.playC2S().register(ScopedCrossbowC2SPayload.ID, ScopedCrossbowC2SPayload.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ScopedCrossbowC2SPayload.ID, ScopedCrossbowC2SPayload.Receiver.INSTANCE);
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static Pair<ItemStack, InteractionHand> getCrossbowWithScope(Player player) {
		return getCrossbowWithScope(player, false, false);
	}

	public static Pair<ItemStack, InteractionHand> getCrossbowWithScope(Player player, boolean checkLoaded, boolean checkCooldown) {
        Predicate<ItemStack> predicate = (stack) ->
                isValidCrossbow(stack)
                        && !stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()
                        && (!checkLoaded || isLoaded(stack))
                        && (!checkCooldown || !player.getCooldowns().isOnCooldown(stack.getItem()));
		ItemStack stack = player.getUseItem();
		if (predicate.test(stack)) {
			return Pair.of(stack, player.getUsedItemHand());
		}
		for (InteractionHand hand : InteractionHand.values()) {
			stack = player.getItemInHand(hand);
			if (predicate.test(stack)) {
				return Pair.of(stack, hand);
			}
		}
		return Pair.of(ItemStack.EMPTY, InteractionHand.MAIN_HAND);
	}

	public static boolean isValidCrossbow(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof CrossbowItem && !stack.is(CrossbowScopingTags.INCOMPATIBLE_ITEMS);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isLoaded(ItemStack stack) {
		return isLoaded(stack, false);
	}

    public static boolean isLoaded(ItemStack stack, boolean checkLoading) {
		if (!isValidCrossbow(stack)) {
			return false;
		}
		if (checkLoading && stack.has(CrossbowScopingDataComponentTypes.LOADING_PHASE)) {
			return false;
		}
		ChargedProjectiles chargedProjectilesComponent = stack.get(DataComponents.CHARGED_PROJECTILES);
		return chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty();
	}

	public static boolean canInsertSpyglass(ItemStack crossbow, ItemStack potentialSpyglass) {
		return crossbow.is(CrossbowScopingTags.INCOMPATIBLE_ITEMS) ? potentialSpyglass.isEmpty() : (potentialSpyglass.isEmpty() || potentialSpyglass.getItem() instanceof SpyglassItem);
	}
}