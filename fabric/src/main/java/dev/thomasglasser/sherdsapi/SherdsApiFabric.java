package dev.thomasglasser.sherdsapi;

import net.fabricmc.api.ModInitializer;

public class SherdsApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SherdsApi.init();
    }
}
