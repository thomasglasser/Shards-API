package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {
    @Inject(method = "appendHoverText", at = @At("RETURN"))
    private void appendHoverText(ItemStack p_285238_, Item.TooltipContext p_339662_, List<Component> p_285448_, TooltipFlag p_284997_, CallbackInfo ci) {
        StackPotDecorations decorations = p_285238_.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
        if (decorations != null) {
            p_285448_.add(CommonComponents.EMPTY);
            Stream.of(decorations.front(), decorations.left(), decorations.right(), decorations.back())
                    .forEach(p_330130_ -> p_285448_.add(p_330130_.orElse(Items.BRICK.getDefaultInstance()).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY)));
        }
    }

    @Inject(method = { "lambda$getDrops$0", "method_49815" }, at = @At("HEAD"), cancellable = true)
    private static void getDrops(DecoratedPotBlockEntity decoratedpotblockentity, Consumer<ItemStack> p_330132_, CallbackInfo ci) {
        if (decoratedpotblockentity.getDecorations() == PotDecorations.EMPTY) {
            StackPotDecorations decorations = ((StackPotDecorationsHolder) decoratedpotblockentity).sherdsapi$getDecorations();
            if (decorations != null) {
                for (ItemStack item : decorations.ordered()) {
                    p_330132_.accept(item);
                }
            }
            ci.cancel();
        }
    }
}
