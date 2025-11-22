package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
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
import java.util.function.Consumer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.GameRules;

@Mixin(ProjectileWeaponItem.class)
public class RangedWeaponItemMixin {

    @ModifyVariable(method = "shoot", at = @At("HEAD"), index = 7, argsOnly = true)
    private float sniperPrecision(float value, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ServerLevel serverWorld) {
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

    //? if <=1.21.1 {
    /*@Inject(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;shootProjectile(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/projectile/Projectile;IFFFLnet/minecraft/world/entity/LivingEntity;)V", shift = At.Shift.AFTER))
    private void sniperVelocity(ServerLevel world, LivingEntity shooter, InteractionHand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, @Nullable LivingEntity target, CallbackInfo ci, @Local Projectile projectile) {
    *///?} else {
    @WrapOperation(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;spawnProjectile(Lnet/minecraft/world/entity/projectile/Projectile;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)Lnet/minecraft/world/entity/projectile/Projectile;"))
    private Projectile sniperVelocity(Projectile first, ServerLevel world, ItemStack projectileStack, Consumer<Projectile> consumer, Operation<Projectile> original, @Local(argsOnly = true) ItemStack stack) {
        Consumer<Projectile> sniper = projectile -> {
            consumer.accept(projectile);
        //?}
            if (!(stack.getItem() instanceof CrossbowItem)) {
                return;
            }
            if (stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
                return;
            }
            GameRules gameRules = world.getGameRules();
            if (gameRules.getBoolean(CrossbowScopingGameRules.HIGHER_VELOCITY)) {
                projectile.setDeltaMovement(projectile.getDeltaMovement().scale(gameRules.getRule(CrossbowScopingGameRules.VELOCITY_MULTIPLIER).get()));
            }
            if (gameRules.getBoolean(CrossbowScopingGameRules.NO_GRAVITY_PROJECTILES) && projectile.getType().is(CrossbowScopingTags.ALLOW_NO_GRAVITY)) {
                projectile.setNoGravity(true);
            }
            return;
        //? if >1.21.1 {
        };
        return original.call(first, world, projectileStack, sniper);
        //?}
    }
}
