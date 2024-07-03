package dev.thomasglasser.sherdsapi.example;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.api.data.FabricSherdDatagenSuite;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

/**
 * Example of how to create sherds using the {@link FabricSherdDatagenSuite}.
 */
public class ExampleFabricDataGenerator implements DataGeneratorEntrypoint {
    /**
     * Statically initialized so we can call {@link FabricSherdDatagenSuite#buildRegistry(RegistrySetBuilder)} below.
     */
    private final FabricSherdDatagenSuite suite = new FabricSherdDatagenSuite(SherdsApi.MOD_ID);

    /**
     * Adds values to the {@link FabricSherdDatagenSuite} and builds it with the provided pack
     * <p>
     * {@link FabricSherdDatagenSuite#build(FabricDataGenerator.Pack)} must be called for generation to work.
     * 
     * @param fabricDataGenerator The {@link FabricDataGenerator} instance provided by Fabric API.
     */
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        suite
                .makeSherdSuite("api", Items.LIGHT)
                .makeSherdSuite(suite.modLoc("hello"), ConventionalItemTags.MUSIC_DISCS, DecoratedPotPatterns.HEART)
                .makeSherdSuite(SherdsApi.modLoc("replacement"), List.of(Items.SHEAF_POTTERY_SHERD, Items.SHELTER_POTTERY_SHERD), DecoratedPotPatterns.ARCHER)
                .build(pack);
    }

    /**
     * Adds the registry keys created by the {@link FabricSherdDatagenSuite} to the provided {@link RegistrySetBuilder}
     * 
     * @param registryBuilder The provided {@link RegistrySetBuilder} instance to add to
     */
    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        suite.buildRegistry(registryBuilder);
    }
}
