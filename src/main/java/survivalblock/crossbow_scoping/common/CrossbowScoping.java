package survivalblock.crossbow_scoping.common;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.crossbow_scoping.client.networking.ScopedCrossbowC2SPayload;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;

public class CrossbowScoping implements ModInitializer {

	public static final String MOD_ID = "crossbow_scoping";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final boolean OMNICROSSBOW_LOADED = FabricLoader.getInstance().isModLoaded("omnicrossbow");

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
		if (stack.getItem() instanceof CrossbowItem && !stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
			return Pair.of(stack, player.getActiveHand());
		}
		for (Hand hand : Hand.values()) {
			stack = player.getStackInHand(hand);
			if (stack.getItem() instanceof CrossbowItem && !stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
				return Pair.of(stack, hand);
			}
		}
		return Pair.of(ItemStack.EMPTY, Hand.MAIN_HAND);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isLoaded(ItemStack stack) {
		ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
		return chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty();
	}
}
