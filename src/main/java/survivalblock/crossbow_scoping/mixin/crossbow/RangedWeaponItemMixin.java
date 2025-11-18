package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.List;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {

    @ModifyVariable(method = "shootAll", at = @At("HEAD"), index = 7, argsOnly = true)
    private float sniperPrecision(float value, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ServerWorld serverWorld) {
        if (!(stack.getItem() instanceof CrossbowItem)) {
            return value;
        }
        if (stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
            return value;
        }
        if (serverWorld.getGameRules().getBoolean(CrossbowScopingGameRules.HIGHER_PRECISION)) {
            return value * 0.2f;
        }
        return value;
    }

    @Inject(method = "shootAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/RangedWeaponItem;shoot(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/projectile/ProjectileEntity;IFFFLnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.AFTER))
    private void sniperVelocity(ServerWorld world, LivingEntity shooter, Hand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, @Nullable LivingEntity target, CallbackInfo ci, @Local ProjectileEntity projectile) {
        if (!(stack.getItem() instanceof CrossbowItem)) {
            return;
        }
        if (stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
            return;
        }
        GameRules gameRules = world.getGameRules();
        if (gameRules.getBoolean(CrossbowScopingGameRules.HIGHER_VELOCITY)) {
            projectile.setVelocity(projectile.getVelocity().multiply(gameRules.get(CrossbowScopingGameRules.VELOCITY_MULTIPLIER).get()));
        }
        if (gameRules.getBoolean(CrossbowScopingGameRules.NO_GRAVITY_PROJECTILES) && projectile.getType().isIn(CrossbowScopingTags.ALLOW_NO_GRAVITY)) {
            projectile.setNoGravity(true);
        }
    }
}
