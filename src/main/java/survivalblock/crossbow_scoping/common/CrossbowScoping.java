package survivalblock.crossbow_scoping.common;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.crossbow_scoping.client.networking.ScopedCrossbowC2SPayload;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

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

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static Pair<ItemStack, Hand> getCrossbowWithScope(PlayerEntity player) {
		ItemStack stack = player.getActiveItem();
		if (isValidCrossbow(stack) && !stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
			return Pair.of(stack, player.getActiveHand());
		}
		for (Hand hand : Hand.values()) {
			stack = player.getStackInHand(hand);
			if (isValidCrossbow(stack) && !stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
				return Pair.of(stack, hand);
			}
		}
		return Pair.of(ItemStack.EMPTY, Hand.MAIN_HAND);
	}

	public static boolean isValidCrossbow(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof CrossbowItem && !stack.isIn(CrossbowScopingTags.INCOMPATIBLE_ITEMS);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isLoaded(ItemStack stack) {
		return isLoaded(stack, false);
	}

    public static boolean isLoaded(ItemStack stack, boolean checkLoaded) {
		if (!isValidCrossbow(stack)) {
			return false;
		}
		if (checkLoaded && stack.contains(CrossbowScopingDataComponentTypes.LOADING_PHASE)) {
			return false;
		}
		ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
		return chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty();
	}

	public static boolean canInsertSpyglass(ItemStack crossbow, ItemStack potentialSpyglass) {
		return crossbow.isIn(CrossbowScopingTags.INCOMPATIBLE_ITEMS) ? potentialSpyglass.isEmpty() : (potentialSpyglass.isEmpty() || potentialSpyglass.getItem() instanceof SpyglassItem);
	}
}