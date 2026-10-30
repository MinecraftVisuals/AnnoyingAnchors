package com.anchorlock;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnchorLockClient implements ClientModInitializer {
    public static final String MOD_ID = "anchorlock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Anchor Lock loaded: Respawn Anchors and Glowstone are locked to their slots.");
    }
}
