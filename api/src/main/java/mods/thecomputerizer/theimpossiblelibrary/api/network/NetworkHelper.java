package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.IterableHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public class NetworkHelper {

    public static <DIR> @Nullable DIR getDirFromName(String name) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getDirFromName(name) : null;
    }

    public static <DIR> @Nullable String getNameFromDir(DIR dir) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getNameFromDir(dir) : null;
    }

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

    public static <DIR> @Nullable DIR getOppositeDir(@Nullable DIR dir) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) && Objects.nonNull(dir) ? api.getOppositeDir(dir) : null;
    }


    public static <N> @Nullable N getNetwork() {
        NetworkAPI<N,?> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.getNetwork() : null;
    }

    @SuppressWarnings("unchecked")
    public static <N,DIR> NetworkAPI<N,DIR> getNetworkAPI() {
        return (NetworkAPI<N,DIR>)TILRef.getCommonSubAPI(CommonAPI::getNetwork);
    }

    public static <DIR> boolean isDirToClient(DIR d) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) && api.isDirToClient(d);
    }

    public static <DIR> boolean isDirLogin(DIR d) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) && api.isDirToClient(d);
    }

    /**
     * This assumes the object is stored as a string.
     */
    public static Object parseObject(ByteBuf buf) {
        return parseObject(buf,null);
    }

    /**
     * This assumes the object is stored as a string and that the class type has been stored when the function is null
     */
    public static Object parseObject(ByteBuf buf, @Nullable Function<String, Object> fromString) {
        if(Objects.nonNull(fromString)) return fromString.apply(readString(buf));
        Class<?> valType;
        String className = readString(buf);
        try {
            valType = Class.forName(className);
        } catch(ClassNotFoundException ex) {
            TILRef.logError( "Could not find class name {} when parsing a generic object from a packet!",className,ex);
            throw new RuntimeException("Could not find class name when parsing a generic object from a packet!",ex);
        }
        return Collection.class.isAssignableFrom(valType) ? readCollection(buf,() -> parseObject(buf)) :
                GenericUtils.parseGenericType(readString(buf),valType);
    }

    public static <V> Collection<V> readCollection(ByteBuf buf, Supplier<V> valFunc) {
        String type = readString(buf).toLowerCase();
        switch(type) {
            case "list": return readList(buf,valFunc);
            case "set": return readSet(buf,valFunc);
            default: {
                TILRef.logError("Tried to decode unsupported collection type: {}",type);
                return Collections.emptyList();
            }
        }
    }

    public static <DIR> DIR readDir(ByteBuf buf) {
        return getDirFromName(readString(buf));
    }

    public static <V> List<V> readList(ByteBuf buf, Supplier<V> valFunc) {
        List<V> ret = new ArrayList<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++) ret.add(valFunc.get());
        return ret;
    }

    public static <K,V> Map<K,V> readMapEntries(ByteBuf buf, Supplier<Entry<K,V>> entryFunc) {
        Map<K,V> ret = new HashMap<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++) {
            Entry<K,V> entry = entryFunc.get();
            ret.put(entry.getKey(),entry.getValue());
        }
        return ret;
    }

    public static <K,V> Map<K,V> readMap(ByteBuf buf, Supplier<K> keyFunc, Supplier<V> valFunc) {
        return readMapEntries(buf,() -> IterableHelper.getMapEntry(keyFunc.get(),valFunc.get()));
    }

    public static @Nullable ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        NetworkAPI<?,?> api = getNetworkAPI();
        return Objects.nonNull(api) ? api.readResourceLocation(buf) : null;
    }

    public static <V> Set<V> readSet(ByteBuf buf, Supplier<V> valFunc) {
        Set<V> ret = new HashSet<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++) ret.add(valFunc.get());
        return ret;
    }

    public static String readString(ByteBuf buf) {
        int strLength = buf.readInt();
        return strLength==0 ? "" : (String)buf.readCharSequence(strLength,StandardCharsets.UTF_8);
    }
    
    public static CompoundTagAPI<?> readTag(ByteBuf buf) {
        return getNetworkAPI().readTag(buf);
    }

    public static <DIR> void registerMessage(MessageDirectionInfo<DIR> info, int id) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        if(Objects.nonNull(api)) api.registerMessage(info,id);
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

    public static <CTX,DIR> @Nullable MessageWrapperAPI<?,CTX> wrapMessage(DIR dir, MessageAPI<CTX> message) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) && Objects.nonNull(message) ? api.wrapMessage(dir,message) : null;
    }

    @SuppressWarnings("unchecked")
    public static <CTX,DIR> @Nullable MessageWrapperAPI<?,CTX> wrapMessages(DIR dir, MessageAPI<CTX> ... messages) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        if(Objects.nonNull(api) && Objects.nonNull(messages) && messages.length>0)
            return messages.length==1 ? wrapMessage(dir,messages[0]) : api.wrapMessages(dir,messages);
        return null;
    }

    public static <CTX,DIR> @Nullable MessageWrapperAPI<?,CTX> wrapMessages(DIR dir, Collection<MessageAPI<CTX>> messages) {
        NetworkAPI<?,DIR> api = getNetworkAPI();
        return Objects.nonNull(api) && Objects.nonNull(messages) && !messages.isEmpty() ?
                api.wrapMessages(dir,messages) : null;
    }

    public static <V> void writeCollection(ByteBuf buf, Collection<V> collection, Consumer<V> valFunc) {
        if(collection instanceof List<?>) {
            writeString(buf,"list");
            writeList(buf,(List<V>)collection,valFunc);
        }
        else if(collection instanceof Set<?>) {
            writeString(buf,"set");
            writeSet(buf,(Set<V>)collection,valFunc);
        } else {
            writeString(buf,"list"); //Assume a list for unknown types
            buf.writeInt(collection.size());
            collection.forEach(valFunc);
        }
    }

    public static <DIR> void writeDir(ByteBuf buf, DIR dir) {
        String name = getNameFromDir(dir);
        writeString(buf,Objects.nonNull(name) ? name : "CLIENT");
    }

    public static <V> void writeList(ByteBuf buf, List<V> list, Consumer<V> valFunc) {
        buf.writeInt(list.size());
        list.forEach(valFunc);
    }

    public static <K,V> void writeMap(ByteBuf buf, Map<K,V> map, Consumer<K> keyFunc, Consumer<V> valFunc) {
        writeSet(buf,map.entrySet(),entry -> {
            keyFunc.accept(entry.getKey());
            valFunc.accept(entry.getValue());
        });
    }

    /**
     * Writes a generic object as a string
     */
    public static void writeObject(ByteBuf buf, Object val) {
        writeObject(buf,val,null);
    }

    /**
     * Writes a generic object as a string. Can handle lists but not arrays or generic collections
     */
    public static void writeObject(ByteBuf buf, Object val, @Nullable Function<Object,String> toString) {
        if(Objects.nonNull(toString)) writeString(buf,toString.apply(val));
        else {
            writeString(buf,val.getClass().getName());
            if(val instanceof Collection<?>) writeCollection(buf,(Collection<?>)val,element -> writeObject(buf,element));
            else writeString(buf,val.toString());
        }
    }

    public static void writeResourceLocation(ByteBuf buf, ResourceLocationAPI<?> resource) {
        writeString(buf,resource.toString());
    }

    public static <V> void writeSet(ByteBuf buf, Set<V> set, Consumer<V> valFunc) {
        buf.writeInt(set.size());
        set.forEach(valFunc);
    }

    public static void writeString(ByteBuf buf, String string) {
        if(Objects.isNull(string) || string.isEmpty()) {
            TILRef.logError("Tried to write a null or empty string to a packet!");
            buf.writeInt(0);
        }
        if(StringUtils.isNotBlank(string)) {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(string);
            string = StandardCharsets.UTF_8.decode(buffer).toString();
        }
        buf.writeInt(string.length());
        buf.writeCharSequence(string,StandardCharsets.UTF_8);
    }
    
    public static void writeTag(ByteBuf buf, CompoundTagAPI<?> tag) {
        getNetworkAPI().writeTag(buf,tag);
    }
}
