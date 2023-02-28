package mods.thecomputerizer.theimpossiblelibrary.util;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
public class NetworkUtil {

    public static void writeString(ByteBuf buf, String string) {
        buf.writeInt(string.length());
        buf.writeCharSequence(string, StandardCharsets.UTF_8);
    }

    public static String readString(ByteBuf buf) {
        int strLength = buf.readInt();
        return (String)buf.readCharSequence(strLength, StandardCharsets.UTF_8);
    }

    public static void writeResourceLocation(ByteBuf buf, ResourceLocation source) {
        String asString = source.toString();
        buf.writeInt(asString.length());
        buf.writeCharSequence(asString, StandardCharsets.UTF_8);
    }

    public static ResourceLocation readResourceLocation(ByteBuf buf) {
        int strLength = buf.readInt();
        return new ResourceLocation((String)buf.readCharSequence(strLength, StandardCharsets.UTF_8));
    }

    public static void writeEntityType(ByteBuf buf, EntityEntry type) {
        ResourceLocation resource = Objects.nonNull(type) ? ForgeRegistries.ENTITIES.getKey(type) : null;
        writeResourceLocation(buf,Objects.nonNull(resource) ? resource : new ResourceLocation(Constants.MODID,"missing"));
    }

    public static Optional<EntityEntry> readEntityType(ByteBuf buf) {
        ResourceLocation location = readResourceLocation(buf);
        return location.getPath().matches("missing") ? Optional.empty() :
                Optional.ofNullable(ForgeRegistries.ENTITIES.getValue(location));
    }

    public static <V> void writeGenericList(ByteBuf buf, List<V> list, BiConsumer<ByteBuf, V> valFunc) {
        buf.writeInt(list.size());
        for(V val : list) valFunc.accept(buf,val);
    }

    public static <V> List<V> readGenericList(ByteBuf buf, Function<ByteBuf, V> valFunc) {
        List<V> ret = new ArrayList<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.add(valFunc.apply(buf));
        return ret;
    }

    public static <K, V> void writeGenericMap(ByteBuf buf, Map<K, V> map, BiConsumer<ByteBuf, K> keyFunc,
                                              BiConsumer<ByteBuf, V> valFunc) {
        buf.writeInt(map.size());
        for(Map.Entry<K, V> entry : map.entrySet()) {
            keyFunc.accept(buf,entry.getKey());
            valFunc.accept(buf,entry.getValue());
        }
    }

    public static <K, V> Map<K, V> readGenericMap(ByteBuf buf, Function<ByteBuf, K> keyFunc,
                                                  Function<ByteBuf, V> valFunc) {
        Map<K, V> ret = new HashMap<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.put(keyFunc.apply(buf),valFunc.apply(buf));
        return ret;
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
            else {
                writeString(buf,val.toString());
            }
        }
    }

    /**
     * This assumes the object is stored as a string.
     */
    public static Object parseGenericObj(ByteBuf buf) {
        return parseGenericObj(buf, null);
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
            ex.printStackTrace();
            throw new RuntimeException("Could not find class name {} when parsing a generic object from a packet!");
        }
        return List.class.isAssignableFrom(valType) ? readGenericList(buf,NetworkUtil::parseGenericObj) :
                GenericUtils.parseGenericType(readString(buf),valType);
    }
}
