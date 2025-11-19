package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class InGameHudMixin {

    @WrapOperation(method = "renderItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack useRealCrossbow(Player instance, Operation<ItemStack> original) {
        instance.crossbow_scoping$setAttacking(true);
        ItemStack crossbow = original.call(instance);
        instance.crossbow_scoping$setAttacking(false);
        return crossbow;
    }
}
