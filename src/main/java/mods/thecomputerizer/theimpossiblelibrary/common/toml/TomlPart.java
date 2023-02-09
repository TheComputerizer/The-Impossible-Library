package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Set;

public enum TomlPart {

    BLANK_LINE("blank_line", BlankLine.class),
    COMMENT("comment", Comment.class),
    VARIABLE("variable", Variable.class),
    TABLE("table", Table.class);

    private static final HashMap<String, TomlPart> PART_LIST = new HashMap<>();
    private final String id;
    private final Class<? extends AbstractType> classType;
    TomlPart(String id, Class<? extends AbstractType> classType) {
        this.id = id;
        this.classType = classType;
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

    public static TomlPart getByID(String id) {
        return PART_LIST.get(id);
    }

    public static Set<String> getIDs() {
        return PART_LIST.keySet();
    }

    static {
        for (TomlPart part : values()) {
            if (PART_LIST.containsKey(part.getID()))
                LogManager.getLogger().error("Toml part {} already exists and cannot be added to the part list!",part.getID());
            else PART_LIST.put(part.getID(), part);
        }
    }
}
