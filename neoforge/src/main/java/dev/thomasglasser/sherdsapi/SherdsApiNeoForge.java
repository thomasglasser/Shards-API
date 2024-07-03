package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.example.ExampleNeoForgeDataGenerator;
import dev.thomasglasser.sherdsapi.impl.SherdsApiNeoForgeEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SherdsApi.MOD_ID)
public class SherdsApiNeoForge {
    public SherdsApiNeoForge(IEventBus bus) {
        SherdsApi.init();

        ExampleNeoForgeDataGenerator.register(bus);

        bus.addListener(SherdsApiNeoForgeEvents::onNewDatapackRegistry);
    }
}
