package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.JVMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.Mappable;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.*;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEBUG_NETWORK;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.CLIENT_ONLY;

@SuppressWarnings("unused")
public class NetworkHandler {
    
    /**
     * In case we need to allow messages to be registered a bit later than normal such as in Fabric.
     */
    private static final boolean ALLOW_LATE_REGISTRATION = CoreAPI.isFabric();
    private static final boolean BOTH_SIDES = CoreAPI.isLegacy();
    private static final boolean DEBUG = DEBUG_NETWORK;
    private static final boolean ENABLE_LOGIN = !CoreAPI.isForge() && !CoreAPI.isLegacy(); //TODO There should be a better way to handle login packet registration
    private static final Mappable<?,MessageDirectionInfo<?>> DIRECTION_INFO = Mappable.makeSynchronized(HashMap::new);
    
    @Getter private static boolean initialized;
    /**
     * Late registration cutoff
     */
    private static boolean loadComplete;
    
    static boolean canRegisterLate() {
        return ALLOW_LATE_REGISTRATION && !CLIENT_ONLY && !loadComplete;
    }

    public static <DIR> @Nullable MessageDirectionInfo<DIR> getDirectionInfo(DIR dir) {
        return GenericUtils.cast(DIRECTION_INFO.get(dir));
    }

    private static <DIR> MessageDirectionInfo<?> getOrInitDirectionInfo(DIR dir) {
        Mappable<DIR,MessageDirectionInfo<?>> map = GenericUtils.cast(DIRECTION_INFO);
        if(Objects.isNull(map)) {
            TILRef.logError("Failed to get or initialize direction info for {}",dir);
            return null;
        }
        map.putIfAbsent(dir,new MessageDirectionInfo<>(dir));
        return map.get(dir);
    }
    
    public static <DIR> boolean isRegistered(Class<?> msgClass, DIR dir) {
        MessageDirectionInfo<?> info = DIRECTION_INFO.get(dir);
        return Objects.nonNull(info) && info.contains(msgClass);
    }

    /**
     * Registers and instantiates the network if necessary and registers queued packets.
     * Ignored if TILRef#CLIENT_ONLY is enabled
     */
    public static void load() {
        if(CLIENT_ONLY) {
            if(DEBUG) TILRef.logInfo("Skipping network registration since CLIENT_ONLY is enabled");
            return;
        }
        int id = 0;
        if(DEBUG) {
            if(DIRECTION_INFO.isEmpty()) TILRef.logInfo("There are no network messages to register");
            else TILRef.logInfo("Loading network messages for {} directions",DIRECTION_INFO.size());
        }
        NetworkHelper.messageRegistrationStarted();
        for(MessageDirectionInfo<?> info : DIRECTION_INFO.values()) {
            NetworkHelper.registerMessage(info,id);
            if(DEBUG) TILRef.logInfo("Registered network direction info: {} (id={})",info,id);
            if(JVMHelper.isJava17()) id++;
        }
        NetworkHelper.messageRegistrationFinished();
        initialized = true;
    }
    
    private static void logDirectionRegistrationDebug(Class<?> msgClass, boolean client) {
        if(DEBUG) {
            String dir = client ? "client" : "server";
            TILRef.logInfo("Tried to register {} as a {} login message, but the login direction is disabled!"+
                           "Registering as a {} message instead",msgClass,dir,dir);
        }
    }
    
    public static void onLoadComplete() {
        loadComplete = true;
    }
    
