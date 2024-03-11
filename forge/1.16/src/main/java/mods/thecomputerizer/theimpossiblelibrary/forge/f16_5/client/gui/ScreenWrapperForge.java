package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.MinecraftForgeTIL;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public class ScreenWrapperForge extends Screen {

    private final ScreenForge screen;

    public ScreenWrapperForge(ScreenForge screen, ITextComponent title) {
        super(title);
        this.screen = screen;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderAPI renderer = MinecraftForgeTIL.getInstance().getRenderer().setMouse(mouseX,mouseY).setPartialTicks(partialTicks);
        if(Objects.nonNull(this.screen)) this.screen.draw(renderer,getBlitOffset());
    }
}