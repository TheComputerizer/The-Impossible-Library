package mods.thecomputerizer.theimpossiblelibrary.util;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
public class NetworkUtil {

    public static void writeString(FriendlyByteBuf buf, String string) {
        if(Objects.nonNull(string) && !string.isEmpty()) {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(string);
            string = StandardCharsets.UTF_8.decode(buffer).toString();
        }
        buf.writeInt(string.length());
        buf.writeCharSequence(string, StandardCharsets.UTF_8);
    }

    public static String readString(FriendlyByteBuf buf) {
        int strLength = buf.readInt();
        return (String)buf.readCharSequence(strLength, StandardCharsets.UTF_8);
    }

    public static void writeEntityType(FriendlyByteBuf buf, EntityType<?> type) {
        ResourceLocation resource = Objects.nonNull(type) ? ForgeRegistries.ENTITY_TYPES.getKey(type) : null;
        buf.writeResourceLocation(Objects.nonNull(resource) ? resource : Constants.res("missing"));
    }

    public static Optional<EntityType<?>> readEntityType(FriendlyByteBuf buf) {
        ResourceLocation location = buf.readResourceLocation();
        return location.getPath().matches("missing") ? Optional.empty() :
                Optional.ofNullable(ForgeRegistries.ENTITY_TYPES.getValue(location));
    }

    public static <V> void writeGenericList(FriendlyByteBuf buf, List<V> list, BiConsumer<FriendlyByteBuf, V> valFunc) {
        buf.writeInt(list.size());
        for(V val : list) valFunc.accept(buf,val);
    }

    public static <V> List<V> readGenericList(FriendlyByteBuf buf, Function<FriendlyByteBuf, V> valFunc) {
        List<V> ret = new ArrayList<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.add(valFunc.apply(buf));
        return ret;
    }

    public static <K, V> void writeGenericMap(FriendlyByteBuf buf, Map<K, V> map, BiConsumer<FriendlyByteBuf, K> keyFunc,
                                              BiConsumer<FriendlyByteBuf, V> valFunc) {
        buf.writeInt(map.size());
        for(Map.Entry<K, V> entry : map.entrySet()) {
            keyFunc.accept(buf,entry.getKey());
            valFunc.accept(buf,entry.getValue());
        }
    }

    public static <K, V> Map<K, V> readGenericMap(FriendlyByteBuf buf, Function<FriendlyByteBuf, K> keyFunc,
                                                  Function<FriendlyByteBuf, V> valFunc) {
        Map<K, V> ret = new HashMap<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.put(keyFunc.apply(buf),valFunc.apply(buf));
        return ret;
    }

    /**
     * Writes a generic object as a string
     */
    public static void writeGenericObj(FriendlyByteBuf buf, Object val) {
        writeGenericObj(buf, val, null);
    }

    /**
     * Writes a generic object as a string. Can handle lists but not arrays or generic collections
     */
    public static void writeGenericObj(FriendlyByteBuf buf, Object val, @Nullable Function<Object, String> toString) {
        if(Objects.nonNull(toString)) writeString(buf,toString.apply(val));
        else {
            writeString(buf,val.getClass().getName());
            if(val instanceof List<?>) writeGenericList(buf,(List<?>)val,(buf1,element) -> writeGenericObj(buf, element));
            else writeString(buf,val.toString());
        }
    }

    /**
     * This assumes the object is stored as a string.
     */
    public static Object parseGenericObj(FriendlyByteBuf buf) {
        return parseGenericObj(buf, null);
    }

    /**
     * This assumes the object is stored as a string and that the class type has been stored when the function is null
     */
    public static Object parseGenericObj(FriendlyByteBuf buf, @Nullable Function<String, Object> fromString) {
        if (Objects.nonNull(fromString)) return fromString.apply(readString(buf));
        boolean valid = true;
        Class<?> valType;
        String className = readString(buf);
        try {
            valType = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Constants.LOGGER.error("Could not find class name {} when parsing a generic object from a packet!",className,ex);
            throw new RuntimeException("Could not find class name when parsing a generic object from a packet!",ex);
        }
        return List.class.isAssignableFrom(valType) ? readGenericList(buf,NetworkUtil::parseGenericObj) :
                GenericUtils.parseGenericType(readString(buf),valType);
    }
}
