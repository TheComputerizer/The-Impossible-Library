package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class CompoundTagLegacy extends BaseTagLegacy<NBTTagCompound> implements CompoundTagAPI {
    protected CompoundTagLegacy(NBTTagCompound tag) {
        super(tag);
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public CompoundTagAPI getCompoundTag(String key) {
        return null;
    }

    @Override
    public ListTagAPI getListTag(String key) {
        return null;
    }

    @Override
    public PrimitiveTagAPI getPrimitiveTag(String key) {
        return null;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public BaseTagAPI getTag(String key) {
        return null;
    }

    @Override
    public void putInt(String key, int value) {

    }

    @Override
    public void putString(String key, String value) {

    }

    @Override
    public void putTag(String key, BaseTagAPI tag) {

    }
}
