package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.GameRules;

@Debug(export = true)
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
    @Unique
    private final ThreadLocal<ItemStack> crossbow_scoping$weaponNet = ThreadLocal.withInitial(() -> ItemStack.EMPTY);

    @WrapMethod(method = "shoot")
    private void captureParamsAndHope(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, LivingEntity target, Operation<Void> original, @Share("stack") LocalRef<ItemStack> stackRef) {
        this.crossbow_scoping$weaponNet.set(weapon);

        try {
            original.call(level, shooter, hand, weapon, projectileItems, velocity, inaccuracy, isCrit, target);
        } catch (Throwable throwable) {
            this.crossbow_scoping$weaponNet.set(ItemStack.EMPTY);
            throw throwable;
        }

        this.crossbow_scoping$weaponNet.set(ItemStack.EMPTY);
    }

    @Inject(method = "method_61659", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;shootProjectile(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/projectile/Projectile;IFFFLnet/minecraft/world/entity/LivingEntity;)V"))
    private void sniperVelocity(LivingEntity livingEntity, int i, float f, float g, float h, LivingEntity livingEntity2, Projectile projectile, CallbackInfo ci) {
        if (!(livingEntity.level() instanceof ServerLevel world)) {
            return;
        }

        ItemStack stack = this.crossbow_scoping$weaponNet.get();
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
    }
}
