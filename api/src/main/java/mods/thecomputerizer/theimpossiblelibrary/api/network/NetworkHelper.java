package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHelper {

    public static <DIR> @Nullable DIR getDirToClient() {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getDirToClient() : null;
    }

    public static <DIR> @Nullable DIR getDirToClientLogin() {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getDirToClientLogin() : null;
    }

    public static <DIR> @Nullable DIR getDirToServer() {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getDirToServer() : null;
    }

    public static <DIR> @Nullable DIR getDirToServerLogin() {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getDirToServerLogin() : null;
    }

    public static <N> @Nullable N getNetwork() {
        NetworkAPI<N,?> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getNetwork() : null;
    }

    @SuppressWarnings("unchecked")
    public static <N,DIR> @Nullable NetworkAPI<N,DIR> getNetworkAPI() {
        return (NetworkAPI<N,DIR>)TILRef.getCommonSubAPI("NetworkAPI",CommonAPI::getNetworkAPI);
    }

    public static <DIR> boolean isDirToClient(DIR d) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) && api.isDirToClient(d);
    }

    /**
     * This assumes the object is stored as a string.
     */
    public static Object parseGenericObj(ByteBuf buf) {
        return parseGenericObj(buf,null);
    }

    /**
     * This assumes the object is stored as a string and that the class type has been stored when the function is null
     */
    public static Object parseGenericObj(ByteBuf buf, @Nullable Function<String, Object> fromString) {
        if (Objects.nonNull(fromString)) return fromString.apply(readString(buf));
        boolean valid = true;
        Class<?> valType;
        String className = readString(buf);
        try {
            valType = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            TILRef.logError( "Could not find class name {} when parsing a generic object from a packet!",className,ex);
            throw new RuntimeException("Could not find class name when parsing a generic object from a packet!",ex);
        }
        return List.class.isAssignableFrom(valType) ? readGenericList(buf, NetworkHelper::parseGenericObj) :
                GenericUtils.parseGenericType(readString(buf),valType);
    }

    public static <V> List<V> readGenericList(ByteBuf buf, Function<ByteBuf, V> valFunc) {
        List<V> ret = new ArrayList<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.add(valFunc.apply(buf));
        return ret;
    }

    public static <K, V> Map<K, V> readGenericMap(ByteBuf buf, Function<ByteBuf, K> keyFunc,
                                                  Function<ByteBuf, V> valFunc) {
        Map<K, V> ret = new HashMap<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.put(keyFunc.apply(buf),valFunc.apply(buf));
        return ret;
    }

    public static @Nullable RegistryEntryAPI<?> readRegistryEntry(ByteBuf buf) {
        ResourceLocationAPI<?> registryKey = readResourceLocation(buf);
        ResourceLocationAPI<?> entryID = readResourceLocation(buf);
        return RegistryHelper.getEntryIfPresent(registryKey,entryID);
    }

    public static @Nullable ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        NetworkAPI<?,?> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.readResourceLocation(buf) : null;
    }

    public static String readString(ByteBuf buf) {
        int strLength = buf.readInt();
        return (String)buf.readCharSequence(strLength, StandardCharsets.UTF_8);
    }

    public static void registerMessage(Object handler) {
        NetworkAPI<?,?> api = getNetworkAPI();
        if(Objects.nonNull(api)) api.registerMessage(handler);
    }

    public static <SIDE,M extends MessageWrapperAPI<?,?>> void registerMessage(int id, Class<M> clazz, SIDE side) {
        NetworkAPI<?,?> api = getNetworkAPI();
        if(Objects.nonNull(api)) api.registerMessage(id,clazz,side);
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unchecked"})
    public static <DIR,CONTEXT,B extends ByteBuf,M extends MessageWrapperAPI<?,?>,MC extends MessageWrapperAPI<?,CONTEXT>> void registerMessage(
            int id, Class<M> clazz, BiConsumer<M,B> encoder, Function<B,M> decoder,BiConsumer<M,Supplier<?>> handler, Optional<DIR> dir) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        if(Objects.nonNull(api))
            registerMessageStupidly(api,id,(Class<MC>)clazz,(BiConsumer<MC,B>)encoder,(Function<B,MC>)decoder,
                    (BiConsumer<MC,Supplier<?>>)handler,dir);
    }

    /**
     * Stupid syntax sugar casting stuff
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static <DIR,CONTEXT,B extends ByteBuf,M extends MessageWrapperAPI<?,CONTEXT>> void registerMessageStupidly(
            NetworkAPI<?,DIR> api, int id, Class<M> clazz, BiConsumer<M,B> encoder, Function<B,M> decoder,
            BiConsumer<M,Supplier<?>> handler, Optional<DIR> dir) {
        api.registerMessage(id,clazz,encoder,decoder,stupidlyCast(handler),dir);
    }

    /**
     * Stupid syntax sugar casting stuff
     */
    @SuppressWarnings("unchecked")
    private static <CONTEXT,M extends MessageWrapperAPI<?,CONTEXT>,S extends Supplier<CONTEXT>> BiConsumer<M,Supplier<CONTEXT>> stupidlyCast(
            BiConsumer<M,Supplier<?>> handler) {
        return (BiConsumer<M,Supplier<CONTEXT>>)(BiConsumer<M,S>)handler;
    }

    public static @Nullable ResourceLocationAPI<?> registerMessage(ResourceLocationAPI<?> resource, boolean toClient) {
        NetworkAPI<?,?> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.registerMessage(resource,toClient) : null;
    }

    public static <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        NetworkAPI<?,?> api = getNetworkAPI();
        if(Objects.nonNull(api) && Objects.nonNull(message) && Objects.nonNull(player))
            api.sendToPlayer(message,player);
    }

    public static <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        NetworkAPI<?,?> api = getNetworkAPI();
        if(Objects.nonNull(api) && Objects.nonNull(message)) api.sendToServer(message);
    }

    public static <V> void writeGenericList(ByteBuf buf, List<V> list, BiConsumer<ByteBuf, V> valFunc) {
        buf.writeInt(list.size());
        for(V val : list) valFunc.accept(buf,val);
    }

    public static <K, V> void writeGenericMap(ByteBuf buf, Map<K, V> map, BiConsumer<ByteBuf, K> keyFunc,
                                              BiConsumer<ByteBuf, V> valFunc) {
        buf.writeInt(map.size());
        for(Map.Entry<K, V> entry : map.entrySet()) {
            keyFunc.accept(buf,entry.getKey());
            valFunc.accept(buf,entry.getValue());
        }
    }

    /**
     * Writes a generic object as a string
     */
    public static void writeGenericObj(ByteBuf buf, Object val) {
        writeGenericObj(buf, val, null);
    }

    /**
     * Writes a generic object as a string. Can handle lists but not arrays or generic collections
     */
    public static void writeGenericObj(ByteBuf buf, Object val, @Nullable Function<Object, String> toString) {
        if(Objects.nonNull(toString)) writeString(buf,toString.apply(val));
        else {
            writeString(buf,val.getClass().getName());
            if(val instanceof List<?>) writeGenericList(buf,(List<?>)val,(buf1,element) -> writeGenericObj(buf, element));
            else writeString(buf,val.toString());
        }
    }

    public static void writeRegistryEntry(ByteBuf buf, RegistryEntryAPI<?> entry) {
        writeResourceLocation(buf,entry.getRegistryKey());
        writeResourceLocation(buf,entry.getID());
    }

    public static void writeResourceLocation(ByteBuf buf, ResourceLocationAPI<?> resource) {
        writeString(buf,resource.toString());
    }

    public static void writeString(ByteBuf buf, String string) {
        if(Objects.nonNull(string) && !string.isEmpty()) {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(string);
            string = StandardCharsets.UTF_8.decode(buffer).toString();
        }
        buf.writeInt(string.length());
        buf.writeCharSequence(string, StandardCharsets.UTF_8);
    }
}
