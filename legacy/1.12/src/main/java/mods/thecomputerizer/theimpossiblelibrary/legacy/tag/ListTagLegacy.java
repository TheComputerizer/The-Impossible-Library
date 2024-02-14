package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

public class ListTagLegacy extends BaseTagLegacy<NBTTagList> implements ListTagAPI {
    protected ListTagLegacy(NBTTagList tag) {
        super(tag);
    }

    @Override
    public void addTag(BaseTagAPI tag) {

    }

    @Override
    public Iterable<BaseTagAPI> iterable() {
        return null;
    }
}
