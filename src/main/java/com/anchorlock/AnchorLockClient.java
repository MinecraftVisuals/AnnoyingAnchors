package com.anchorlock;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnchorLockClient implements ClientModInitializer {
    public static final String MOD_ID = "anchorlock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.anchorlock.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_PERIOD,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                LockedItems.enabled = !LockedItems.enabled;
                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("Anchor Lock: " + (LockedItems.enabled ? "ON" : "OFF")),
                            true);
                }
            }
        });

        LOGGER.info("Anchor Lock loaded (starts ON). Press . to toggle.");
    }
}
