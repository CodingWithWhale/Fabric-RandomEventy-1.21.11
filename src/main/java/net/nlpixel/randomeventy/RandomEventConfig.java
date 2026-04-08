package net.nlpixel.randomeventy;

import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class RandomEventConfig {
    public static boolean randomBlockDrops = false;
    public static boolean randomMobDrops = false;
    public static boolean randomBlockGen = false;
    public static boolean randomDimensionGen = false;
    public static boolean randomWorldShape = false;


    public static final List<BlockState> SAFE_PALETTE = new ArrayList<>();

    public static void initPalette() {
        if (!SAFE_PALETTE.isEmpty()) return;
        Registries.BLOCK.forEach(block -> {
            BlockState state = block.getDefaultState();
            if (state.isFullCube(net.minecraft.world.EmptyBlockView.INSTANCE, BlockPos.ORIGIN)
                    && state.getFluidState().isEmpty()
                    && !(block instanceof net.minecraft.block.FallingBlock)) {
                SAFE_PALETTE.add(state);
            }
        });
    }
}