package survivalblock.crossbow_scoping.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingEnUsLangGenerator extends FabricLanguageProvider {

    protected CrossbowScopingEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("item.crossbow_scoping.crossbow.scope", "Scope - %s");

        translationBuilder.add(CrossbowScopingGameRules.HIGHER_PRECISION.getTranslationKey(), "Crossbow Scoping - Higher Precision");
        translationBuilder.add(CrossbowScopingGameRules.HIGHER_VELOCITY.getTranslationKey(), "Crossbow Scoping - Higher Velocity");
        translationBuilder.add(CrossbowScopingGameRules.VELOCITY_MULTIPLIER.getTranslationKey(), "Crossbow Scoping - Velocity Multiplier");
        translationBuilder.add(CrossbowScopingGameRules.NO_GRAVITY_PROJECTILES.getTranslationKey(), "Crossbow Scoping - Allow No Gravity Projectiles");
    }
}
