package dev.thomasglasser.sherdsapi.impl;

import com.mojang.serialization.Codec;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public record StackPotDecorations(Optional<ItemStack> back, Optional<ItemStack> left, Optional<ItemStack> right, Optional<ItemStack> front) {
    public static final Codec<StackPotDecorations> CODEC = ItemStack.OPTIONAL_CODEC
            .sizeLimitedListOf(4)
            .xmap(StackPotDecorations::new, StackPotDecorations::ordered);
    public static final StreamCodec<RegistryFriendlyByteBuf, StackPotDecorations> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
            .apply(ByteBufCodecs.list(4))
            .map(StackPotDecorations::new, StackPotDecorations::ordered);

    private static final StackPotDecorations EMPTY = new StackPotDecorations(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

    private StackPotDecorations(List<ItemStack> p_331803_) {
        this(getItem(p_331803_, 0), getItem(p_331803_, 1), getItem(p_331803_, 2), getItem(p_331803_, 3));
    }

    public StackPotDecorations(ItemStack p_331754_, ItemStack p_331488_, ItemStack p_331845_, ItemStack p_330988_) {
        this(List.of(p_331754_, p_331488_, p_331845_, p_330988_));
    }

    private static Optional<ItemStack> getItem(List<ItemStack> decorations, int index) {
        if (index >= decorations.size()) {
            return Optional.empty();
        } else {
            ItemStack item = decorations.get(index);
            return item.is(Items.BRICK) ? Optional.empty() : Optional.of(item);
        }
    }

    public CompoundTag save(CompoundTag tag) {
        if (this.equals(EMPTY)) {
            return tag;
        } else {
            tag.put("patterns", CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow());
            return tag;
        }
    }

    public List<ItemStack> ordered() {
        return Stream.of(this.back, this.left, this.right, this.front).map(p_331733_ -> p_331733_.orElse(Items.BRICK.getDefaultInstance())).toList();
    }

    public static @Nullable StackPotDecorations load(@Nullable CompoundTag tag) {
        return tag != null && tag.contains("patterns") ? CODEC.parse(NbtOps.INSTANCE, tag.get("patterns")).result().orElse(null) : null;
    }

    public static ItemStack createDecoratedPotItem(StackPotDecorations decorations) {
        ItemStack itemstack = Items.DECORATED_POT.getDefaultInstance();
        itemstack.set(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), decorations);
        return itemstack;
    }
}
