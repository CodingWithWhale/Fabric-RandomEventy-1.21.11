package net.nlpixel.randomeventy.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.nlpixel.randomeventy.RandomEventConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(Chunk.class)
public class WorldGenRandomizerMixin {

    @Unique
    private static final List<BlockState> STABLE_PALETTE = new ArrayList<>();

    @ModifyVariable(method = "setBlockState", at = @At("HEAD"), argsOnly = true)
    private BlockState randomizeWorld(BlockState state, BlockPos pos) {
        if (state == null || state.isAir() || state.isOf(Blocks.BEDROCK)) return state;

        if (RandomEventConfig.randomDimensionGen) {
            if (state.isOf(Blocks.NETHERRACK) || state.isOf(Blocks.END_STONE)) {
                double noise = Math.sin(pos.getX() * 0.1) * Math.cos(pos.getZ() * 0.1);
                return (noise > 0) ? Blocks.GRASS_BLOCK.getDefaultState() : Blocks.DIRT.getDefaultState();
            }

            if (state.isOf(Blocks.LAVA)) return Blocks.WATER.getDefaultState();
            if (state.isOf(Blocks.GLOWSTONE) || state.isOf(Blocks.MAGMA_BLOCK)) return Blocks.OAK_LOG.getDefaultState();
        }

        if (RandomEventConfig.randomBlockGen) {
            if (STABLE_PALETTE.isEmpty()) {
                generateStablePalette();
            }
            long seed = (long)pos.getX() * 3129871 ^ (long)pos.getZ() * 116129781L ^ (long)pos.getY() * 42069L;
            int index = Math.abs((int)seed) % STABLE_PALETTE.size();
            return STABLE_PALETTE.get(index);
        }

        return state;
    }

    @Unique
    private void generateStablePalette() {
        Registries.BLOCK.forEach(block -> {
            BlockState s = block.getDefaultState();
            if (s.isFullCube(net.minecraft.world.EmptyBlockView.INSTANCE, BlockPos.ORIGIN)
                    && s.getFluidState().isEmpty()
                    && !(block instanceof net.minecraft.block.FallingBlock)) {
                STABLE_PALETTE.add(s);
            }
        });
        Collections.shuffle(STABLE_PALETTE);
    }
}