package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.NBTTagString;

public class StringTagLegacy extends BaseTagLegacy<NBTTagString> implements StringTagAPI {

    protected StringTagLegacy(NBTTagString tag) {
        super(tag);
    }

    @Override
    public String asString() {
        return this.tag.getString();
    }
}
