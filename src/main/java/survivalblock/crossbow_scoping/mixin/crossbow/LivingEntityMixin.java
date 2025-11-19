package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Mixin(value = LivingEntity.class, priority = 10000)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract ItemStack getUseItem();

    @Shadow
    public abstract boolean isUsingItem();

    @ModifyExpressionValue(method = {"updatingUsingItem", "completeUsingItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    protected ItemStack replaceWithScope(ItemStack original) {
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
