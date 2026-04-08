package net.nlpixel.randomeventy.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.nlpixel.randomeventy.RandomEventConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunkGenerator.class)
public class DimensionShapeMixin {

    @Inject(method = "getBlockState", at = @At("RETURN"), cancellable = true)
    private void createIslandSlices(ChunkNoiseSampler chunkNoiseSampler, int x, int y, int z, BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (RandomEventConfig.randomWorldShape) {
            BlockState original = cir.getReturnValue();

            if (!original.isAir() && !original.isOf(Blocks.BEDROCK) && original.getFluidState().isEmpty()) {
                double noise = Math.sin(y * 0.15) + Math.cos(x * 0.1) + Math.sin(z * 0.1);
                if (noise < -0.4) {
                    cir.setReturnValue(Blocks.AIR.getDefaultState());
                }
            }
        }
    }
}