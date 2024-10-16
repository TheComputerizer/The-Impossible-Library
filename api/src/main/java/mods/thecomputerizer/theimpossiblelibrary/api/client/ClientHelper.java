package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class ClientHelper {
    
    @IndirectCallers
    public static void addResourcePackFolder(File dir) {
        MinecraftAPI<?> api = getMinecraft();
        if(Objects.nonNull(api)) api.addResourcePackFolder(dir);
        else TILRef.logError("Unable to add resource pack folder `{}` since MinecraftAPI<?> is null");
    }
    
    public static int getDisplayHeight() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getDisplayHeight() : 1;
    }
    
    public static int getDisplayWidth() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getDisplayWidth() : 1;
    }
    
    public static @Nullable FontAPI<?> getFont() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getFont() : null;
    }
    
    public static int getGuiScale() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getGUIScale() : 0;
    }
    
    public static @Nullable MinecraftAPI<?> getMinecraft() {
        return TILRef.getClientSubAPI(ClientAPI::getMinecraft);
    }
    
    public static @Nullable PlayerAPI<?,?> getPlayer() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getPlayer() : null;
    }
    
    public static @Nullable RenderAPI getRenderer() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getRenderer() : null;
    }
    
    public static @Nullable MinecraftWindow getWindow() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getWindow() : null;
    }
    
    public static @Nullable WorldAPI<?> getWorld() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getWorld() : null;
    }
    
    @IndirectCallers
    public static boolean isDisplayFocused() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.isNull(api) || api.isDisplayFocused();
    }
    
    @IndirectCallers
    public static boolean isFinishedLoading() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) && api.isFinishedLoading();
    }
    
    @IndirectCallers
    public static boolean isFullScreen() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) && api.isFullScreen();
    }
    
    public static boolean isLoading() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) && api.isLoading();
    }
    
    @IndirectCallers
    public static boolean isPaused() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) && api.isPaused();
    }
    
    @IndirectCallers
    public static void sendMessage(TextAPI<?> msg) {
        sendMessage(msg,null,false,false);
    }
    
    @IndirectCallers
    public static void sendMessage(TextAPI<?> msg, UUID uuid) {
        sendMessage(msg,uuid,false,false);
    }
    
    @IndirectCallers
    public static void sendMessage(TextAPI<?> msg, boolean isStatusMsg) {
        sendMessage(msg,null,isStatusMsg,false);
    }
    
    @IndirectCallers
    public static void sendMessage(TextAPI<?> msg, boolean isStatusMsg, boolean isActionBar) {
        sendMessage(msg,null,isStatusMsg,isActionBar);
    }
    
    public static void sendMessage(TextAPI<?> msg, @Nullable UUID uuid, boolean isStatusMsg, boolean isActionBar) {
        MinecraftAPI<?> api = getMinecraft();
        if(Objects.isNull(api)) {
            TILRef.logWarn("Cannot send {}message `{}` since MinecraftAPI<?> is null",isStatusMsg ? "status " : "",
                           msg.getOriginal());
            return;
        }
        if(isStatusMsg) api.sendStatusMessageToPlayer(msg,isActionBar);
        else api.sendMessageToPlayer(msg,uuid);
    }
}