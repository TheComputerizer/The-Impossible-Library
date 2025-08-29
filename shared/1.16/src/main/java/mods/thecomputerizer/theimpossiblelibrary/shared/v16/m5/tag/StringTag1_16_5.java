package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringTag;

public class StringTag1_16_5 extends StringTagAPI<StringTag> {

    public StringTag1_16_5(Object tag) {
        super(tag);
    }

    @Override public String getValue() {
        return getIfNotNull(StringTag::getAsString);
    }
}