package dev.thomasglasser.sherdsapi.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

@ApiStatus.Internal
public record Sherd(Ingredient ingredient, Optional<ResourceKey<DecoratedPotPattern>> pattern)
{
    public static final Codec<Sherd> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(Sherd::ingredient),
            ResourceKey.codec(Registries.DECORATED_POT_PATTERN).optionalFieldOf("pattern").forGetter(Sherd::pattern)
    ).apply(instance, Sherd::new));
}
