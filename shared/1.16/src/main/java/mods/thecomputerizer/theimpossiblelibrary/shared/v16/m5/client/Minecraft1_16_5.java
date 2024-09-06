package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font.Font1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.Render1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Player1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public abstract class Minecraft1_16_5 implements MinecraftAPI {

    protected final Minecraft mc;
    protected final Font1_16_5 font;
    protected final Render1_16_5 render;

    protected Minecraft1_16_5(Minecraft mc, Font1_16_5 font, Render1_16_5 render) {
        this.mc = mc;
        this.font = font;
        this.render = render;
    }
    
    @Override public void addResourcePackFolder(File dir) {}
    
    @Override public Font1_16_5 getFont() {
        return this.font;
    }
    
    @Override abstract public @Nullable Player1_16_5<?,?> getPlayer();

    @Override public Render1_16_5 getRenderer() {
        return this.render;
    }

    @Override public @Nullable World1_16_5<?> getWorld() {
        return Objects.nonNull(this.mc) && Objects.nonNull(this.mc.level) ? (World1_16_5<?>)WrapperHelper.wrapWorld(this.mc.level) : null;
    }

    @Override public <S> boolean isCurrentScreen(S screen) {
        return Objects.nonNull(this.mc) && this.mc.screen==screen;
    }

    @Override public boolean isCurrentScreenAPI() {
        return false;
    }

    @Override public boolean isDisplayFocused() {
        return Objects.nonNull(this.mc) && this.mc.isWindowActive();
    }

    @Override public boolean isFinishedLoading() {
        return !isLoading();
    }

    @Override public boolean isLoading() {
        return Objects.isNull(this.mc);
    }

    @Override public boolean isPaused() {
        return Objects.nonNull(this.mc) && this.mc.isPaused();
    }
}