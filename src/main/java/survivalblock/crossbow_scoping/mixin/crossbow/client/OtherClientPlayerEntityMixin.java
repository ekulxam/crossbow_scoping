package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.mixin.crossbow.PlayerEntityMixin;

@Mixin(RemotePlayer.class)
public abstract class OtherClientPlayerEntityMixin extends PlayerEntityMixin {

    @Override
    protected boolean useSpyglassAsOtherClientPlayer(ItemStack stack, ItemStack other, Operation<Boolean> original) {
        if (super.useSpyglassAsOtherClientPlayer(stack, other, original)) {
            return true;
        }

        if (!CrossbowScoping.isLoaded(other)) {
            return false;
        }

        ItemStack maybeScope = other.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (maybeScope.isEmpty()) {
            return false;
        }
        return original.call(stack, maybeScope);
    }
}
