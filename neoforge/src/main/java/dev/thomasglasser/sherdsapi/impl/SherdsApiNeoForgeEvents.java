package dev.thomasglasser.sherdsapi.impl;

import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class SherdsApiNeoForgeEvents {
    public static void onNewDatapackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(SherdsApiRegistries.SHERD, Sherd.CODEC, Sherd.CODEC);
    }
}
