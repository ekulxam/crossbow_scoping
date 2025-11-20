package survivalblock.crossbow_scoping.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

@Environment(EnvType.CLIENT)
public final class ScopeRenderer {

    public static final RenderStateDataKey<ItemStack> SCOPE = RenderStateDataKey.create(CrossbowScoping.id("scope")::toString);
    public static final RenderStateDataKey<ItemStack> REVERSE_REFERENCE = RenderStateDataKey.create(CrossbowScoping.id("whattheheckisthis")::toString);

    private ScopeRenderer() {
    }

    public static void renderScopeOnCrossbow(ItemStack /*? <=1.21.1 {*/ /*stack *//*?} else {*/ scope /*?}*/, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ItemRenderer itemRenderer) {
        //? if <=1.21.1 {
        /*if (!(stack.getItem() instanceof CrossbowItem)) {
            return;
        }
        ItemStack scope = stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (scope == null || scope.isEmpty()) {
            return;
        }
        *///?}
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(-135));
        //? if <=1.21.1 {
        /*matrices.translate(-0.707, 0.1, 0.6);
        *///?} else {
        matrices.translate(0, 0.0, 0.1);
        //?}
        itemRenderer.renderStatic(scope, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, light, overlay, matrices, vertexConsumers, Minecraft.getInstance().level, 0);
        matrices.popPose();
    }
}
