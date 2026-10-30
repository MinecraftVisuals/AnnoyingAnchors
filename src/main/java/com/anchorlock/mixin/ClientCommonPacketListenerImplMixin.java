package com.anchorlock.mixin;

import com.anchorlock.LockedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public class ClientCommonPacketListenerImplMixin {

    /** Blocks the swap-with-offhand key (F) when it would move a locked item. */
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void anchorlock$blockOffhandSwap(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ServerboundPlayerActionPacket action
                && action.getAction() == ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null
                    && (LockedItems.isLocked(player.getMainHandItem())
                    || LockedItems.isLocked(player.getOffhandItem()))) {
                ci.cancel();
            }
        }
    }
}
