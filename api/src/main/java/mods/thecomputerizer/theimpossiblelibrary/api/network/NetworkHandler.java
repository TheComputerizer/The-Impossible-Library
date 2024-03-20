package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.Mappable;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.Wrapperable;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageHandlerDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class NetworkHandler {

    private static final Wrapperable<MessageInfo<?,? extends MessageWrapperAPI<?,?>>> MESSAGE_INFOS =
            Wrapperable.makeSynchronized(ArrayList::new);
    private static final Mappable<Class<? extends MessageWrapperAPI<?,?>>,MessageInfo<?,?>> REGISTERED_MESSAGES =
            Mappable.makeSynchronized(HashMap::new);

    @SuppressWarnings("unchecked")
    public static <M extends MessageWrapperAPI<?,?>> @Nullable MessageInfo<?,M> getMessageInfo(Class<M> clazz) {
        MessageInfo<?,?> info = REGISTERED_MESSAGES.get(clazz);
        return Objects.nonNull(info) ? (MessageInfo<?,M>)info : null;
    }

    /**
     * Registers instantiates the network if necessary and registers queued packets.
     * Ignored if TILRef#CLIENT_ONLY is enabled
     */
    public static void load() {
        REGISTERED_MESSAGES.clear();
        int id = 0;
        if(TILRef.CLIENT_ONLY) return;
        for(MessageInfo<?,? extends MessageWrapperAPI<?,?>> info : MESSAGE_INFOS) {
            info.register(id);
            REGISTERED_MESSAGES.put(info.getMsgClass(),info);
            id++;
        }
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToClient(
            Class<M> clazz, Function<ByteBuf,M> decoder) {
        registerMsg(clazz,decoder,NetworkHelper.getDirToClient());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToClient(
            Class<M> clazz, Supplier<? extends MessageHandlerDefault> customHandler) {
        registerMsg(clazz,customHandler,NetworkHelper.getDirToClient());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToClientLogin(
            Class<M> clazz, Function<ByteBuf,M> decoder) {
        registerMsg(clazz,decoder,NetworkHelper.getDirToClientLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToClientLogin(
            Class<M> clazz, Supplier<? extends MessageHandlerDefault> customHandler) {
        registerMsg(clazz,customHandler,NetworkHelper.getDirToClientLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToServer(
            Class<M> clazz, Function<ByteBuf,M> decoder) {
        registerMsg(clazz,decoder,NetworkHelper.getDirToServer());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToServer(
            Class<M> clazz, Supplier<? extends MessageHandlerDefault> customHandler) {
        registerMsg(clazz,customHandler,NetworkHelper.getDirToServer());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToServerLogin(
            Class<M> clazz, Function<ByteBuf,M> decoder) {
        registerMsg(clazz,decoder,NetworkHelper.getDirToServerLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    public static <M extends MessageWrapperAPI<?,?>> void registerMsgToServerLogin(
            Class<M> clazz, Supplier<? extends MessageHandlerDefault> customHandler) {
        registerMsg(clazz,customHandler,NetworkHelper.getDirToServerLogin());
    }

    /**
     * Message registration must happen before load is called
     */
    private static <DIR,M extends MessageWrapperAPI<?,?>> void registerMsg(
            Class<M> clazz, Function<ByteBuf,M> decoder, DIR dir) {
        MESSAGE_INFOS.add(new MessageInfo<>(clazz,dir,decoder));
    }

    /**
     * Message registration must happen before load is called
     */
    public static <DIR,M extends MessageWrapperAPI<?,?>> void registerMsg(
            Class<M> clazz, Supplier<? extends MessageHandlerDefault> customHandler, DIR dir) {
        MESSAGE_INFOS.add(new MessageInfo<>(clazz,dir,customHandler));
    }

    /**
     * Message registration must happen before load is called
     */
    public static void registerMsgs(MessageInfo<?,?> ... infos) {
        registerMsgs(Arrays.asList(infos));
    }

    /**
     * Message registration must happen before load is called
     */
    public static void registerMsgs(Iterable<MessageInfo<?,?>> infos) {

    }
}
