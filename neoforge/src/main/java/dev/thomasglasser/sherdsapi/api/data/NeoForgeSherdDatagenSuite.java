package dev.thomasglasser.sherdsapi.api.data;

import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Sherd datagen adapted to NeoForge API
 */
public class NeoForgeSherdDatagenSuite extends BaseSherdDatagenSuite<NeoForgeSherdDatagenSuite>
{
	/**
	 * @param event The GatherDataEvent to register events to.
	 * @param modId The ID of the mod that is generating sherds.
	 */
	public NeoForgeSherdDatagenSuite(GatherDataEvent event, String modId)
	{
		super(modId);
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = event.getGenerator().getPackOutput();
		RegistrySetBuilder builder = (new RegistrySetBuilder()).add(SherdsApiRegistries.SHERD, (pContext) -> this.sherds.forEach((pair) -> pContext.register(pair.getFirst(), pair.getSecond())));

		DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), builder, Set.of(modId))
		{
			public String getName()
			{
				String var10000 = super.getName();
				return "SherdDatagenSuite / " + var10000;
			}
		};

		generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

		final CompletableFuture<HolderLookup.Provider> registries = datapackBuiltinEntriesProvider.getRegistryProvider();

		generator.addProvider(event.includeServer(), new ItemTagsProvider(packOutput, registries, null, NeoForgeSherdDatagenSuite.this.modId, event.getExistingFileHelper())
		{
			@Override
			protected CompletableFuture<HolderLookup.Provider> createContentsProvider()
			{
				return registries.thenApply((p_274768_) -> {
					this.builders.clear();
					this.addTags(p_274768_);
					return p_274768_;
				});
			}

			public String getName()
			{
				String var10000 = super.getName();
				return "SherdDatagenSuite / " + var10000;
			}

			@Override
			protected void addTags(HolderLookup.Provider pProvider)
			{
				IntrinsicTagAppender<Item> tag = tag(ItemTags.DECORATED_POT_SHERDS);
				sherds.forEach(pair -> Arrays.stream(pair.getSecond().ingredient().getValues()).forEach(value ->
				{
					if (value instanceof Ingredient.TagValue tagValue)
					{
						tag.addTag(tagValue.tag());
					}
					else
					{
						Item[] item = value.getItems().stream().map(ItemStack::getItem).toArray(Item[]::new);
						tag.add(item);
					}
				}));
			}
		});
	}
}
