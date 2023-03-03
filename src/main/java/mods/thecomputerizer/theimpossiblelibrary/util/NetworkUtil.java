package mods.thecomputerizer.theimpossiblelibrary.util;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
public class NetworkUtil {

    public static void writeString(PacketBuffer buf, String string) {
        buf.writeInt(string.length());
        buf.writeCharSequence(string, StandardCharsets.UTF_8);
    }

    public static String readString(PacketBuffer buf) {
        int strLength = buf.readInt();
        return (String)buf.readCharSequence(strLength, StandardCharsets.UTF_8);
    }

    public static void writeEntityType(PacketBuffer buf, EntityType<?> type) {
        ResourceLocation resource = Objects.nonNull(type) ? ForgeRegistries.ENTITIES.getKey(type) : null;
        buf.writeResourceLocation(Objects.nonNull(resource) ? resource : new ResourceLocation(Constants.MODID,"missing"));
    }

    public static Optional<EntityType<?>> readEntityType(PacketBuffer buf) {
        ResourceLocation location = buf.readResourceLocation();
        return location.getPath().matches("missing") ? Optional.empty() :
                Optional.ofNullable(ForgeRegistries.ENTITIES.getValue(location));
    }

    public static <V> void writeGenericList(PacketBuffer buf, List<V> list, BiConsumer<PacketBuffer, V> valFunc) {
        buf.writeInt(list.size());
        for(V val : list) valFunc.accept(buf,val);
    }

    public static <V> List<V> readGenericList(PacketBuffer buf, Function<PacketBuffer, V> valFunc) {
        List<V> ret = new ArrayList<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.add(valFunc.apply(buf));
        return ret;
    }

    public static <K, V> void writeGenericMap(PacketBuffer buf, Map<K, V> map, BiConsumer<PacketBuffer, K> keyFunc,
                                              BiConsumer<PacketBuffer, V> valFunc) {
        buf.writeInt(map.size());
        for(Map.Entry<K, V> entry : map.entrySet()) {
            keyFunc.accept(buf,entry.getKey());
            valFunc.accept(buf,entry.getValue());
        }
    }

    public static <K, V> Map<K, V> readGenericMap(PacketBuffer buf, Function<PacketBuffer, K> keyFunc,
                                                  Function<PacketBuffer, V> valFunc) {
        Map<K, V> ret = new HashMap<>();
        int size = buf.readInt();
        for(int i=0;i<size;i++)
            ret.put(keyFunc.apply(buf),valFunc.apply(buf));
        return ret;
    }

    /**
     * Writes a generic object as a string
     */
    public static void writeGenericObj(PacketBuffer buf, Object val) {
        writeGenericObj(buf, val, null);
    }

    /**
     * Writes a generic object as a string. Can handle lists but not arrays or generic collections
     */
    public static void writeGenericObj(PacketBuffer buf, Object val, @Nullable Function<Object, String> toString) {
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
    public static Object parseGenericObj(PacketBuffer buf) {
        return parseGenericObj(buf, null);
    }

    /**
     * This assumes the object is stored as a string and that the class type has been stored when the function is null
     */
    public static Object parseGenericObj(PacketBuffer buf, @Nullable Function<String, Object> fromString) {
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
