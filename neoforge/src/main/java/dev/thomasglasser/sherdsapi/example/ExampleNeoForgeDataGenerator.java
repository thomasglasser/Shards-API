package dev.thomasglasser.sherdsapi.example;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.api.data.NeoForgeSherdDatagenSuite;
import dev.thomasglasser.sherdsapi.impl.SherdsApiSherds;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import java.util.List;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * Example of how to create sherds using the {@link NeoForgeSherdDatagenSuite}.
 */
public class ExampleNeoForgeDataGenerator {
    public static void gatherData(GatherDataEvent event) {
        new NeoForgeSherdDatagenSuite(event, SherdsApi.MOD_ID)
                .makeSherdSuite(SherdsApiSherds.LIGHTBULB, ConventionalItemTags.MUSIC_DISCS, DecoratedPotPatterns.ARCHER)
                .makeSherdSuite(SherdsApiSherds.API, List.of(Items.STRUCTURE_BLOCK, Items.STRUCTURE_VOID));
    }

    /**
     * Must be called from the mod constructor to register the event
     * 
     * @param bus The mod event bus to register to
     */
    public static void register(IEventBus bus) {
        bus.addListener(ExampleNeoForgeDataGenerator::gatherData);
    }
}
