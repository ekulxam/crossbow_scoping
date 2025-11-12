package survivalblock.crossbow_scoping.client.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

public final class ScopedCrossbowC2SPayload implements CustomPayload {

    public static final ScopedCrossbowC2SPayload RIGHT = new ScopedCrossbowC2SPayload(false);
    public static final ScopedCrossbowC2SPayload LEFT = new ScopedCrossbowC2SPayload(true);

    public static final Id<ScopedCrossbowC2SPayload> ID = new Id<>(CrossbowScoping.id("scoped_crossbow_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, ScopedCrossbowC2SPayload> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public ScopedCrossbowC2SPayload decode(RegistryByteBuf buf) {
            return PacketCodecs.BOOL.decode(buf) ? LEFT : RIGHT;
        }

        @Override
        public void encode(RegistryByteBuf buf, ScopedCrossbowC2SPayload value) {
            PacketCodecs.BOOL.encode(buf, value.leftHand);
        }
    };

    private final boolean leftHand;

    private ScopedCrossbowC2SPayload(boolean leftHand) {
        this.leftHand = leftHand;
    }

    @NotNull
    public static ScopedCrossbowC2SPayload fromHand(Hand hand) {
        return hand == Hand.OFF_HAND ? LEFT : RIGHT;
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
            Hand hand = payload.leftHand ? Hand.OFF_HAND : Hand.MAIN_HAND;
            player.crossbow_scoping$setAttacking(true);
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isEmpty() || stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty() || !CrossbowScoping.isLoaded(stack) || player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                return;
            }
            stack.use(player.getWorld(), player, hand);
            player.crossbow_scoping$setAttacking(false);
        }
    }
}