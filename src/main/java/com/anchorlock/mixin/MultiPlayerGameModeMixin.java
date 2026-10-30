package com.anchorlock.mixin;

import com.anchorlock.LockedItems;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Inject(method = "handleInventoryMouseClick", at = @At("HEAD"), cancellable = true)
    private void anchorlock$blockMoves(int containerId, int slotId, int buttonNum,
                                       ClickType clickType, Player player, CallbackInfo ci) {
        AbstractContainerMenu menu = player.containerMenu;
        if (menu == null || menu.containerId != containerId) {
            return;
        }

        // Double-click "collect all" would pull locked stacks out of the inventory.
        if (clickType == ClickType.PICKUP_ALL && LockedItems.isLocked(menu.getCarried())) {
            ci.cancel();
            return;
        }

        if (slotId < 0 || slotId >= menu.slots.size()) {
            return;
        }

        Slot slot = menu.slots.get(slotId);
        boolean inOwnInventory = slot.container == player.getInventory();

        switch (clickType) {
            case PICKUP, QUICK_MOVE, THROW, CLONE -> {
                if (inOwnInventory && LockedItems.isLocked(slot.getItem())) {
                    ci.cancel();
                }
            }
            case SWAP -> {
                // buttonNum is the hotbar slot (0-8) or 40 for the offhand.
                ItemStack other = buttonNum == 40
                        ? player.getOffhandItem()
                        : (buttonNum >= 0 && buttonNum < 9 ? player.getInventory().getItem(buttonNum) : ItemStack.EMPTY);
                boolean slotLocked = inOwnInventory && LockedItems.isLocked(slot.getItem());
                if (slotLocked || LockedItems.isLocked(other)) {
                    ci.cancel();
                }
            }
            default -> {
            }
        }
    }
}
