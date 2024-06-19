package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin
{
	@ModifyReturnValue(method = "getPatternFromItem", at = @At("RETURN"))
	private static ResourceKey<DecoratedPotPattern> getResourceKey(ResourceKey<DecoratedPotPattern> original, Item item)
	{
		Level level = ClientUtils.getLevel();

		if (level != null)
		{
			Registry<Sherd> registry = level.registryAccess().registry(SherdsApiRegistries.SHERD).orElseThrow();
			for (Sherd sherd : registry.stream().toList())
			{
				if (sherd.ingredient().test(item.getDefaultInstance()))
				{
					if (sherd.pattern().isPresent())
					{
						return sherd.pattern().get();
					}
					else
					{
						return ResourceKey.create(Registries.DECORATED_POT_PATTERN, registry.getResourceKey(sherd).orElseThrow().location());
					}
				}
			}
		}
		return original;
	}
}
