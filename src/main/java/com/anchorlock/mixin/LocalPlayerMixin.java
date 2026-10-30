package com.anchorlock.mixin;

import com.anchorlock.LockedItems;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    /** Blocks the Q / Ctrl+Q drop keys for locked items. */
    @Inject(method = "drop", at = @At("HEAD"), cancellable = true)
    private void anchorlock$blockDrop(boolean fullStack, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (LockedItems.isLocked(self.getMainHandItem())) {
            cir.setReturnValue(false);
        }
    }
}
