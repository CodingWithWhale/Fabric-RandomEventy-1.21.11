package net.nlpixel.randomeventy.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.Registries;
import net.nlpixel.randomeventy.RandomEventConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(AbstractBlock.class)
public class BlockRandomizerMixin {

    @Inject(method = "getDroppedStacks", at = @At("RETURN"), cancellable = true)
    private void onGetDroppedStacks(BlockState state, LootWorldContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {

        if (RandomEventConfig.randomBlockDrops) {
            System.out.println("DEBUG: Randomizer is ON. Randomizing: " + state.getBlock().asItem().getName().getString());

            List<ItemStack> originalDrops = cir.getReturnValue();

            if (originalDrops != null && !originalDrops.isEmpty()) {
                List<ItemStack> newDrops = new ArrayList<>();

                for (ItemStack stack : originalDrops) {
                    var randomItem = Registries.ITEM.getRandom(net.minecraft.util.math.random.Random.create()).get().value();
                    newDrops.add(new ItemStack(randomItem, stack.getCount()));
                }

                cir.setReturnValue(newDrops);
            }
        }
    }
}