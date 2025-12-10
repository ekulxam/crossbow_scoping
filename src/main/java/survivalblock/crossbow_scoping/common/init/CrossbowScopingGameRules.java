package survivalblock.crossbow_scoping.common.init;

//? if <1.21.11 {
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.level.GameRules;
//?} else {
/*import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
*///?}
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingGameRules {

    //? if <1.21.11 {
    public static final GameRules.Key<GameRules.BooleanValue> HIGHER_PRECISION = GameRuleRegistry.register("crossbow_scoping:higherPrecision", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> HIGHER_VELOCITY = GameRuleRegistry.register("crossbow_scoping:higherVelocity", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<DoubleRule> VELOCITY_MULTIPLIER = GameRuleRegistry.register("crossbow_scoping:velocityMultiplier", GameRules.Category.MISC, GameRuleFactory.createDoubleRule(2.5, 1.0, 45));
    public static final GameRules.Key<GameRules.BooleanValue> NO_GRAVITY_PROJECTILES = GameRuleRegistry.register("crossbow_scoping:noGravityProjectiles", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
    //?} else {
    /*public static final GameRule<Boolean> HIGHER_PRECISION = GameRuleBuilder.forBoolean(true).buildAndRegister(CrossbowScoping.id("higher_precision"));
    public static final GameRule<Boolean> HIGHER_VELOCITY = GameRuleBuilder.forBoolean(true).buildAndRegister(CrossbowScoping.id("higher_velocity"));
    public static final GameRule<Double> VELOCITY_MULTIPLIER = GameRuleBuilder.forDouble(2.5).range(1.0, 45.0).buildAndRegister(CrossbowScoping.id("velocity_multiplier"));
    public static final GameRule<Boolean> NO_GRAVITY_PROJECTILES = GameRuleBuilder.forBoolean(false).buildAndRegister(CrossbowScoping.id("no_gravity_projectiles"));
    *///?}

    public static boolean getBoolean(GameRules gameRules, /*? <1.21.11 {*/ GameRules.Key<GameRules.BooleanValue> /*?} else {*/ /*GameRule<Boolean> *//*?}*/ booleanRule) {
        return gameRules. /*? <1.21.11 {*/ getBoolean(booleanRule) /*?} else {*/ /*get(booleanRule) *//*?}*/;
    }

    public static double getDouble(GameRules gameRules, /*? <1.21.11 {*/ GameRules.Key<DoubleRule> /*?} else {*/ /*GameRule<Double> *//*?}*/ doubleRule) {
        return gameRules. /*? <1.21.11 {*/ getRule(doubleRule).get() /*?} else {*/ /*get(doubleRule) *//*?}*/;
    }

    public static void init() {

    }
}
