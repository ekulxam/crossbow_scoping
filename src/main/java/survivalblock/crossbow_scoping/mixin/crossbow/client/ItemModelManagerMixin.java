//? if >1.21.1 {
package survivalblock.crossbow_scoping.mixin.crossbow.client;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@SuppressWarnings("UnusedMixin")
@Mixin(ItemModelResolver.class)
public class ItemModelManagerMixin {

    @Inject(method = "appendItemLayers", at = @At("HEAD"))
    private void addCrossbowScopingData(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity entity, int seed, CallbackInfo ci) {
        if (CrossbowScoping.isASpyglass(stack)) {
            if (entity instanceof Player player && !stack.isEmpty()) {
                player.crossbow_scoping$setAttacking(true);
                ItemStack crossbow = player.getItemInHand(player.getUsedItemHand());
                player.crossbow_scoping$setAttacking(false);

                if (ItemStack.matches(stack, crossbow.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY))) {
                    renderState.setData(ScopeRenderer.REVERSE_REFERENCE, crossbow);
                    renderState.appendModelIdentityElement(crossbow.copyWithCount(1));
                }
            }

            return;
        }

        if (CrossbowScoping.isValidCrossbow(stack)) {
            ItemStack scope = stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
            if (!scope.isEmpty()) {
                renderState.setData(ScopeRenderer.SCOPE, scope);
                renderState.appendModelIdentityElement(scope.copyWithCount(1));
            }
        }
    }
}
//?}