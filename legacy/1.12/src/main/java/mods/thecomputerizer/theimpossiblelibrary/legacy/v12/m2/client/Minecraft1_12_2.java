package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.entity.ClientPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.font.Font1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render.Render1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.lwjgl.opengl.Display;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Minecraft1_12_2 implements MinecraftAPI {
    
    private static final List<String> MCMETA_LINES = Arrays.asList(
            "{","\t\"pack\": {","\t\t\"pack_format\": 3,",
            "\t\t\"description\": \"Relocated The Impossible Library resources\"", "\t}", "}");

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
    
    @Override public void addResourcePackFolder(File dir) {
        if(TILDev.DEV) {
            TILDev.logInfo("Attmpting to manually define dev resources");
            if(dir.exists() && dir.isDirectory()) {
                FileHelper.writeLines(new File(dir,"pack.mcmeta"),MCMETA_LINES,false);
                List<IResourcePack> defaultPacks = getResourcePacks(Minecraft.getMinecraft());
                if(Objects.nonNull(defaultPacks)) defaultPacks.add(new FolderResourcePack(dir));
            } else TILDev.logError("The TILResources directory doesn't seem to exist. Were the resources copied correctly?");
        }
    }
    
    @Override public int getDisplayHeight() {
        return this.mc.displayHeight;
    }
    
    @Override public int getDisplayWidth() {
        return this.mc.displayWidth;
    }
    
    @Override public FontAPI getFont() {
        return this.font;
    }
    
    @Override public int getGUIScale() {
        int scale = Objects.nonNull(this.mc) && Objects.nonNull(this.mc.gameSettings) ? this.mc.gameSettings.guiScale : 0;
        return scale==0 ? 4 : scale;
    }
    
    @Override public @Nullable PlayerAPI<? extends EntityPlayer,EntityEntry> getPlayer() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.player) ? new ClientPlayer1_12_2(this.mc.player) : null;
    }

    @Override public RenderAPI getRenderer() {
        return this.render;
    }
    
    private @Nullable List<IResourcePack> getResourcePacks(Minecraft mc) {
        try {
            return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, mc, "field_110449_ao");
        } catch(Exception ex) {
            TILRef.logError("Unable to get resource pack list",ex);
            return null;
        }
    }

    /**
     * TODO Cache this?
     */
    @Override public MinecraftWindow getWindow() {
        ScaledResolution res = Objects.nonNull(this.mc) ? new ScaledResolution(this.mc) : null;
        if(Objects.isNull(res)) {
            TILRef.logFatal("Unable to get MinecraftWidnow since the Minecraft is null?");
            return new MinecraftWindow(1d,1d,0);
        }
        return new MinecraftWindow(res.getScaledWidth(),res.getScaledHeight(),res.getScaleFactor());
    }

    @Override public @Nullable WorldAPI<World> getWorld() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.world) ? new World1_12_2(this.mc.world) : null;
    }

    @Override public <S> boolean isCurrentScreen(S screen) {
        return Objects.nonNull(this.mc) && this.mc.currentScreen==screen;
    }

    @Override public boolean isCurrentScreenAPI() {
        return false;
    }

    @Override public boolean isDisplayFocused() {
        if(Objects.isNull(this.mc)) {
            TILRef.logError("Unable to determine display focus state for null Minecraft instance");
            return false;
        }
        try {
            return this.mc.addScheduledTask(() -> Display.isCreated() && Display.isActive()).get();
        } catch(ExecutionException | InterruptedException ex) {
            TILRef.logWarn("Unable to determine display focus state",ex);
            return false;
        }
    }

    @Override public boolean isFinishedLoading() {
        return !FMLClientHandler.instance().isLoading() && Objects.nonNull(this.mc) &&
                Objects.nonNull(this.mc.currentScreen);
    }

    @Override public boolean isFullScreen() {
        return Objects.nonNull(this.mc) && this.mc.isFullScreen();
    }

    @Override public boolean isLoading() {
        return FMLClientHandler.instance().isLoading();
    }

    @Override public boolean isPaused() {
        return Objects.nonNull(this.mc) && this.mc.isGamePaused();
    }
}
