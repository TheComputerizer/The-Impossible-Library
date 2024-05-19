package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Minecraft1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class ScreenWrapper1_12_2 extends GuiScreen implements Wrapped<ScreenAPI> {
    
    private final ScreenAPI wrapped;
    
    public ScreenWrapper1_12_2(@Nullable ScreenAPI wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override public boolean doesGuiPauseGame() {
        return Objects.isNull(this.wrapped) || this.wrapped.shouldPauseGame();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderContext ctx = RenderContext.get(Minecraft1_12_2.getInstance());
        ctx.setPartialTicks(partialTicks);
        if(Objects.nonNull(this.wrapped)) this.wrapped.draw(ctx,VectorHelper.zero3D(),mouseX,mouseY);
    }
    
    @Override public ScreenAPI getWrapped() {
        return this.wrapped;
    }
    
    @Override public void onGuiClosed() {
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenClosed();
    }
    
    @Override public void onResize(Minecraft mc, int w, int h) {
        super.onResize(mc,w,h);
        if(Objects.nonNull(this.wrapped)) this.wrapped.onResolutionUpdated(Minecraft1_12_2.getInstance().getWindow());
    }
}