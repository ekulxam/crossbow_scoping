package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Mixin(ItemUsage.class)
public class ItemUsageMixin {

    @ModifyExpressionValue(method = "consumeHeldItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack useScopeInstead(ItemStack original, World world, PlayerEntity player, Hand hand) {
        if (!(original.getItem() instanceof CrossbowItem)) {
            return original;
        }
        ItemStack stackInComponents = original.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            ((LivingEntityAccessor) player).crossbow_scoping$setActiveItem(stackInComponents);
            return original;
        }
        return original;
    }
}
