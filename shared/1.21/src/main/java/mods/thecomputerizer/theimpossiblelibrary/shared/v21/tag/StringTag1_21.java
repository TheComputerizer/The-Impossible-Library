package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringTag;

public class StringTag1_21 extends StringTagAPI<StringTag> implements TagWrapper {

    public StringTag1_21(Object tag) {
        super(tag);
    }
    
    @Override public String getValue() {
        return getIfNotNull(StringTag::getAsString);
    }
}