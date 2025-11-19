package survivalblock.crossbow_scoping.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

import java.util.Map;

@Environment(EnvType.CLIENT)
public final class ScopeRenderer {

    public static final RenderStateDataKey<Boolean> IS_SPYGLASS = RenderStateDataKey.create(CrossbowScoping.id("spyglass")::toString);
    public static final RenderStateDataKey<ItemStack> SCOPE = RenderStateDataKey.create(CrossbowScoping.id("scope")::toString);
    public static final RenderStateDataKey<ItemStack> REVERSE_REFERENCE = RenderStateDataKey.create(CrossbowScoping.id("whattheheckisthis")::toString);

    public static final RenderStateDataKey<StackOfTables> STACK_OF_TABLES = RenderStateDataKey.create(CrossbowScoping.id("stack_of_tables")::toString);

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
        matrices.translate(-0.707, 0.1, 0.6);
        itemRenderer.renderStatic(scope, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, light, overlay, matrices, vertexConsumers, Minecraft.getInstance().level, 0);
        matrices.popPose();
    }

    public record StackOfTables(Map<InteractionHand, Map<Boolean, ItemStack>> map, InteractionHand activeHand) {

        public static final StackOfTables EMPTY = new StackOfTables(ImmutableMap.of(), InteractionHand.OFF_HAND);

        public static StackOfTables populate(Player player) {
            ImmutableMap.Builder<InteractionHand, Map<Boolean, ItemStack>> outerBuilder = ImmutableMap.builder();

            for (InteractionHand hand : new InteractionHand[]{InteractionHand.MAIN_HAND, InteractionHand.OFF_HAND}) {
                ImmutableMap.Builder<Boolean, ItemStack> innerBuilder = ImmutableMap.builder();

                innerBuilder.put(false, player.getItemInHand(hand));

                player.crossbow_scoping$setAttacking(true);
                innerBuilder.put(true, player.getItemInHand(hand));
                player.crossbow_scoping$setAttacking(false);

                outerBuilder.put(hand, innerBuilder.build());
            }

            return new StackOfTables(outerBuilder.build(), player.getUsedItemHand());
        }

        public ItemStack get(boolean attacking, InteractionHand hand) {
            return this.map.get(hand).get(attacking);
        }

        public ItemStack get(boolean attacking) {
            return this.get(attacking, this.activeHand);
        }
    }
}
