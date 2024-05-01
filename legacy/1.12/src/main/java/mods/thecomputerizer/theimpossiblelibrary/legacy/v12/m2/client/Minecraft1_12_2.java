package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.entity.ClientPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.font.Font1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui.Screen1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render.Render1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILCore1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.lwjgl.opengl.Display;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Minecraft1_12_2 implements MinecraftAPI {

    public static Minecraft1_12_2 getInstance() {
        return new Minecraft1_12_2(Minecraft.getMinecraft());
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private Minecraft1_12_2(Minecraft mc) {
        this.mc = mc;
        this.font = new Font1_12_2();
        this.render = new Render1_12_2();
    }

    @Override
    public FontAPI getFont() {
        return this.font;
    }

    @Override
    public @Nullable PlayerAPI<? extends EntityPlayer,EntityEntry> getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new ClientPlayer1_12_2(this.mc.player) : null;
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    @Override
    public @Nullable ScreenAPI<GuiScreen> getScreen() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.currentScreen) ?
                new Screen1_12_2(this.mc.currentScreen) : null;
    }

    /**
     * TODO Cache this?
     */
    @Override
    public MinecraftWindow getWindow() {
        if(Objects.isNull(this.mc)) return null;
        ScaledResolution res = new ScaledResolution(this.mc);
        return new MinecraftWindow(res.getScaledWidth(),res.getScaledHeight(),res.getScaleFactor());
    }

    @Override
    public @Nullable WorldAPI<World> getWorld() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.world) ? new World1_12_2(this.mc.world) : null;
    }

    @Override
    public <S> boolean isCurrentScreen(S screen) {
        return Objects.nonNull(this.mc) && screen==this.mc.currentScreen;
    }

    @Override
    public boolean isCurrentScreenAPI(ScreenAPI<?> screen) {
        return isCurrentScreen(screen.getScreen());
    }

    @Override
    public boolean isDisplayFocused() {
        if(Objects.isNull(this.mc)) return false;
        try {
            return this.mc.addScheduledTask(() -> Display.isCreated() && Display.isActive()).get();
        } catch(ExecutionException | InterruptedException ex) {
            TILRef.logWarn("Unable to determine display focus state",ex);
            return false;
        }
    }

    @Override
    public boolean isFinishedLoading() {
        return !FMLClientHandler.instance().isLoading() && Objects.nonNull(this.mc) &&
                Objects.nonNull(this.mc.currentScreen);
    }

    @Override
    public boolean isFullScreen() {
        return Objects.nonNull(this.mc) && this.mc.isFullScreen();
    }

    @Override
    public boolean isLoading() {
        return FMLClientHandler.instance().isLoading();
    }

    @Override
    public boolean isPaused() {
        return Objects.nonNull(this.mc) && this.mc.isGamePaused();
    }

    @Override
    public <S> void setScreen(@Nullable S screen) { //TODO Fix this
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((Screen1_12_2)screen).getInstance());
        //else this.mc.displayGuiScreen(null);
    }

    @Override
    public void setScreenAPI(@Nullable ScreenAPI<?> api) { //TODO Fix this
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((Screen1_12_2)screen).getInstance());
        //else this.mc.displayGuiScreen(null);
    }
}
