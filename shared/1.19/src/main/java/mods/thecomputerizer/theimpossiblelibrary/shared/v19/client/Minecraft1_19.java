package mods.thecomputerizer.theimpossiblelibrary.shared.v19.client;

import com.mojang.blaze3d.platform.Window;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.font.Font1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.render.Render1_19;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static net.minecraft.world.phys.HitResult.Type.BLOCK;

public class Minecraft1_19 extends MinecraftAPI<Minecraft> {
    
    public static MinecraftAPI<?> getInstance() {
        return new Minecraft1_19(Minecraft.getInstance());
    }
    
    protected Minecraft1_19(Minecraft mc, Font1_19 font, Render1_19 render) {
        super(mc,font,render);
    }
    
    public Minecraft1_19(Minecraft mc) {
        super(mc,new Font1_19(),new Render1_19());
    }
    
    @Override public void addResourcePackFolder(File dir) {}
    
    @Override public @Nullable Object getCurrentScreen() {
        return Objects.nonNull(this.wrapped) ? this.wrapped.screen : null;
    }
    
    @Override public int getDisplayHeight() {
        return this.wrapped.getWindow().getHeight();
    }
    
    @Override public int getDisplayWidth() {
        return this.wrapped.getWindow().getWidth();
    }
    
    @SuppressWarnings("ConstantValue")
    @Override public int getGUIScale() {
        return Objects.nonNull(this.wrapped) && Objects.nonNull(this.wrapped.options) ?
                this.wrapped.options.guiScale().get() : 0;
    }
    
    @Override public @Nullable PlayerAPI<?,?> getPlayer() {
        return WrapperHelper.wrapPlayer(this.wrapped.player);
    }
    
    private @Nullable HitResult getTarget() {
        return Objects.nonNull(this.wrapped) ? this.wrapped.hitResult : null;
    }
    
    @Override public @Nullable BlockEntityAPI<?,?> getTargetBlockEntity() {
        HitResult target = getTarget();
        if(target instanceof BlockHitResult && target.getType()==BLOCK) {
            BlockPos pos = ((BlockHitResult)target).getBlockPos();
            Level world = this.wrapped.level;
            return Objects.nonNull(world) ? WrapperHelper.wrapBlockEntity(world.getBlockEntity(pos)) : null;
        }
        return null;
    }
    
    @Override public @Nullable EntityAPI<?,?> getTargetEntity() {
        HitResult target = getTarget();
        return target instanceof EntityHitResult ?
                WrapperHelper.wrapEntity(((EntityHitResult)target).getEntity()) : null;
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
        return Objects.nonNull(this.wrapped) && (this.wrapped.isPaused() ||
                (Objects.nonNull(this.wrapped.level) && isPauseScreen(getCurrentScreen())));
    }
    
    private boolean isPauseScreen(@Nullable Object screenObj) {
        return screenObj instanceof Screen screen && screen.isPauseScreen();
    }
    
    @Override public <T> Supplier<T> scheduleReturnable(Supplier<T> supplier) {
        final CompletableFuture<T> future = this.wrapped.submit(supplier);
        return () -> {
            try {
                future.get();
            } catch(ExecutionException|InterruptedException ex) {
                TILRef.logError("Failed to retrieve CompletableFuture instance!",ex);
            }
            return null;
        };
    }
    
    @Override public void scheduleRunnable(Runnable runnable) {
        this.wrapped.submit(runnable).getNow(null);
    }
}