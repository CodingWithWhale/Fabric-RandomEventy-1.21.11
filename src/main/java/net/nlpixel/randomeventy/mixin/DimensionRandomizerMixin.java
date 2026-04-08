package net.nlpixel.randomeventy.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.world.chunk.ProtoChunk;
import net.nlpixel.randomeventy.RandomEventConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ProtoChunk.class)
public class DimensionRandomizerMixin {

    @ModifyVariable(method = "setBlockState", at = @At("HEAD"), argsOnly = true)
    private BlockState swapDimensionBlocks(BlockState state) {
        if (RandomEventConfig.randomDimensionGen && state != null) {

            if (state.isOf(Blocks.NETHERRACK) || state.isOf(Blocks.END_STONE)) {
                return Blocks.GRASS_BLOCK.getDefaultState();
            }
        }
        return state;
    }
}