    public static <DIR> @Nullable MessageDirectionInfo<DIR> readDirectionInfo(ByteBuf buf) {
        return getDirectionInfo(NetworkHelper.readDir(buf));
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToClient(
            Class<M> clazz, Function<ByteBuf,M> decoder) {
        registerMsg(clazz,decoder,NetworkHelper.getDirToClient());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToClient(Class<M> clazz, MessageHandlerAPI handler) {
        registerMsg(clazz,handler,NetworkHelper.getDirToClient());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToClientLogin(Class<M> clazz, Function<ByteBuf,M> decoder) {
        if(ENABLE_LOGIN) registerMsg(clazz,decoder,NetworkHelper.getDirToClientLogin());
        else {
            logDirectionRegistrationDebug(clazz,true);
            registerMsgToClient(clazz,decoder);
        }
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToClientLogin(Class<M> clazz, MessageHandlerAPI handler) {
        if(ENABLE_LOGIN) registerMsg(clazz,handler,NetworkHelper.getDirToClientLogin());
        else {
            logDirectionRegistrationDebug(clazz,true);
            registerMsgToClient(clazz,handler);
        }
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToServer(Class<M> clazz, Function<ByteBuf,M> decoder) {
        registerMsg(clazz,decoder,NetworkHelper.getDirToServer());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToServer(Class<M> clazz, MessageHandlerAPI handler) {
        registerMsg(clazz,handler,NetworkHelper.getDirToServer());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToServerLogin(Class<M> clazz, Function<ByteBuf,M> decoder) {
        if(ENABLE_LOGIN) registerMsg(clazz,decoder,NetworkHelper.getDirToServerLogin());
        else {
            logDirectionRegistrationDebug(clazz,false);
            registerMsgToServer(clazz,decoder);
        }
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToServerLogin(Class<M> clazz, MessageHandlerAPI handler) {
        if(ENABLE_LOGIN) registerMsg(clazz,handler,NetworkHelper.getDirToServerLogin());
        else {
            logDirectionRegistrationDebug(clazz,false);
            registerMsgToServer(clazz,handler);
        }
    }

    /**
     * Message registration must happen before load is called.
     * The direction may be null in the case of a client receiver trying to register on the server side
     */
    private static <DIR,M extends MessageAPI<?>> void registerMsg(Class<M> clazz, Function<ByteBuf,M> decoder, DIR dir) {
        if(DEBUG) TILRef.logInfo("Registering message {} to direction {} with function {}",clazz,dir,decoder);
        registerMsg(dir,dirInfo -> new MessageInfo<>(clazz,dirInfo,decoder));
        if(DEBUG) TILRef.logInfo("Successfully registered {} function handler for message {}",dir,clazz);
    }

    /**
     * Message registration must happen before load is called
     * The direction may be null in the case of a client receiver trying to register on the server side
     */
    public static <DIR,M extends MessageAPI<?>> void registerMsg(Class<M> clazz, MessageHandlerAPI handler, DIR dir) {
        if(DEBUG)
            TILRef.logInfo("Registering message {} to direction {} with handler type {}",clazz,dir,
                           Objects.nonNull(handler) ? handler.getClass() : null);
        registerMsg(dir,dirInfo -> new MessageInfo<>(clazz,dirInfo,handler));
        if(DEBUG) TILRef.logInfo("Successfully registered {} handler for message {}",dir,clazz);
    }
    
    /**
     * Message registration must happen before load is called.
     * Register a message on the designated side.
     * If the 'both' flag is enabled, register an identical message on the opposite side.
     * Registering more than 1 MessageInfo for the same message class in the same MessageDirectionInfo is not supported.
     */
    private static <DIR> void registerMsg(DIR dir, Function<MessageDirectionInfo<?>,MessageInfo<?>> infoSupplier) {
        Collection<MessageDirectionInfo<DIR>> infos = null;
        if(Objects.nonNull(dir)) {
            MessageDirectionInfo<?> info = getOrInitDirectionInfo(dir);
            if(Objects.nonNull(info)) {
                info.supply(infoSupplier);
                infos = new ArrayList<>();
                infos.add(GenericUtils.cast(info));
            } else TILRef.logError("Failed to register message for direction {}",dir);
            if(BOTH_SIDES) {
                DIR oppositeDir = NetworkHelper.getOppositeDir(dir);
                if(DEBUG)
                    TILRef.logInfo("Registering message to opposite direction {} (direction={})",oppositeDir,dir);
                MessageDirectionInfo<?> oppositeInfo = getOrInitDirectionInfo(oppositeDir);
                if(Objects.nonNull(oppositeInfo)) {
                    oppositeInfo.supply(infoSupplier);
                    if(Objects.isNull(infos)) infos = new ArrayList<>();
                    infos.add(GenericUtils.cast(oppositeInfo));
                } else TILRef.logError("Failed to register message for opposite direction {}",oppositeDir);
            }
        }
        if(initialized && canRegisterLate()) NetworkHelper.registerLateMessages(infos);
    }

    /**
     * Message registration must happen before load is called
     */
    public static void registerMsgs(MessageInfo<?> ... infos) {
        registerMsgs(Arrays.asList(infos));
    }

    /**
     * Message registration must happen before load is called
     */
    public static void registerMsgs(Iterable<MessageInfo<?>> infos) {
        for(MessageInfo<?> info : infos)
            registerMsg(info.getMsgClass(),new MessageHandlerDefault(info::decode),info.getDirectionInfo().getDirection());
    }
}