package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_12_2 extends BaseTag1_12_2<NBTTagList> implements ListTagAPI {

    public ListTag1_12_2(NBTTagList tag) {
        super(tag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTag(BaseTagAPI tag) {
        this.tag.appendTag(((BaseTag1_12_2<NBTBase>)tag).tag);
    }

    /**
     * TODO Use a less stupid implementation of this
     */
    @Override
    public Iterable<BaseTagAPI> iterable() {
        List<BaseTagAPI> tags = new ArrayList<>();
        for(NBTBase based : this.tag) tags.add(new BaseTag1_12_2<>(based));
        return tags;
    }
}
