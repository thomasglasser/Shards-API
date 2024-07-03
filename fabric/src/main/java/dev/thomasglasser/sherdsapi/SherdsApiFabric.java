package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public class SherdsApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SherdsApi.init();

        DynamicRegistries.registerSynced(SherdsApiRegistries.SHERD, Sherd.CODEC, Sherd.CODEC);
    }
}
