package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @WrapMethod(method = "renderItemHotbar")
    private void useRealCrossbows(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Operation<Void> original) {
        Player player = this.getCameraPlayer();
        if (player == null) {
            original.call(guiGraphics, deltaTracker);
            return;
        }
        player.crossbow_scoping$setAttacking(true);
        original.call(guiGraphics, deltaTracker);
        player.crossbow_scoping$setAttacking(false);
    }
}
