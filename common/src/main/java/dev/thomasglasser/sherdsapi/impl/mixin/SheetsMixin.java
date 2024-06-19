package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(Sheets.class)
public abstract class SheetsMixin
{
	@Shadow
	private static Material createDecoratedPotMaterial(ResourceLocation resourceLocation)
	{
		return null;
	}

	@Unique
	private static final Map<Sherd, Material> CUSTOM_MATERIALS = new HashMap<>();

	@ModifyReturnValue(method = "getDecoratedPotMaterial", at = @At("RETURN"))
	private static Material sherdsapi_getDecoratedPotMaterial(Material original, @Nullable ResourceKey<DecoratedPotPattern> key)
	{
		final Level level = Minecraft.getInstance().level;
		if (level != null)
		{
			Registry<Sherd> registry = level.registryAccess().registry(SherdsApiRegistries.SHERD).orElseThrow();
			for (Sherd sherd : registry.stream().toList())
			{
				ResourceKey<DecoratedPotPattern> pattern;
				if (sherd.pattern().isPresent())
					pattern = sherd.pattern().get();
				else
					pattern = ResourceKey.create(Registries.DECORATED_POT_PATTERN, registry.getResourceKey(sherd).orElseThrow().location());
				if (pattern == key)
				{
					if (!CUSTOM_MATERIALS.containsKey(sherd))
					{
						DecoratedPotPattern decoratedPotPattern = level.registryAccess().registry(Registries.DECORATED_POT_PATTERN).orElseThrow().get(key);
						ResourceLocation loc = decoratedPotPattern == null ? key.location() : decoratedPotPattern.assetId();
						CUSTOM_MATERIALS.put(sherd, createDecoratedPotMaterial(loc));
					}

					return CUSTOM_MATERIALS.get(sherd);
				}
			}
		}
		return original;
	}
}
