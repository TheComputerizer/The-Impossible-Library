package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public interface MinecraftAPI {

    FontAPI getFont();
    @Nullable PlayerAPI<?> getPlayer();
    RenderAPI getRenderer();
    @Nullable ScreenAPI<?> getScreen();
    MinecraftWindow getWindow();
    @Nullable WorldAPI<?> getWorld();

    default boolean hasPlayer() {
        return Objects.nonNull(getPlayer());
    }

    default boolean hasPlayerAndWorld() {
        return hasPlayer() && hasWorld();
    }

    default boolean hasScreen() {
        return Objects.nonNull(getScreen());
    }

    default boolean hasWorld() {
        return Objects.nonNull(getWorld());
    }

    <S> boolean isCurrentScreen(S screen);
    boolean isCurrentScreenAPI(ScreenAPI<?> screen);
    boolean isDisplayFocused();
    boolean isFinishedLoading();
    boolean isFullScreen();
    boolean isLoading();
    boolean isPaused();

    default boolean isUnpausedAndFocused() {
        return isDisplayFocused() && !isPaused();
    }

    default void sendMessageToPlayer(TextAPI<?> text) {
        sendMessageToPlayer(text,null);
    }

    default void sendMessageToPlayer(TextAPI<?> text, @Nullable UUID uuid) {
        PlayerAPI<?> player = getPlayer();
        if(Objects.nonNull(player)) player.sendMessage(text,uuid);
    }

    default void sendStatusMessageToPlayer(TextAPI<?> text, boolean actionBar) {
        PlayerAPI<?> player = getPlayer();
        if(Objects.nonNull(player)) player.sendStatusMessage(text,actionBar);
    }

    <S> void setScreen(@Nullable S screen);
    void setScreenAPI(@Nullable ScreenAPI<?> screen);
}