package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.SherdsApi;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class SherdsApiRegistries {
    public static final ResourceKey<Registry<Sherd>> SHERD = createRegistryKey("sherd");

    public static ResourceKey<Sherd> sherdKey(ResourceLocation loc) {
        return ResourceKey.create(SherdsApiRegistries.SHERD, loc);
    }

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(SherdsApi.modLoc(name));
    }

    public static void init() {}
}
