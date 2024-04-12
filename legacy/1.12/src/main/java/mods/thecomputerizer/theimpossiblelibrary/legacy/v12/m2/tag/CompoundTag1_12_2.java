package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class CompoundTag1_12_2 extends BaseTag1_12_2<NBTTagCompound> implements CompoundTagAPI {

    public CompoundTag1_12_2(NBTTagCompound tag) {
        super(tag);
    }

    @Override
    public boolean contains(String key) {
        return this.tag.hasKey(key);
    }

    @Override
    public CompoundTagAPI getCompoundTag(String key) {
        return new CompoundTag1_12_2(this.tag.getCompoundTag(key));
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
        return new BaseTag1_12_2<>(this.tag.getTag(key));
    }

    @Override
    public void putInt(String key, int value) {
        this.tag.setInteger(key,value);
    }

    @Override
    public void putString(String key, String value) {
        this.tag.setString(key,value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putTag(String key, BaseTagAPI tag) {
        this.tag.setTag(key,((BaseTag1_12_2<NBTBase>)tag).tag);
    }
}
