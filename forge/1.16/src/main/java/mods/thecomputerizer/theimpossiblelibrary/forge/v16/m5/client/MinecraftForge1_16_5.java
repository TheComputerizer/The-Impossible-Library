package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.entity.ClientPlayerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.font.FontForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render.RenderForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.Minecraft1_16_5;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.ClientModLoader;

import javax.annotation.Nullable;
import java.util.Objects;

public class MinecraftForge1_16_5 extends Minecraft1_16_5 {
    
    public static MinecraftForge1_16_5 getInstance() {
        return new MinecraftForge1_16_5(Minecraft.getInstance());
    }
    
    private MinecraftForge1_16_5(Minecraft mc) {
        super(mc,new FontForge1_16_5(),new RenderForge1_16_5());
    }
    
    @Override public int getDisplayHeight() {
        return this.mc.getWindow().getHeight();
    }
    
    @Override public int getDisplayWidth() {
        return this.mc.getWindow().getWidth();
    }
    
    @Override public int getGUIScale() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.options) ? this.mc.options.guiScale : 0;
    }
    
    @Override public @Nullable ClientPlayerForge1_16_5 getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new ClientPlayerForge1_16_5(this.mc.player) : null;
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
    
    @Override public boolean isFullScreen() {
        return Objects.nonNull(this.mc) && this.mc.getWindow().isFullscreen();
    }
    
    @Override public boolean isLoading() {
        return super.isLoading() || ClientModLoader.isLoading();
    }
}