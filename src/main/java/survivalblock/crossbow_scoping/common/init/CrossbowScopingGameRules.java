package survivalblock.crossbow_scoping.common.init;

//? if <1.21.11 {
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.level.GameRules;
//?} else {
/*import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
*///?}
import survivalblock.atmosphere.registrar.GameRuleRegistrant;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingGameRules {

    private static final GameRuleRegistrant REGISTRANT = new GameRuleRegistrant(CrossbowScoping.MOD_ID);

    //~ if >=1.21.11 'GameRules.Key<' -> 'GameRule<' {
    //~ if >=1.21.11 'GameRules.BooleanValue>' -> 'Boolean>' {
    //~ if >=1.21.11 'DoubleRule>' -> 'Double>' {
    public static final GameRules.Key<GameRules.BooleanValue> HIGHER_PRECISION = REGISTRANT.registerBoolean("higher_precision", true);
    public static final GameRules.Key<GameRules.BooleanValue> HIGHER_VELOCITY = REGISTRANT.registerBoolean("higher_velocity", true);
    public static final GameRules.Key<DoubleRule> VELOCITY_MULTIPLIER = REGISTRANT.registerDouble("velocity_multiplier", 2.5, 1.0, 45.0);
    public static final GameRules.Key<GameRules.BooleanValue> NO_GRAVITY_PROJECTILES = REGISTRANT.registerBoolean("no_gravity_projectiles", false);
    //~}
    //~}
    //~}

    public static void init() {
        // NO-OP
    }
}
