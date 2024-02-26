package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringNBT;

public class StringTagForge extends BaseTagForge<StringNBT> implements StringTagAPI {

    protected StringTagForge(StringNBT tag) {
        super(tag);
    }

    @Override
    public String asString() {
        return this.tag.getAsString();
    }
}
