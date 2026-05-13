package com.example.clearwater.mixin;

import net.minecraft.block.material.Material;
import net.minecraft.client.render.OverlaysRenderer;
import net.minecraft.entity.living.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OverlaysRenderer.class)
public class OverlaysRendererMixin {

    @Redirect(
        method = "renderOverlays",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/living/player/AbstractClientPlayer;isInFluid(Lnet/minecraft/block/material/Material;)Z")
    )
    private boolean clearWaterOverlay(AbstractClientPlayer player, Material material) {
        return false;
    }
}
