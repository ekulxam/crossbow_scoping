package survivalblock.crossbow_scoping.mixin.crossbow;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import survivalblock.crossbow_scoping.common.injected_interface.CrossbowAttackingPlayer;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements CrossbowAttackingPlayer {

    @Unique
    private boolean crossbow_scoping$attacking = false;

    @Override
    public void crossbow_scoping$setAttacking(boolean attacking) {
        this.crossbow_scoping$attacking = attacking;
    }

    @Override
    public boolean crossbow_scoping$isAttacking() {
        return this.crossbow_scoping$attacking;
    }
}
