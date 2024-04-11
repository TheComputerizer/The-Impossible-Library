package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Minecraft1_16_5;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public class ScreenWrapper1_16_5 extends Screen {

    private final Screen1_16_5 screen;

    public ScreenWrapper1_16_5(Screen1_16_5 screen, ITextComponent title) {
        super(title);
        this.screen = screen;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderAPI renderer = Minecraft1_16_5.getInstance().getRenderer().setMouse(mouseX,mouseY).setPartialTicks(partialTicks);
        if(Objects.nonNull(this.screen)) this.screen.draw(renderer,getBlitOffset());
    }
}