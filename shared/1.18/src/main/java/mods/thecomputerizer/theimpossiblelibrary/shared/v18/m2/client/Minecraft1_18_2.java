package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client;

import com.mojang.blaze3d.platform.Window;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.font.Font1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.render.Render1_18_2;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public class Minecraft1_18_2 extends MinecraftAPI<Minecraft> {
    
    public static MinecraftAPI<?> getInstance() {
        return new Minecraft1_18_2(Minecraft.getInstance());
    }
    
    public Minecraft1_18_2(Minecraft mc) {
        super(mc,new Font1_18_2(),new Render1_18_2());
    }
    
    @Override public void addResourcePackFolder(File dir) {}
    
    @Override public int getDisplayHeight() {
        return this.wrapped.getWindow().getHeight();
    }
    
    @Override public int getDisplayWidth() {
        return this.wrapped.getWindow().getWidth();
    }
    
    @SuppressWarnings("ConstantValue")
    @Override public int getGUIScale() {
        return Objects.nonNull(this.wrapped) && Objects.nonNull(this.wrapped.options) ? this.wrapped.options.guiScale : 0;
    }
    
    @Override public @Nullable PlayerAPI<?,?> getPlayer() {
        return WrapperHelper.wrapPlayer(this.wrapped.player);
    }
    
    /**
     * TODO Cache this?
     */
    @Override public MinecraftWindow getWindow() {
        Window window = Objects.nonNull(this.wrapped) ? this.wrapped.getWindow() : null;
        if(Objects.isNull(window)) {
            TILRef.logFatal("Unable to get MinecraftWindow since the Minecraft main window is null?");
            return new MinecraftWindow(1d,1d,0);
        }
        return new MinecraftWindow(window.getGuiScaledWidth(),window.getGuiScaledHeight(),(int)window.getGuiScale());
    }
    
    @Override public @Nullable WorldAPI<?> getWorld() {
        return Objects.nonNull(this.wrapped) && Objects.nonNull(this.wrapped.level) ? WrapperHelper.wrapWorld(this.wrapped.level) : null;
    }

    @Override public <S> boolean isCurrentScreen(S screen) {
        return Objects.nonNull(this.wrapped) && this.wrapped.screen==screen;
    }

    @Override public boolean isCurrentScreenAPI() {
        return false;
    }

    @Override public boolean isDisplayFocused() {
        return Objects.nonNull(this.wrapped) && this.wrapped.isWindowActive();
    }

    @Override public boolean isFinishedLoading() {
        return !isLoading();
    }
    
    @Override public boolean isFullScreen() {
        return Objects.nonNull(this.wrapped) && this.wrapped.getWindow().isFullscreen();
    }

    @Override public boolean isPaused() {
        return Objects.nonNull(this.wrapped) && this.wrapped.isPaused();
    }
}