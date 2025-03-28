package survivalblock.crossbow_scoping.common.datagen;

import archives.tater.omnicrossbow.OmniCrossbow;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingTagGenerator {

    public static class CrossbowScopingEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

        public CrossbowScopingEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagProvider<EntityType<?>>.FabricTagBuilder allowNoGravityBuilder = getOrCreateTagBuilder(CrossbowScopingTags.ALLOW_NO_GRAVITY);
            allowNoGravityBuilder.add(EntityType.ARROW);
            allowNoGravityBuilder.add(EntityType.SPECTRAL_ARROW);
            allowNoGravityBuilder.add(EntityType.FIREWORK_ROCKET);
        }
    }

    public static class CrossbowScopingEnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {

        public CrossbowScopingEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagProvider<Enchantment>.FabricTagBuilder usesExtendedCooldownBuilder = getOrCreateTagBuilder(CrossbowScopingTags.USES_EXTENDED_COOLDOWN);
            usesExtendedCooldownBuilder.addOptional(OmniCrossbow.MULTICHAMBERED);
        }
    }

    public static class CrossbowScopingItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        public CrossbowScopingItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagProvider<Item>.FabricTagBuilder incompatibleItems = getOrCreateTagBuilder(CrossbowScopingTags.INCOMPATIBLE_ITEMS);
            incompatibleItems.addOptional(Identifier.of("pierced", "long_crossbow"));
        }
    }
}
