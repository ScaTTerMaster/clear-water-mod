package com.example.clearwater.mixin;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private Minecraft minecraft;

    @Redirect(
        method = "renderFog",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/living/LivingEntity;isInFluid(Lnet/minecraft/block/material/Material;)Z",
            ordinal = 0)
    )
    private boolean clearWaterFogColor(LivingEntity entity, Material material) {
        return false;
    }

    @Redirect(
        method = "setupFog",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/living/LivingEntity;isInFluid(Lnet/minecraft/block/material/Material;)Z",
            ordinal = 0)
    )
    private boolean clearWaterFogDensity(LivingEntity entity, Material material) {
        return false;
    }

    @Redirect(
        method = "renderTick",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/level/Level;getBrightness(III)F")
    )
    private float limitUnderwaterDarkness(Level level, int x, int y, int z) {
        float brightness = level.getBrightness(x, y, z);
        LivingEntity entity = this.minecraft.viewEntity;
        if (entity != null && entity.isInFluid(Material.WATER)) {
            return Math.max(brightness, 0.4f);
        }
        return brightness;
    }
}
