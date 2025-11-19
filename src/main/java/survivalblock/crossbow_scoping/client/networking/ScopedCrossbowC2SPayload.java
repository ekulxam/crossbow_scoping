package survivalblock.crossbow_scoping.client.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@SuppressWarnings("ClassCanBeRecord")
public final class ScopedCrossbowC2SPayload implements CustomPacketPayload {

    public static final ScopedCrossbowC2SPayload RIGHT = new ScopedCrossbowC2SPayload(false);
    public static final ScopedCrossbowC2SPayload LEFT = new ScopedCrossbowC2SPayload(true);

    public static final Type<ScopedCrossbowC2SPayload> ID = new Type<>(CrossbowScoping.id("scoped_crossbow_c2s_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ScopedCrossbowC2SPayload> PACKET_CODEC = new StreamCodec<>() {
        @Override
        public ScopedCrossbowC2SPayload decode(RegistryFriendlyByteBuf buf) {
            return ByteBufCodecs.BOOL.decode(buf) ? LEFT : RIGHT;
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, ScopedCrossbowC2SPayload value) {
            ByteBufCodecs.BOOL.encode(buf, value.leftHand);
        }
    };

    private final boolean leftHand;

    private ScopedCrossbowC2SPayload(boolean leftHand) {
        this.leftHand = leftHand;
    }

    @NotNull
    public static ScopedCrossbowC2SPayload fromHand(InteractionHand hand) {
        return hand == InteractionHand.OFF_HAND ? LEFT : RIGHT;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ScopedCrossbowC2SPayload> {

        public static final Receiver INSTANCE = new Receiver();

        private Receiver() {

        }

        @Override
        public void receive(ScopedCrossbowC2SPayload payload, ServerPlayNetworking.Context context) {
            Player player = context.player();
            InteractionHand hand = payload.leftHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            player.crossbow_scoping$setAttacking(true);
            ItemStack stack = player.getItemInHand(hand);
            if (stack.isEmpty() || stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty() || !CrossbowScoping.isLoaded(stack) || player.getCooldowns().isOnCooldown(stack.getItem())) {
                return;
            }
            stack.use(player.level(), player, hand);
            player.crossbow_scoping$setAttacking(false);
        }
    }
}