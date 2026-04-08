package net.nlpixel.randomeventy.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.nlpixel.randomeventy.client.RandomSettingsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends net.minecraft.client.gui.screen.Screen {

    protected CreateWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void addTopTabButton(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Randomizer"), (button) -> {
                    MinecraftClient.getInstance().setScreen(new RandomSettingsScreen(this));
                })
                .dimensions(this.width / 2 + 102, 30, 95, 20)
                .build());
    }
}