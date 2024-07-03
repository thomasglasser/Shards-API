package dev.thomasglasser.sherdsapi.api.data;

import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import java.util.Arrays;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Sherd datagen adapted to Fabric API
 */
public class FabricSherdDatagenSuite extends BaseSherdDatagenSuite<FabricSherdDatagenSuite> {
    /**
     * @param modId The ID of the mod making sherds.
     */
    public FabricSherdDatagenSuite(String modId) {
        super(modId);
    }

    /**
     * Adds the registry keys created to the provided {@link RegistrySetBuilder}
     * <p>
     * Must be called in {@link DataGeneratorEntrypoint#buildRegistry(RegistrySetBuilder)} in order for this suite to work
     * 
     * @param builder The builder to add the keys to
     */
    public void buildRegistry(RegistrySetBuilder builder) {
        sherds.forEach(pair -> builder.add(SherdsApiRegistries.SHERD, context -> context.register(pair.getFirst(), pair.getSecond())));
    }

    /**
     * Registers the datagen with the provided pack
     * 
     * @param pack The pack to register datagen with
     */
    public void build(FabricDataGenerator.Pack pack) {
        pack.addProvider(((output, registries) -> new FabricDynamicRegistryProvider(output, registries) {
            @Override
            protected void configure(HolderLookup.Provider registries, Entries entries) {
                sherds.forEach(pair -> entries.add(pair.getFirst(), pair.getSecond()));
            }

            @Override
            public String getName() {
                return "SherdDatagenSuite / Registries";
            }
        }));

        pack.addProvider((output, registries) -> new FabricTagProvider.ItemTagProvider(output, registries, null) {
            public String getName() {
                String var10000 = super.getName();
                return "SherdDatagenSuite / " + var10000;
            }

            @Override
            protected void addTags(HolderLookup.Provider pProvider) {
                FabricTagBuilder tag = getOrCreateTagBuilder(ItemTags.DECORATED_POT_SHERDS);
                sherds.forEach(pair -> Arrays.stream(pair.getSecond().ingredient().values).forEach(value -> {
                    if (value instanceof Ingredient.TagValue tagValue) {
                        tag.addOptionalTag(tagValue.tag());
                    } else {
                        Item[] item = value.getItems().stream().map(ItemStack::getItem).toArray(Item[]::new);
                        tag.add(item);
                    }
                }));
            }
        });
    }
}
