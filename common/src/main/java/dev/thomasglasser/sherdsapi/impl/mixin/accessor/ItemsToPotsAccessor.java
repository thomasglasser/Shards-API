package dev.thomasglasser.sherdsapi.impl.mixin.accessor;

import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DecoratedPotPatterns.class)
public interface ItemsToPotsAccessor {
    @Accessor("ITEM_TO_POT_TEXTURE")
    static Map<Item, ResourceKey<String>> getItemsToPots() {
        throw new AssertionError("This should not happen!");
    }
}
