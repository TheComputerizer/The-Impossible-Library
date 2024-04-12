package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_16_5 extends BaseTag1_16_5<ListNBT> implements ListTagAPI {

    public ListTag1_16_5(ListNBT tag) {
        super(tag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTag(BaseTagAPI tag) {
        this.tag.add(((BaseTag1_16_5<INBT>)tag).tag);
    }

    /**
     * TODO Use a less stupid implementation of this
     */
    @Override
    public Iterable<BaseTagAPI> iterable() {
        List<BaseTagAPI> tags = new ArrayList<>();
        for(INBT based : this.tag) tags.add(new BaseTag1_16_5<>(based));
        return tags;
    }
}
