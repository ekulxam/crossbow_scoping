package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;

@SuppressWarnings("NonExtendableApiUsage")
@Mixin(ItemStackRenderState.class)
public abstract class ItemRenderStateMixin implements FabricRenderState {

    @Inject(method = "render", at = @At("RETURN"))
    private void renderScope(PoseStack matrices, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        ItemStack scope = this.getDataOrDefault(ScopeRenderer.SCOPE, ItemStack.EMPTY);

        if (scope.isEmpty()) {
            return;
        }

        ScopeRenderer.renderScopeOnCrossbow(scope, matrices, bufferSource, packedLight, packedOverlay, Minecraft.getInstance().getItemRenderer());
    }
}
