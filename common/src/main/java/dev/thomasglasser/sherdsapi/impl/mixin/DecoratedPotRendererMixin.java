package dev.thomasglasser.sherdsapi.impl.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin {
    @Shadow
    protected abstract void renderSide(ModelPart modelPart, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Material material);

    @Shadow
    @Final
    private ModelPart frontSide;
    @Shadow
    @Final
    private ModelPart backSide;
    @Shadow
    @Final
    private ModelPart leftSide;
    @Shadow
    @Final
    private ModelPart rightSide;

    @Shadow
    private static Material getSideMaterial(Optional<Item> item) {
        return null;
    }

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;getDecorations()Lnet/minecraft/world/level/block/entity/PotDecorations;"), cancellable = true)
    private void render(DecoratedPotBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        if (blockEntity.getDecorations() == PotDecorations.EMPTY && blockEntity instanceof StackPotDecorationsHolder holder) {
            StackPotDecorations decorations = holder.sherdsapi$getDecorations();
            if (decorations != null) {
                renderSide(frontSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.front()));
                renderSide(backSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.back()));
                renderSide(leftSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.left()));
                renderSide(rightSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.right()));
                poseStack.popPose();
                ci.cancel();
            }
        }
    }

    @Unique
    private static final Map<ResourceLocation, Material> CUSTOM_MATERIALS = new HashMap<>();

    @Unique
    private static Material sherdsapi$getDecoratedPotMaterial(ResourceLocation location) {
        return CUSTOM_MATERIALS.computeIfAbsent(location, Sheets::createDecoratedPotMaterial);
    }

    @Unique
    private static Material sherdsapi$getSideMaterial(Optional<ItemStack> optional) {
        if (optional.isPresent()) {
            ItemStack stack = optional.get();
            if (stack.has(SherdsApiDataComponents.SHERD_PATTERN.get())) {
                Material material = sherdsapi$getDecoratedPotMaterial(stack.get(SherdsApiDataComponents.SHERD_PATTERN.get()));
                if (material != null) {
                    return material;
                }
            } else {
                return getSideMaterial(Optional.of(stack.getItem()));
            }
        }

        return Sheets.DECORATED_POT_SIDE;
    }
}
