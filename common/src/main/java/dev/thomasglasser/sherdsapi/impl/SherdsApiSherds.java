package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.SherdsApi;
import net.minecraft.resources.ResourceKey;

public class SherdsApiSherds {
    public static final ResourceKey<Sherd> API = create("api");
    public static final ResourceKey<Sherd> LIGHTBULB = create("lightbulb");

    private static ResourceKey<Sherd> create(String id) {
        return ResourceKey.create(SherdsApiRegistries.SHERD, SherdsApi.modLoc(id));
    }

    public static void init() {}
}
