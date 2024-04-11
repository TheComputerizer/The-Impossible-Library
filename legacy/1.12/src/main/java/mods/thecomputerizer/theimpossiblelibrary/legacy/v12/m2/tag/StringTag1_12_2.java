package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.NBTTagString;

public class StringTag1_12_2 extends BaseTag1_12_2<NBTTagString> implements StringTagAPI {

    protected StringTag1_12_2(NBTTagString tag) {
        super(tag);
    }

    @Override
    public String asString() {
        return this.tag.getString();
    }
}
