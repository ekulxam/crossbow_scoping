package survivalblock.crossbow_scoping.common.datagen;

import archives.tater.omnicrossbow.OmniCrossbow;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingTagGenerator {

    public static class CrossbowScopingEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

        public CrossbowScopingEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            FabricTagProvider<EntityType<?>>.FabricTagBuilder allowNoGravityBuilder = getOrCreateTagBuilder(CrossbowScopingTags.ALLOW_NO_GRAVITY);
            allowNoGravityBuilder.add(EntityType.ARROW);
            allowNoGravityBuilder.add(EntityType.SPECTRAL_ARROW);
            allowNoGravityBuilder.add(EntityType.FIREWORK_ROCKET);
        }
    }

    public static class CrossbowScopingEnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {

        public CrossbowScopingEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            FabricTagProvider<Enchantment>.FabricTagBuilder usesExtendedCooldownBuilder = getOrCreateTagBuilder(CrossbowScopingTags.USES_EXTENDED_COOLDOWN);
            usesExtendedCooldownBuilder.addOptional(OmniCrossbow.id("multichambered"));
        }
    }

    public static class CrossbowScopingItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        private static final String PIERCED = "pierced";

        public CrossbowScopingItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            FabricTagProvider<Item>.FabricTagBuilder incompatibleItems = getOrCreateTagBuilder(CrossbowScopingTags.INCOMPATIBLE_ITEMS);
            incompatibleItems.addOptional(ResourceLocation.fromNamespaceAndPath(PIERCED, "long_crossbow"));
        }
    }
}
