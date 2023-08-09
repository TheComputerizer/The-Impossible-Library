package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;
import java.util.function.BiFunction;

public enum TomlPart {

    BLANK_LINE("blank_line", BlankLine.class, (buf, table) -> new BlankLine(buf)),
    COMMENT("comment", Comment.class, Comment::new),
    VARIABLE("variable", Variable.class, Variable::new),
    TABLE("table", Table.class, Table::new);

    private static final HashMap<String, TomlPart> PART_LIST = new HashMap<>();
    private static final HashMap<Class<? extends AbstractType>, TomlPart> CLASS_LIST = new HashMap<>();
    private final String id;
    private final Class<? extends AbstractType> classType;
    private final BiFunction<FriendlyByteBuf, Table, AbstractType> packetReader;
    TomlPart(String id, Class<? extends AbstractType> classType, BiFunction<FriendlyByteBuf, Table, AbstractType> packetReader) {
        this.id = id;
        this.classType = classType;
        this.packetReader = packetReader;
    }

    public String getID() {
        return this.id;
    }

    public Class<? extends AbstractType> getClassType() {
        return this.classType;
    }

    public boolean matchesType(AbstractType type) {
        return this.classType==type.getClass();
    }

    public AbstractType decode(FriendlyByteBuf buf, @Nullable Table parent) {
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
