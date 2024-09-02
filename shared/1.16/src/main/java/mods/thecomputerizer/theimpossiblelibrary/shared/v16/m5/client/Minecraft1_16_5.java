package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.entity.ClientPlayer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font.Font1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.Render1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.client.ClientModLoader;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public class Minecraft1_16_5 implements MinecraftAPI {

    public static Minecraft1_16_5 getInstance() {
        return new Minecraft1_16_5(Minecraft.getInstance());
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private Minecraft1_16_5(Minecraft mc) {
        this.mc = mc;
        this.font = new Font1_16_5();
        this.render = new Render1_16_5();
    }
    
    @Override public void addResourcePackFolder(File dir) {}
    
    @Override public int getDisplayHeight() {
        return this.mc.getWindow().getHeight();
    }
    
    @Override public int getDisplayWidth() {
        return this.mc.getWindow().getWidth();
    }
    
    @Override
    public FontAPI getFont() {
        return this.font;
    }
    
    @Override public int getGUIScale() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.options) ? this.mc.options.guiScale : 0;
    }
    
    @Override
    public @Nullable PlayerAPI<ClientPlayerEntity,?> getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new ClientPlayer1_16_5(this.mc.player) : null;
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    /**
     * TODO Cache this?
     */
    @Override
    public MinecraftWindow getWindow() {
        MainWindow window = Objects.nonNull(this.mc) ? this.mc.getWindow() : null;
        if(Objects.isNull(window)) {
            TILRef.logFatal("Unable to get MinecraftWidnow since the Minecraft main window is null?");
            return new MinecraftWindow(1d,1d,0);
        }
        return new MinecraftWindow(window.getGuiScaledWidth(),window.getGuiScaledHeight(),(int)window.getGuiScale());
    }

    @Override
    public @Nullable WorldAPI<IWorld> getWorld() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.level) ? new World1_16_5(this.mc.level) : null;
    }

    @Override
    public <S> boolean isCurrentScreen(S screen) {
        return Objects.nonNull(this.mc) && this.mc.screen==screen;
    }

    @Override
    public boolean isCurrentScreenAPI() {
        return false;
    }

    @Override
    public boolean isDisplayFocused() {
        return Objects.nonNull(this.mc) && this.mc.isWindowActive();
    }

    @Override
    public boolean isFinishedLoading() {
        return !isLoading();
    }

    @Override
    public boolean isFullScreen() {
        return Objects.nonNull(this.mc) && this.mc.getWindow().isFullscreen();
    }

    @Override
    public boolean isLoading() {
        return ClientModLoader.isLoading() || Objects.isNull(this.mc);
    }

    @Override
    public boolean isPaused() {
        return Objects.nonNull(this.mc) && this.mc.isPaused();
    }
}
