package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.common.injected_interface.CrossbowAttackingPlayer;

import java.util.Map;

@Mixin(value = Player.class, priority = 10000)
public abstract class PlayerEntityMixin extends LivingEntityMixin implements CrossbowAttackingPlayer {

    @Unique
    private boolean crossbow_scoping$attacking = false;
    @Unique
    private ItemStack crossbow_scoping$initialScope = ItemStack.EMPTY;

    @Override
    public void crossbow_scoping$setAttacking(boolean attacking) {
        this.crossbow_scoping$attacking = attacking;
    }

    @Override
    public boolean crossbow_scoping$isAttacking() {
        return this.crossbow_scoping$attacking;
    }

    @Override
    public void crossbow_scoping$setStartingToScope(ItemStack scope) {
        this.crossbow_scoping$initialScope = scope;
    }

    @Override
    public boolean crossbow_scoping$usingScope(ItemStack crossbow) {
        if (this.crossbow_scoping$attacking || crossbow.isEmpty()) {
            return false;
        }
        ItemStack scope;
        if (CrossbowScoping.isValidCrossbow(crossbow)) {
            scope = crossbow.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        } else if (CrossbowScoping.isASpyglass(crossbow)) {
            scope = crossbow; // in the case that I accidentally pass in the spyglass
        } else {
            scope = ItemStack.EMPTY;
        }
        if (scope.isEmpty()) {
            return false;
        }
        if (!this.crossbow_scoping$initialScope.isEmpty()) {
            boolean initial = ItemStack.matches(this.crossbow_scoping$initialScope, scope);
            if (initial) {
                return true;
            }
        }
        ItemStack active = this.getUseItem();
        if (active == null || active.isEmpty()) {
            return false;
        }
        return ItemStack.matches(active, scope);
    }

    //? if <=1.21.4 {
    @WrapOperation(method = "setItemSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private Object setScopeStack(NonNullList<?> instance, int i, Object object, Operation<Object> original) {
        return CrossbowScoping.setScopeStack(instance, i, object, (Player) (Object) this, original);
    }
    //?}

    /*
            begin credit
            Adapted from https://github.com/ekulxam/amarong/blob/f8264bdf61751705497ecca122e5d655c067eba4/src/main/java/survivalblock/amarong/mixin/staff/PlayerEntityMixin.java#L13
            The MIT license can be found in PlayerInventoryMixin
             */
    //? if <=1.21.1 {
    @ModifyReturnValue(method = "getItemBySlot", at = @At("RETURN"))
    //?} else {
    /*@Override
    *///?}
    protected ItemStack returnSpyglassStack(ItemStack original) {
        if (this.crossbow_scoping$usingScope(original)) {
            return this.replaceWithScope(original);
        }
        return original;
    }
    // end credit

    @Override
    protected void swapCorrectly(Operation<Void> original) {
        this.crossbow_scoping$setAttacking(true);
        super.swapCorrectly(original);
        this.crossbow_scoping$setAttacking(false);
    }

    @Override
    protected Map<EquipmentSlot, ItemStack> forgetScopePseudochange(Operation<Map<EquipmentSlot, ItemStack>> original) {
        this.crossbow_scoping$setAttacking(true);
        Map<EquipmentSlot, ItemStack> result = super.forgetScopePseudochange(original);
        this.crossbow_scoping$setAttacking(false);
        return result;
    }

    @Override
    protected void syncCrossbowOnEquipmentChange(Map<EquipmentSlot, ItemStack> equipments, Operation<Void> original) {
        this.crossbow_scoping$setAttacking(true);
        super.syncCrossbowOnEquipmentChange(equipments, original);
        this.crossbow_scoping$setAttacking(false);
    }
}
