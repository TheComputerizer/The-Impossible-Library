package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font.Font1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.Render1_16_5;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

public class Minecraft1_16_5 extends MinecraftAPI<Minecraft> {
    
    public static MinecraftAPI<?> getInstance() {
        return new Minecraft1_16_5(Minecraft.getInstance());
    }
    
    public Minecraft1_16_5(Minecraft mc) {
        super(mc,new Font1_16_5(),new Render1_16_5());
    }
    
    @Override public void addResourcePackFolder(File dir) {}
    
    @Override public int getDisplayHeight() {
        return this.wrapped.getWindow().getHeight();
    }
    
    @Override public int getDisplayWidth() {
        return this.wrapped.getWindow().getWidth();
    }
    
    @Override public int getGUIScale() {
        return Objects.nonNull(this.wrapped) && Objects.nonNull(this.wrapped.options) ? this.wrapped.options.guiScale : 0;
    }
    
    @Override public @Nullable PlayerAPI<?,?> getPlayer() {
        return WrapperHelper.wrapPlayer(this.wrapped.player);
    }
    
    private @Nullable RayTraceResult getTarget() {
        return Objects.nonNull(this.wrapped) ? this.wrapped.hitResult : null;
    }
    
    @Override public @Nullable BlockEntityAPI<?,?> getTargetBlockEntity() {
        RayTraceResult target = getTarget();
        if(target instanceof BlockRayTraceResult && target.getType()==BLOCK) {
            BlockPos pos = ((BlockRayTraceResult)target).getBlockPos();
            World world = this.wrapped.level;
            return Objects.nonNull(world) ? WrapperHelper.wrapBlockEntity(world.getBlockEntity(pos)) : null;
        }
        return null;
    }
    
    @Override public @Nullable EntityAPI<?,?> getTargetEntity() {
        RayTraceResult target = getTarget();
        return target instanceof EntityRayTraceResult ?
                WrapperHelper.wrapEntity(((EntityRayTraceResult)target).getEntity()) : null;
    }
    
    /**
     * TODO Cache this?
     */
    @Override public MinecraftWindow getWindow() {
        MainWindow window = Objects.nonNull(this.wrapped) ? this.wrapped.getWindow() : null;
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
        return Objects.nonNull(this.wrapped) && this.wrapped.isPaused();
    }
}