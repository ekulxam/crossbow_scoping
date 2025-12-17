package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(ServerEntity.class)
public class EntityTrackerEntryMixin {

    @WrapMethod(method = "sendPairingData")
    private void syncCrossbow(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> consumer, Operation<Void> original) {
        player.crossbow_scoping$setAttacking(true);
        original.call(player, consumer);
        player.crossbow_scoping$setAttacking(false);
    }
}
