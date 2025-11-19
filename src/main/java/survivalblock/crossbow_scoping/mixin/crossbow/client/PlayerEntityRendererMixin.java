package survivalblock.crossbow_scoping.mixin.crossbow.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;

@Mixin(PlayerRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("RETURN"))
    private void extractAStackOfTables(AbstractClientPlayer entity, PlayerRenderState state, float partialTick, CallbackInfo ci) {
        state.setData(ScopeRenderer.STACK_OF_TABLES, ScopeRenderer.StackOfTables.populate(entity));
    }
}
