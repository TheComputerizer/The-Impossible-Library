package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class ListTagLegacy extends BaseTagLegacy<NBTTagList> implements ListTagAPI {
    protected ListTagLegacy(NBTTagList tag) {
        super(tag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTag(BaseTagAPI tag) {
        this.tag.appendTag(((BaseTagLegacy<NBTBase>)tag).tag);
    }

    /**
     * TODO Use a less stupid implementation of this
     */
    @Override
    public Iterable<BaseTagAPI> iterable() {
        List<BaseTagAPI> tags = new ArrayList<>();
        for(NBTBase based : this.tag) tags.add(new BaseTagLegacy<>(based));
        return tags;
    }
}
