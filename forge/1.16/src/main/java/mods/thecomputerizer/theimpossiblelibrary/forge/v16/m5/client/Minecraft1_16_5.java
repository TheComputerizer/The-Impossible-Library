package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.font.Font1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui.Screen1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render.Render1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.entity.Player1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.Objects;

public class Minecraft1_16_5 implements MinecraftAPI {

    private static Minecraft1_16_5 INSTANCE;

    public static Minecraft1_16_5 getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new Minecraft1_16_5(Minecraft.getInstance());
        return INSTANCE;
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private Minecraft1_16_5(Minecraft mc) {
        this.mc = mc;
        this.font = new Font1_16_5();
        this.render = new Render1_16_5();
    }

    @Override
    public FontAPI getFont() {
        return this.font;
    }

    @Override
    public @Nullable PlayerAPI<PlayerEntity> getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new Player1_16_5(this.mc.player) : null;
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    @Override
    public @Nullable ScreenAPI<Screen> getScreen() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.screen) ? new Screen1_16_5(this.mc.screen) : null;
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
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.level) ? new World1_16_5(this.mc.level) : null;
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
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((Screen1_12_2)screen).get());
        //else this.mc.displayGuiScreen(null);
    }

    @Override
    public void setScreenAPI(@Nullable ScreenAPI<?> screen) { //TODO Fix this
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((Screen1_12_2)screen).get());
        //else this.mc.displayGuiScreen(null);
    }
}
