package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringNBT;

public class StringTag1_16_5 extends BaseTag1_16_5<StringNBT> implements StringTagAPI<StringNBT> {

    public StringTag1_16_5(StringNBT tag) {
        super(tag);
    }

    @Override
    public String getValue() {
        return this.wrapped.getAsString();
    }
}
