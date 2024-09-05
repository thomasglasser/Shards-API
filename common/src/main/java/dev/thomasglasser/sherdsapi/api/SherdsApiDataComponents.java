package dev.thomasglasser.sherdsapi.api;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class SherdsApiDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, SherdsApi.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> SHERD_PATTERN = DATA_COMPONENTS.register("sherd_pattern", () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StackPotDecorations>> STACK_POT_DECORATIONS = DATA_COMPONENTS.register("stack_pot_decorations", () -> DataComponentType.<StackPotDecorations>builder().persistent(StackPotDecorations.CODEC).networkSynchronized(StackPotDecorations.STREAM_CODEC).cacheEncoding().build());

    public static void init() {}
}
