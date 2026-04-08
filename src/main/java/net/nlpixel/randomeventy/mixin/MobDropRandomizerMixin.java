package net.nlpixel.randomeventy.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.nlpixel.randomeventy.RandomEventConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MobDropRandomizerMixin {

    @Inject(method = "dropStack", at = @At("HEAD"), cancellable = true)
    private void onDropStack(ServerWorld world, ItemStack stack, CallbackInfoReturnable<ItemEntity> cir) {

        if (RandomEventConfig.randomMobDrops && (Object)this instanceof MobEntity) {

            if (stack != null && !stack.isEmpty()) {
                Entity entity = (Entity)(Object)this;

                var randomItem = Registries.ITEM.getRandom(world.getRandom()).get().value();
                ItemStack randomStack = new ItemStack(randomItem, stack.getCount());

                ItemEntity itemEntity = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), randomStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);

                cir.setReturnValue(itemEntity);
            }
        }
    }
}