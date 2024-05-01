package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.Mappable;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Function;

@SuppressWarnings("unused")
public class NetworkHandler {

    private static final Mappable<?,MessageDirectionInfo<?>> DIRECTION_INFO = Mappable.makeSynchronized(HashMap::new);

    @SuppressWarnings("unchecked")
    public static <DIR> @Nullable MessageDirectionInfo<DIR> getDirectionInfo(DIR dir) {
        return (MessageDirectionInfo<DIR>)DIRECTION_INFO.get(dir);
    }

    @SuppressWarnings("unchecked")
    private static <DIR> MessageDirectionInfo<?> getOrInitDirectionInfo(DIR dir) {
        Mappable<DIR,MessageDirectionInfo<?>> map = (Mappable<DIR,MessageDirectionInfo<?>>)DIRECTION_INFO;
        map.putIfAbsent(dir,new MessageDirectionInfo<>(dir));
        return map.get(dir);
    }

    /**
     * Registers instantiates the network if necessary and registers queued packets.
     * Ignored if TILRef#CLIENT_ONLY is enabled
     */
    public static void load() {
        if(TILRef.CLIENT_ONLY) return;
        int id = 0;
        for(MessageDirectionInfo<?> info : DIRECTION_INFO.values()) {
            NetworkHelper.registerMessage(info,id);
            id++;
        }
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
        registerMsg(clazz,decoder,NetworkHelper.getDirToClientLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToClientLogin(Class<M> clazz, MessageHandlerAPI handler) {
        registerMsg(clazz,handler,NetworkHelper.getDirToClientLogin());
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
        registerMsg(clazz,decoder,NetworkHelper.getDirToServerLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageAPI<?>> void registerMsgToServerLogin(Class<M> clazz, MessageHandlerAPI handler) {
        registerMsg(clazz,handler,NetworkHelper.getDirToServerLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    private static <DIR,M extends MessageAPI<?>> void registerMsg(Class<M> clazz, Function<ByteBuf,M> decoder, DIR dir) {
        MessageDirectionInfo<?> dirInfo = getOrInitDirectionInfo(dir);
        dirInfo.getInfoSet().add(new MessageInfo<>(clazz,dirInfo,decoder));
    }

    /**
     * Message registration must happen before load is called
     */
    public static <DIR,M extends MessageAPI<?>> void registerMsg(Class<M> clazz, MessageHandlerAPI handler, DIR dir) {
        MessageDirectionInfo<?> dirInfo = getOrInitDirectionInfo(dir);
        dirInfo.getInfoSet().add(new MessageInfo<>(clazz,dirInfo,handler));
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

    }
}