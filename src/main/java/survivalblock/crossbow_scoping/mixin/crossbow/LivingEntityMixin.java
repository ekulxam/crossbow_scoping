package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(method = {"tickActiveItemStack", "consumeItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack replaceWithScope(ItemStack original) {
        if (!(original.getItem() instanceof CrossbowItem)) {
            return original;
        }
        if (!CrossbowScoping.isLoaded(original, true)) {
            return original;
        }
        ItemStack stackInComponents = original.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            return stackInComponents;
        }
        return original;
    }

}
