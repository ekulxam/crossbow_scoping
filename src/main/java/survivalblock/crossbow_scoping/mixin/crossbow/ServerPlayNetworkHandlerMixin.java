package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayer player;

    @WrapMethod(method = "handlePlayerAction")
    private void crossbowAction(ServerboundPlayerActionPacket packet, Operation<Void> original) {
        this.player.crossbow_scoping$setAttacking(true);
        original.call(packet);
        this.player.crossbow_scoping$setAttacking(false);
    }
}
