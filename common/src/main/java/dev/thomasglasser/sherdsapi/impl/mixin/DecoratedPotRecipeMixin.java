package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.DecoratedPotRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotRecipe.class)
public class DecoratedPotRecipeMixin {
    @ModifyExpressionValue(method = "matches(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/world/level/Level;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean matches(boolean original, @Local ItemStack itemstack) {
        return original || itemstack.has(SherdsApiDataComponents.SHERD_PATTERN.get());
    }

    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void assemble(CraftingInput input, HolderLookup.Provider provider, CallbackInfoReturnable<ItemStack> cir) {
        if (input.items().stream().anyMatch(stack -> stack.has(SherdsApiDataComponents.SHERD_PATTERN.get()))) {
            StackPotDecorations decorations = new StackPotDecorations(input.getItem(1).copyWithCount(1), input.getItem(3).copyWithCount(1), input.getItem(5).copyWithCount(1), input.getItem(7).copyWithCount(1));
            cir.setReturnValue(StackPotDecorations.createDecoratedPotItem(decorations));
        }
    }
}
