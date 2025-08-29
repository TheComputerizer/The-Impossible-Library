package mods.thecomputerizer.theimpossiblelibrary.shared.v19.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringTag;

public class StringTag1_19 extends StringTagAPI<StringTag> {

    public StringTag1_19(Object tag) {
        super(tag);
    }
    
    @Override public String getValue() {
        return getIfNotNull(StringTag::getAsString);
    }
}