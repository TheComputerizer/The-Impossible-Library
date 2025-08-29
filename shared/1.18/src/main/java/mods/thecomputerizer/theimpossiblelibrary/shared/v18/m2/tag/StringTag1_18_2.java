package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringTag;

public class StringTag1_18_2 extends StringTagAPI<StringTag> {

    public StringTag1_18_2(Object tag) {
        super(tag);
    }
    
    @Override public String getValue() {
        return getIfNotNull(StringTag::getAsString);
    }
}