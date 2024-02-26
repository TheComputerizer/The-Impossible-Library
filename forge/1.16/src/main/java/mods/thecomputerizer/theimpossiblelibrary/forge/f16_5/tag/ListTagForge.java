package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public class ListTagForge extends BaseTagForge<ListNBT> implements ListTagAPI {
    protected ListTagForge(ListNBT tag) {
        super(tag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTag(BaseTagAPI tag) {
        this.tag.add(((BaseTagForge<INBT>)tag).tag);
    }

    /**
     * TODO Use a less stupid implementation of this
     */
    @Override
    public Iterable<BaseTagAPI> iterable() {
        List<BaseTagAPI> tags = new ArrayList<>();
        for(INBT based : this.tag) tags.add(new BaseTagForge<>(based));
        return tags;
    }
}
