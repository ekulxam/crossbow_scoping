package survivalblock.crossbow_scoping.client.networking;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Hand;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public final class ScopedCrossbowC2SPayload implements CustomPayload {

    public static final ScopedCrossbowC2SPayload INSTANCE = new ScopedCrossbowC2SPayload();

    public static final Id<ScopedCrossbowC2SPayload> ID = new Id<>(CrossbowScoping.id("scoped_crossbow_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, ScopedCrossbowC2SPayload> PACKET_CODEC = PacketCodec.unit(INSTANCE);

    private ScopedCrossbowC2SPayload() {

    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ScopedCrossbowC2SPayload> {

        public static final Receiver INSTANCE = new Receiver();

        private Receiver() {

        }

        @Override
        public void receive(ScopedCrossbowC2SPayload payload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();
            Pair<ItemStack, Hand> pair = CrossbowScoping.getCrossbowWithScope(player);
            ItemStack stack = pair.getFirst();
            if (stack.isEmpty() || !CrossbowScoping.isLoaded(stack)) {
                return;
            }
            player.crossbow_scoping$setAttacking(true);
            stack.use(player.getWorld(), player, pair.getSecond());
            player.crossbow_scoping$setAttacking(false);
        }
    }
}