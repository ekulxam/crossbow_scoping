package survivalblock.crossbow_scoping.common.init;

import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingDataComponentTypes {

    public static final ComponentType<ItemStack> CROSSBOW_SCOPE = ComponentType.<ItemStack>builder().codec(ItemStack.OPTIONAL_CODEC).packetCodec(ItemStack.OPTIONAL_PACKET_CODEC).build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, CrossbowScoping.id("crossbow_scope"), CROSSBOW_SCOPE);
    }
}
