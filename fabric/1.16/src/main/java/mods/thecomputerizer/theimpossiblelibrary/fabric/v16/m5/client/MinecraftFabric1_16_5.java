package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client;

import com.mojang.blaze3d.platform.Window;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.entity.ClientPlayerFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.font.FontFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.render.RenderFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.Minecraft1_16_5;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.Objects;

public class MinecraftFabric1_16_5 extends Minecraft1_16_5 {
    
    public static MinecraftFabric1_16_5 getInstance() {
        return new MinecraftFabric1_16_5(Minecraft.getInstance());
    }
    
    protected MinecraftFabric1_16_5(Minecraft mc) {
        super(mc,new FontFabric1_16_5(),new RenderFabric1_16_5());
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
    
    @Override public @Nullable ClientPlayerFabric1_16_5 getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new ClientPlayerFabric1_16_5(this.mc.player) : null;
    }
    
    /**
     * TODO Cache this?
     */
    @Override public MinecraftWindow getWindow() {
        Window window = Objects.nonNull(this.mc) ? this.mc.getWindow() : null;
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
    
    @Override public boolean isLoading() { //TODO This might not catch some edge cases
        return super.isLoading() || (Objects.isNull(this.mc.level) && Objects.isNull(this.mc.screen));
    }
}