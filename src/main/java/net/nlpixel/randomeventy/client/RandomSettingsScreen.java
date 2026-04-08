package net.nlpixel.randomeventy.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.nlpixel.randomeventy.RandomEventConfig;

public class RandomSettingsScreen extends Screen {
    private final Screen parent;

    public RandomSettingsScreen(Screen parent) {
        super(Text.literal("Randomizer Options"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;
        int startY = 60;
        int spacing = 25;

        this.addDrawableChild(CheckboxWidget.builder(Text.literal("Random Block Drops"), this.textRenderer)
                .pos(centerX, startY).checked(RandomEventConfig.randomBlockDrops)
                .callback((cb, checked) -> RandomEventConfig.randomBlockDrops = checked).build());

        this.addDrawableChild(CheckboxWidget.builder(Text.literal("Random Mob Drops"), this.textRenderer)
                .pos(centerX, startY + spacing).checked(RandomEventConfig.randomMobDrops)
                .callback((cb, checked) -> RandomEventConfig.randomMobDrops = checked).build());

        this.addDrawableChild(CheckboxWidget.builder(Text.literal("Random Blocks Gen"), this.textRenderer)
                .pos(centerX, startY + (spacing * 2)).checked(RandomEventConfig.randomBlockGen)
                .callback((cb, checked) -> RandomEventConfig.randomBlockGen = checked).build());

        this.addDrawableChild(CheckboxWidget.builder(Text.literal("Cheese Dimension"), this.textRenderer)
                .pos(centerX, startY + (spacing * 3)).checked(RandomEventConfig.randomWorldShape)
                .callback((cb, checked) -> RandomEventConfig.randomWorldShape = checked).build());

        this.addDrawableChild(CheckboxWidget.builder(Text.literal("Random Dimension Gen"), this.textRenderer)
                .pos(centerX, startY + (spacing * 4)).checked(RandomEventConfig.randomDimensionGen)
                .callback((cb, checked) -> RandomEventConfig.randomDimensionGen = checked).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), (button) -> this.client.setScreen(this.parent))
                .dimensions(centerX, this.height - 40, 200, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }
}