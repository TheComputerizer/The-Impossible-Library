package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.Render1_16_5;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public abstract class Minecraft1_16_5 implements MinecraftAPI {

    protected final Minecraft mc;
    protected final FontAPI font;
    protected final RenderAPI<?> render;

    protected Minecraft1_16_5(Minecraft mc, FontAPI font) {
        this.mc = mc;
        this.font = font;
        this.render = new Render1_16_5();
    }
    
    @Override public void addResourcePackFolder(File dir) {}
    
    @Override public int getDisplayHeight() {
        return this.mc.getWindow().getHeight();
    }
    
    @Override public int getDisplayWidth() {
        return this.mc.getWindow().getWidth();
    }
    
    @Override public FontAPI getFont() {
        return this.font;
    }
    
    @Override public int getGUIScale() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.options) ? this.mc.options.guiScale : 0;
    }
    
    @Override public @Nullable PlayerAPI<?,?> getPlayer() {
        return WrapperHelper.wrapPlayer(this.mc.player);
    }

    @Override public RenderAPI<?> getRenderer() {
        return this.render;
    }
    
    /**
     * TODO Cache this?
     */
    @Override public MinecraftWindow getWindow() {
        MainWindow window = Objects.nonNull(this.mc) ? this.mc.getWindow() : null;
        if(Objects.isNull(window)) {
            TILRef.logFatal("Unable to get MinecraftWindow since the Minecraft main window is null?");
            return new MinecraftWindow(1d,1d,0);
        }
        return new MinecraftWindow(window.getGuiScaledWidth(),window.getGuiScaledHeight(),(int)window.getGuiScale());
    }
    
    @Override public @Nullable WorldAPI<?> getWorld() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.level) ? WrapperHelper.wrapWorld(this.mc.level) : null;
    }

    @Override public <S> boolean isCurrentScreen(S screen) {
        return Objects.nonNull(this.mc) && this.mc.screen==screen;
    }

    @Override public boolean isCurrentScreenAPI() {
        return false;
    }

    @Override public boolean isDisplayFocused() {
        return Objects.nonNull(this.mc) && this.mc.isWindowActive();
    }

    @Override public boolean isFinishedLoading() {
        return !isLoading();
    }
    
    @Override public boolean isFullScreen() {
        return Objects.nonNull(this.mc) && this.mc.getWindow().isFullscreen();
    }

    @Override public boolean isPaused() {
        return Objects.nonNull(this.mc) && this.mc.isPaused();
    }
}