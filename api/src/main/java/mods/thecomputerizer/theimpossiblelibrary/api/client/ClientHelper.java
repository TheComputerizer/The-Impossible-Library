package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class ClientHelper {
    
    static Map<String,String> optionsCache;
    
    @IndirectCallers
    public static void addResourcePackFolder(File dir) {
        MinecraftAPI<?> api = getMinecraft();
        if(Objects.nonNull(api)) api.addResourcePackFolder(dir);
        else TILRef.logError("Unable to add resource pack folder `{}` since MinecraftAPI<?> is null");
    }
    
    /**
     * Returns true if the check fails
     */
    private static boolean checkString(@Nullable String str, String msg, Object ... args) {
        if(Objects.isNull(str) || str.isEmpty()) {
            TILRef.logError(msg,args);
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if the check fails
     */
    private static boolean checkValue(@Nullable String value, String type, Object ... args) {
        return checkString(value,"Cannot get null or empty option value as "+type+" (from {})",args);
    }
    
    public static @Nullable String getCachedOption(String key) {
        if(Objects.isNull(key) || key.isEmpty()) {
            TILRef.logError("Cannot get option from null or empty key!");
            return null;
        }
        return getOptionsCache().get(key);
    }
    
    @IndirectCallers
    public static boolean getCachedOptionBoolean(String key) {
        String value = getCachedOption(key);
        return !checkValue(value,"boolean",key) && Boolean.parseBoolean(value);
    }
    
    @IndirectCallers
    public static byte getCachedOptionByte(String key) {
        return getCachedOptionByte(key,(byte)0);
    }
    
    public static byte getCachedOptionByte(String key, byte defaultValue) {
        return getCachedOptionNumber(key,defaultValue);
    }
    
    @IndirectCallers
    public static double getCachedOptionDouble(String key) {
        return getCachedOptionDouble(key,0d);
    }
    
    public static double getCachedOptionDouble(String key, double defaultValue) {
        return getCachedOptionNumber(key,defaultValue);
    }
    
    @IndirectCallers
    @SuppressWarnings({"unchecked","DataFlowIssue"})
    public static <E extends Enum<E>> E getCachedOptionEnum(String key, E defualtValue) {
        String value = getCachedOption(key);
        Class<E> clazz = (Class<E>)defualtValue.getClass();
        return checkValue(value,"enum ("+clazz.getName()+")",key) ? defualtValue : Enum.valueOf(clazz,value);
    }
    
    @IndirectCallers
    public static float getCachedOptionFloat(String key) {
        return getCachedOptionFloat(key,0f);
    }
    
    public static float getCachedOptionFloat(String key, float defaultValue) {
        return getCachedOptionNumber(key,defaultValue);
    }
    
    @IndirectCallers
    public static int getCachedOptionInt(String key) {
        return getCachedOptionInt(key,0);
    }
    
    public static int getCachedOptionInt(String key, int defaultValue) {
        return getCachedOptionNumber(key,defaultValue);
    }
    
    @IndirectCallers
    public static long getCachedOptionLong(String key) {
        return getCachedOptionLong(key,0L);
    }
    
    public static long getCachedOptionLong(String key, long defaultValue) {
        return getCachedOptionNumber(key,defaultValue);
    }
    
    @IndirectCallers
    public static short getCachedOptionShort(String key) {
        return getCachedOptionShort(key,(short)0);
    }
    
    public static short getCachedOptionShort(String key, short defaultValue) {
        return getCachedOptionNumber(key,defaultValue);
    }
    
    /**
     * Gets volume level of the input sound category.
     * This should only be called during startup if the volume level is needed before the options field is initialized.
     */
    public static float getCachedOptionSoundCategory(String name) {
        String key = "soundCategory_"+("records".equals(name) ? "record" : name);
        return getCachedOptionFloat(key,1f);
    }
    
    private static <N extends Number> N getCachedOptionNumber(String key, N defaultValue) {
        String value = getCachedOption(key);
        String type = defaultValue.getClass().getSimpleName().toLowerCase();
        if(type.equals("integer")) type = "int";
        return checkValue(value,type,key) ? defaultValue : GenericUtils.parseNumber(value,defaultValue);
    }
    
    @IndirectCallers
    public static @Nullable Object getCurrentScreen() {
        final MinecraftAPI<?> mc = getMinecraft();
        if(Objects.isNull(mc)) return null;
        return mc.scheduleReturnable(mc::getCurrentScreen).get();
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
    
    /**
     * Returns a direct map of lines read in from options.txt.
     * Each line in the options.txt file is parsed as 'key:value'
     */
    public static Map<String,String> getOptionsCache() {
        if(Objects.nonNull(optionsCache)) return optionsCache;
        final Map<String,String> cacheBuilder = new HashMap<>();
        for(String line : FileHelper.toLines("options.txt")) {
            int split = line.indexOf(':');
            if(split<=0) continue; //Index 0 places it at the start of the line, and we don't want empty keys
            String key = line.substring(0,split);
            String value = line.substring(split+1);
            if(!value.isEmpty()) cacheBuilder.put(key,value);
        }
        optionsCache = Collections.unmodifiableMap(cacheBuilder);
        return optionsCache;
    }
    
    public static @Nullable PlayerAPI<?,?> getPlayer() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getPlayer() : null;
    }
    
    public static RenderAPI getRenderer() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getRenderer() : null;
    }
    
    @IndirectCallers
    public static @Nullable BlockEntityAPI<?,?> getTargetBlockEntity() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getTargetBlockEntity() : null;
    }
    
    @IndirectCallers
    public static @Nullable EntityAPI<?,?> getTargetEntity() {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.getTargetEntity() : null;
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
    public static <T> Supplier<T> scheduleReturnable(Supplier<T> supplier) {
        MinecraftAPI<?> api = getMinecraft();
        return Objects.nonNull(api) ? api.scheduleReturnable(supplier) : () -> null;
    }
    
    public static void scheduleRunnable(Runnable runnable) {
        MinecraftAPI<?> api = getMinecraft();
        if(Objects.nonNull(api)) api.scheduleRunnable(runnable);
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
        final MinecraftAPI<?> api = getMinecraft();
        if(Objects.isNull(api)) {
            TILRef.logWarn("Cannot send {}message `{}` since MinecraftAPI<?> is null",isStatusMsg ? "status " : "",
                           msg.getOriginal());
            return;
        }
        scheduleRunnable(() -> {
            if(isStatusMsg) api.sendStatusMessageToPlayer(msg,isActionBar);
            else api.sendMessageToPlayer(msg,uuid);
        });
    }
}