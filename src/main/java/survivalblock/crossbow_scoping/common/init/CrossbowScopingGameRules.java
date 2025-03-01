package survivalblock.crossbow_scoping.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class CrossbowScopingGameRules {

    public static final GameRules.Key<GameRules.BooleanRule> HIGHER_PRECISION = GameRuleRegistry.register("crossbow_scoping:higherPrecision", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> HIGHER_VELOCITY = GameRuleRegistry.register("crossbow_scoping:higherVelocity", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<DoubleRule> VELOCITY_MULTIPLIER = GameRuleRegistry.register("crossbow_scoping:velocityMultiplier", GameRules.Category.MISC, GameRuleFactory.createDoubleRule(2.5, 1.0, 45));
    public static final GameRules.Key<GameRules.BooleanRule> NO_GRAVITY_PROJECTILES = GameRuleRegistry.register("crossbow_scoping:noGravityProjectiles", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));

    public static void init() {

    }
}
