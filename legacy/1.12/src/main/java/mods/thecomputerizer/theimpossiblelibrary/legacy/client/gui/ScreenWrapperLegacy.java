package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.MinecraftLegacy;
import net.minecraft.client.gui.GuiScreen;

import java.util.Objects;

public class ScreenWrapperLegacy extends GuiScreen {

    private final ScreenLegacy screen;

    public ScreenWrapperLegacy(ScreenLegacy screen) {
        this.screen = screen;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderAPI renderer = MinecraftLegacy.getInstance().getRenderer().setMouse(mouseX,mouseY).setPartialTicks(partialTicks);
        if(Objects.nonNull(this.screen)) this.screen.draw(renderer,this.zLevel);
    }
}