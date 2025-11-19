package survivalblock.crossbow_scoping.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingGameRules;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingEnUsLangGenerator extends FabricLanguageProvider {

    protected CrossbowScopingEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("item.crossbow_scoping.crossbow.scope", "Scope - %s");

        translationBuilder.add(CrossbowScopingGameRules.HIGHER_PRECISION.getDescriptionId(), "Crossbow Scoping - Higher Precision");
        translationBuilder.add(CrossbowScopingGameRules.HIGHER_VELOCITY.getDescriptionId(), "Crossbow Scoping - Higher Velocity");
        translationBuilder.add(CrossbowScopingGameRules.VELOCITY_MULTIPLIER.getDescriptionId(), "Crossbow Scoping - Velocity Multiplier");
        translationBuilder.add(CrossbowScopingGameRules.NO_GRAVITY_PROJECTILES.getDescriptionId(), "Crossbow Scoping - Allow No Gravity Projectiles");
    }
}
