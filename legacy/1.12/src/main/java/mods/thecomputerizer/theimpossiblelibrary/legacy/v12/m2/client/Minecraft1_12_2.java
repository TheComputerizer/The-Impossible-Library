package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import com.google.common.util.concurrent.ListenableFuture;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.entity.ClientPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.font.Font1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render.Render1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.lwjgl.opengl.Display;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.minecraft.util.math.BlockPos.ORIGIN;

public class Minecraft1_12_2 extends MinecraftAPI<Minecraft> {
    
    private static final List<String> MCMETA_LINES = Arrays.asList(
            "{","\t\"pack\": {","\t\t\"pack_format\": 3,",
            "\t\t\"description\": \"Relocated The Impossible Library resources\"", "\t}", "}");
    private static final String DEFAULT_RESOURCE_PACKS_FIELD = NAMED_ENV ? "defaultResourcePacks" : "field_110449_ao";

    public static Minecraft1_12_2 getInstance() {
        return new Minecraft1_12_2(Minecraft.getMinecraft());
    }

    private Minecraft1_12_2(Object mc) {
        super(mc,new Font1_12_2(),new Render1_12_2());
    }
    
    @Override public void addResourcePackFolder(File dir) {
        if(DEV) {
            TILDev.logInfo("Attempting to manually define dev resources");
            if(dir.exists() && dir.isDirectory()) {
                FileHelper.writeLines(new File(dir,"pack.mcmeta"),MCMETA_LINES,false);
                List<IResourcePack> defaultPacks = getResourcePacks(this.wrapped);
                if(Objects.nonNull(defaultPacks)) defaultPacks.add(new FolderResourcePack(dir));
            } else TILDev.logError("The TILResources directory doesn't seem to exist. Were the resources copied correctly?");
        }
    }
    
    @Override public @Nullable Object getCurrentScreen() {
        return getIfNotNull(w -> w.currentScreen);
    }
    
    @Override public int getDisplayHeight() {
        return getIfNotNullOrDefault(w -> w.displayHeight,0);
    }
    
    @Override public int getDisplayWidth() {
        return getIfNotNullOrDefault(w -> w.displayWidth,0);
    }
    
    @Override public int getGUIScale() {
        return wrapIfNotNullOrDefault(w -> w.gameSettings,s -> s.guiScale,4);
    }
    
    @Override public @Nullable PlayerAPI<? extends EntityPlayer,EntityEntry> getPlayer() {
        return wrapIfNotNull(w -> w.player,ClientPlayer1_12_2::new);
    }
    
    private @Nullable RayTraceResult getTarget() {
        return getIfNotNull(w -> w.objectMouseOver);
    }
    
    @Override public @Nullable BlockEntityAPI<?,?> getTargetBlockEntity() {
        RayTraceResult target = getTarget();
        if(Objects.isNull(target) || Objects.nonNull(target.entityHit)) return null;
        BlockPos pos = target.getBlockPos();
        return Objects.isNull(this.wrapped) || pos==ORIGIN ? null :
                WrapperHelper.wrapBlockEntity(this.wrapped.world.getTileEntity(pos));
    }
    
    @Override public @Nullable EntityAPI<?,?> getTargetEntity() {
        RayTraceResult target = getTarget();
        return Objects.isNull(target) ? null : WrapperHelper.wrapEntity(target.entityHit);
    }
    
    private @Nullable List<IResourcePack> getResourcePacks(Minecraft mc) {
        try {
            return Hacks.getFieldDirect(mc,DEFAULT_RESOURCE_PACKS_FIELD);
        } catch(Exception ex) {
            TILRef.logError("Unable to get resource pack list",ex);
            return null;
        }
    }

    /**
     * TODO Cache this?
     */
    @Override public MinecraftWindow getWindow() {
        ScaledResolution res = getIfNotNull(ScaledResolution::new);
        if(Objects.isNull(res)) {
            TILRef.logFatal("Unable to get MinecraftWindow since the Minecraft is null?");
            return new MinecraftWindow(1d,1d,0);
        }
        return new MinecraftWindow(res.getScaledWidth(),res.getScaledHeight(),res.getScaleFactor());
    }

    @Override public @Nullable WorldAPI<World> getWorld() {
        return wrapIfNotNull(w -> w.world,World1_12_2::new);
    }
    
    /**
     * Will return true if the current and input screen are both null
     */
    @Override public <S> boolean isCurrentScreen(S screen) {
        return getIfNotNull(w -> w.currentScreen)==screen;
    }

    @Override public boolean isCurrentScreenAPI() {
        return false;
    }

    @Override public boolean isDisplayFocused() {
        if(Objects.isNull(this.wrapped)) {
            TILRef.logError("Unable to determine display focus state for null Minecraft instance");
            return false;
        }
        try {
            return this.wrapped.addScheduledTask(() -> Display.isCreated() && Display.isActive()).get();
        } catch(ExecutionException | InterruptedException ex) {
            TILRef.logWarn("Unable to determine display focus state",ex);
            return false;
        }
    }

    @Override public boolean isFinishedLoading() {
        return !FMLClientHandler.instance().isLoading() && notNullGetter(w -> w.currentScreen);
    }

    @Override public boolean isFullScreen() {
        return getIfNotNullOrDefault(Minecraft::isFullScreen,false);
    }

    @Override public boolean isLoading() {
        return FMLClientHandler.instance().isLoading();
    }

    @Override public boolean isPaused() {
        return getIfNotNullOrDefault(Minecraft::isFullScreen,false) ||
               (notNullGetter(w -> w.world) && isPauseScreen());
    }
    
    private boolean isPauseScreen() {
        return isPauseScreen(getCurrentScreen());
    }
    
    private boolean isPauseScreen(@Nullable Object screen) {
        return screen instanceof GuiScreen && ((GuiScreen)screen).doesGuiPauseGame();
    }
    
    @Override public <T> Supplier<T> scheduleReturnable(Supplier<T> supplier) {
        if(Objects.isNull(this.wrapped)) return () -> null;
        final ListenableFuture<T> future = this.wrapped.addScheduledTask(supplier::get);
        return () -> {
            try {
                future.get();
            } catch(ExecutionException|InterruptedException ex) {
                TILRef.logError("Failed to retrieve ListenableFuture instance!",ex);
            }
            return null;
        };
    }
    
    @Override public void scheduleRunnable(Runnable runnable) {
        if(Objects.isNull(this.wrapped)) return;
        try {
            this.wrapped.addScheduledTask(runnable).get();
        } catch(ExecutionException|InterruptedException ex) {
            TILRef.logError("Failed to execute scheduled Runnable!",ex);
        }
    }
}