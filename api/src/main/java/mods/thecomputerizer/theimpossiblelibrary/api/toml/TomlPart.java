package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public enum TomlPart {

    BLANK_LINE("blank_line",BlankLine.class,(buf,table) -> new BlankLine(buf)),
    COMMENT("comment",Comment.class,Comment::new),
    VARIABLE("variable",Variable.class,Variable::new),
    TABLE("table",Table.class,Table::new);

    private static final Map<String,TomlPart> PART_LIST = new HashMap<>();
    private static final Map<Class<? extends AbstractType>,TomlPart> CLASS_LIST = new HashMap<>();
    private final String id;
    @Getter private final Class<? extends AbstractType> classType;
    private final BiFunction<ByteBuf,Table,AbstractType> packetReader;
    TomlPart(String id, Class<? extends AbstractType> classType, BiFunction<ByteBuf,Table,AbstractType> packetReader) {
        this.id = id;
        this.classType = classType;
        this.packetReader = packetReader;
    }

    public String getID() {
        return this.id;
    }

    public boolean matchesType(AbstractType type) {
        return this.classType==type.getClass();
    }

    public AbstractType decode(ByteBuf buf, @Nullable Table parent) {
        return this.packetReader.apply(buf,parent);
    }

    public static TomlPart getByID(String id) {
        return PART_LIST.get(id);
    }

    public static TomlPart getByClass(Class<? extends AbstractType> classType) {
        return CLASS_LIST.get(classType);
    }

    public static Set<String> getIDs() {
        return PART_LIST.keySet();
    }

    static {
        for (TomlPart part : values()) {
            if (PART_LIST.containsKey(part.getID()))
                LogManager.getLogger().error("Toml part {} already exists and cannot be added to the part list!",part.getID());
            else {
                PART_LIST.put(part.getID(), part);
                CLASS_LIST.put(part.classType, part);
            }
        }
    }
}
