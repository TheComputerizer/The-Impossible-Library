package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.font.FontForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui.ScreenForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.render.RenderForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity.PlayerForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.world.WorldForge;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.Objects;

public class MinecraftForgeTIL implements MinecraftAPI {

    private static MinecraftForgeTIL INSTANCE;

    public static MinecraftForgeTIL getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new MinecraftForgeTIL(Minecraft.getInstance());
        return INSTANCE;
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private MinecraftForgeTIL(Minecraft mc) {
        this.mc = mc;
        this.font = new FontForge();
        this.render = new RenderForge();
    }

    @Override
    public FontAPI getFont() {
        return this.font;
    }

    @Override
    public @Nullable PlayerAPI<PlayerEntity> getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new PlayerForge(this.mc.player) : null;
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    @Override
    public @Nullable ScreenAPI<Screen> getScreen() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.screen) ? new ScreenForge(this.mc.screen) : null;
    }

    /**
     * TODO Cache this?
     */
    @Override
    public MinecraftWindow getWindow() {
        MainWindow window = this.mc.getWindow();
        return new MinecraftWindow(window.getGuiScaledWidth(),window.getGuiScaledHeight(),(int)window.getGuiScale());
    }

    @Override
    public @Nullable WorldAPI<IWorld> getWorld() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.level) ? new WorldForge(this.mc.level) : null;
    }

    @Override
    public <S> boolean isCurrentScreen(S screen) {
        return screen==this.mc.screen;
    }

    @Override
    public boolean isCurrentScreenAPI(ScreenAPI<?> screen) {
        return screen.get()==this.mc.screen;
    }

    @Override
    public boolean isDisplayFocused() {
        return Objects.nonNull(this.mc) && this.mc.isWindowActive();
    }

    @Override
    public boolean isFinishedLoading() {
        return false;
    }

    @Override
    public boolean isFullScreen() {
        return Objects.nonNull(this.mc) && this.mc.getWindow().isFullscreen();
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean isPaused() {
        return Objects.nonNull(this.mc) && this.mc.isPaused();
    }

    @Override
    public <S> void setScreen(@Nullable S screen) { //TODO Fix this
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((ScreenLegacy)screen).get());
        //else this.mc.displayGuiScreen(null);
    }

    @Override
    public void setScreenAPI(@Nullable ScreenAPI<?> screen) { //TODO Fix this
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((ScreenLegacy)screen).get());
        //else this.mc.displayGuiScreen(null);
    }
}
