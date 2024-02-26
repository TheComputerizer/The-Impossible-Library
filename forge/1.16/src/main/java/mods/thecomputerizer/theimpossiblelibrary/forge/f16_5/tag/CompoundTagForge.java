package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class CompoundTagForge extends BaseTagForge<CompoundNBT> implements CompoundTagAPI {
    protected CompoundTagForge(CompoundNBT tag) {
        super(tag);
    }

    @Override
    public boolean contains(String key) {
        return this.tag.contains(key);
    }

    @Override
    public CompoundTagAPI getCompoundTag(String key) {
        return new CompoundTagForge(this.tag.getCompound(key));
    }

    @Override
    public ListTagAPI getListTag(String key) {
        return getTag(key).asListTag();
    }

    @Override
    public PrimitiveTagAPI getPrimitiveTag(String key) {
        return getTag(key).asPrimitiveTag();
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public BaseTagAPI getTag(String key) {
        return new BaseTagForge<>(this.tag.get(key));
    }

    @Override
    public void putInt(String key, int value) {
        this.tag.putInt(key,value);
    }

    @Override
    public void putString(String key, String value) {
        this.tag.putString(key,value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putTag(String key, BaseTagAPI tag) {
        this.tag.put(key,((BaseTagForge<INBT>)tag).tag);
    }
}
