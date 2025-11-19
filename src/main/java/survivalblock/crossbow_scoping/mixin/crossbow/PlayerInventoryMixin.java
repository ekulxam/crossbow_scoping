package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Mixin(value = Inventory.class, priority = 10000)
/*
begin credit
Adapted from https://github.com/ekulxam/amarong/blob/f8264bdf61751705497ecca122e5d655c067eba4/src/main/java/survivalblock/amarong/mixin/staff/PlayerInventoryMixin.java#L17
Licensed under the MIT license, as follows:

MIT License

Copyright (c) 1:22 UTC 25 July 2025-present ekulxam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
public class PlayerInventoryMixin {

    @Shadow
    @Final
    public Player player;

    @ModifyExpressionValue(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;get(I)Ljava/lang/Object;"))
    private Object getScopeStack(Object original) {
        if (!(original instanceof ItemStack stack)) {
            return original;
        }
        if (!CrossbowScoping.isLoaded(stack, true)) {
            return original;
        }
        if (!this.player.crossbow_scoping$usingScope(stack)) {
            return original;
        }
        ItemStack stackInComponents = stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            return stackInComponents;
        }
        return original;
    }

    @WrapOperation(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private Object setScopeStack(NonNullList<?> instance, int index, Object element, Operation<Object> original) {
        if (!(element instanceof ItemStack stack)) {
            return original.call(instance, index, element);
        }
        Object obj = instance.get(index);
        if (!(obj instanceof ItemStack crossbow)) {
            return original.call(instance, index, element);
        }
        if (!CrossbowScoping.isLoaded(crossbow, true)) {
            return original.call(instance, index, element);
        }
        if (!this.player.crossbow_scoping$usingScope(crossbow)) {
            return original.call(instance, index, element);
        }
        if (stack.isEmpty()) {
            return crossbow.remove(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE);
        } else if (stack.getItem() instanceof SpyglassItem) {
            return crossbow.set(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, stack);
        }
        return original;
    }
}
// end credit