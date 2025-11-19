package survivalblock.crossbow_scoping.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Environment(EnvType.CLIENT)
public final class ScopeRenderer {

    private ScopeRenderer() {
    }

    public static void renderScopeOnCrossbow(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ItemRenderer itemRenderer) {
        if (!(stack.getItem() instanceof CrossbowItem)) {
            return;
        }
        ItemStack otherStack = stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (otherStack == null || otherStack.isEmpty()) {
            return;
        }
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(-135));
        matrices.translate(-0.707, 0.1, 0.6);
        itemRenderer.renderStatic(otherStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, light, overlay, matrices, vertexConsumers, Minecraft.getInstance().level, 0);
        matrices.popPose();
    }
}
