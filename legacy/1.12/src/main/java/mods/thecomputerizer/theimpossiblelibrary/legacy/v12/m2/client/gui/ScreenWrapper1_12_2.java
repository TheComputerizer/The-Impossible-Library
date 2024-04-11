package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Minecraft1_12_2;
import net.minecraft.client.gui.GuiScreen;

import java.util.Objects;

public class ScreenWrapper1_12_2 extends GuiScreen {

    private final Screen1_12_2 screen;

    public ScreenWrapper1_12_2(Screen1_12_2 screen) {
        this.screen = screen;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderAPI renderer = Minecraft1_12_2.getInstance().getRenderer().setMouse(mouseX,mouseY).setPartialTicks(partialTicks);
        if(Objects.nonNull(this.screen)) this.screen.draw(renderer,this.zLevel);
    }
}