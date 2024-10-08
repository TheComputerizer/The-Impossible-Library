package mods.thecomputerizer.theimpossiblelibrary.api.client;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class MinecraftAPI<M> extends AbstractWrapped<M> {
    
    protected final FontAPI<?> font;
    protected final RenderAPI renderer;
    
    protected MinecraftAPI(M mc, FontAPI<?> font, RenderAPI renderer) {
        super(mc);
        this.font = font;
        this.renderer = renderer;
    }

    public abstract void addResourcePackFolder(File dir);
    public abstract int getDisplayHeight();
    public abstract int getDisplayWidth();
    public abstract int getGUIScale();
    public abstract @Nullable PlayerAPI<?,?> getPlayer();
    public abstract MinecraftWindow getWindow();
    public abstract @Nullable WorldAPI<?> getWorld();

    public boolean hasPlayer() {
        return Objects.nonNull(getPlayer());
    }
    
    @IndirectCallers
    public boolean hasPlayerAndWorld() {
        return hasPlayer() && hasWorld();
    }
    
    @IndirectCallers
    public boolean hasScreen() {
        return false;
    }
    
    public boolean hasWorld() {
        return Objects.nonNull(getWorld());
    }
    
    @IndirectCallers public abstract <S> boolean isCurrentScreen(S screen);
    @IndirectCallers public abstract boolean isCurrentScreenAPI();
    public abstract boolean isDisplayFocused();
    public abstract boolean isFinishedLoading();
    public abstract boolean isFullScreen();
    
    public boolean isLoading() {
        return TILRef.getClientHandles().isLoading(this.wrapped);
    }
    
    public abstract boolean isPaused();
    
    @IndirectCallers
    public boolean isUnpausedAndFocused() {
        return isDisplayFocused() && !isPaused();
    }
    
    @IndirectCallers
    public void sendMessageToPlayer(TextAPI<?> text) {
        sendMessageToPlayer(text,null);
    }
    
    public void sendMessageToPlayer(TextAPI<?> text, @Nullable UUID uuid) {
        PlayerAPI<?,?> player = getPlayer();
        if(Objects.nonNull(player)) player.sendMessage(text,uuid);
    }
    
    public void sendStatusMessageToPlayer(TextAPI<?> text, boolean actionBar) {
        PlayerAPI<?,?> player = getPlayer();
        if(Objects.nonNull(player)) player.sendStatusMessage(text,actionBar);
    }
}