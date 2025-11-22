//? if <=1.21.1 {
/*package survivalblock.crossbow_scoping.mixin.compat.sodium.client;

import com.bawnorton.mixinsquared.TargetHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;

@SuppressWarnings("UnusedMixin")
@Mixin(value = ItemRenderer.class, priority = 3000)
public abstract class ItemRendererMixinMixin {

    @TargetHandler(mixin = "net.caffeinemc.mods.sodium.mixin.features.render.frapi.ItemRendererMixin", name = "beforeRenderItem")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/frapi/render/ItemRenderContext;renderModel(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V", shift = At.Shift.AFTER, remap = true), remap = false)
    private void sodiumBrokeMyMod(ItemStack stack, ItemDisplayContext renderMode, boolean invert, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci, CallbackInfo ciSquared) {
        ScopeRenderer.renderScopeOnCrossbow(stack, matrices, vertexConsumers, light, overlay, (ItemRenderer) (Object) this);
    }
}
*///?}