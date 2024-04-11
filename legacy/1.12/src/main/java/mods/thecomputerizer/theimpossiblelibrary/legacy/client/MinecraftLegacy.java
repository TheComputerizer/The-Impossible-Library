package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.TILLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.font.FontLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui.ScreenLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.render.RenderLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.entity.PlayerLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.world.WorldLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.Display;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MinecraftLegacy implements MinecraftAPI {

    private static MinecraftLegacy INSTANCE;

    public static MinecraftLegacy getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new MinecraftLegacy(Minecraft.getMinecraft());
        return INSTANCE;
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private MinecraftLegacy(Minecraft mc) {
        this.mc = mc;
        this.font = new FontLegacy();
        this.render = new RenderLegacy();
    }

    @Override
    public FontAPI getFont() {
        return this.font;
    }

    @Override
    public @Nullable PlayerAPI<EntityPlayer> getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new PlayerLegacy(this.mc.player) : null;
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    @Override
    public @Nullable ScreenAPI<GuiScreen> getScreen() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.currentScreen) ?
                new ScreenLegacy(this.mc.currentScreen) : null;
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
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.world) ? new WorldLegacy(this.mc.world) : null;
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
            TILLegacy.LEGACY_REF.logWarn("Unable to determine display focus state",ex);
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
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((ScreenLegacy)screen).get());
        //else this.mc.displayGuiScreen(null);
    }

    @Override
    public void setScreenAPI(@Nullable ScreenAPI<?> api) { //TODO Fix this
        //if(Objects.nonNull(screen)) this.mc.displayGuiScreen(((ScreenLegacy)screen).get());
        //else this.mc.displayGuiScreen(null);
    }
}
