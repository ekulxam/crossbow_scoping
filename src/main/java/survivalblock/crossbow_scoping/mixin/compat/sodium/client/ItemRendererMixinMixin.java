package survivalblock.crossbow_scoping.mixin.compat.sodium.client;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;

@Mixin(value = ItemRenderer.class, priority = 3000)
public abstract class ItemRendererMixinMixin {

    @TargetHandler(mixin = "net.caffeinemc.mods.sodium.mixin.features.render.frapi.ItemRendererMixin", name = "beforeRenderItem")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/frapi/render/ItemRenderContext;renderModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", shift = At.Shift.AFTER))
    private void sodiumBrokeMyMod(ItemStack stack, ModelTransformationMode renderMode, boolean invert, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci, CallbackInfo ciSquared) {
        ScopeRenderer.renderScopeOnCrossbow(stack, matrices, vertexConsumers, light, overlay, (ItemRenderer) (Object) this);
    }
}
