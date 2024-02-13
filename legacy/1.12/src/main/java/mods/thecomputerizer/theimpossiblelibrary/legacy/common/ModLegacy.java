package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class ModLegacy implements ModAPI {
    @Override
    public String getModName(String modid) {
        for(ModContainer mod : Loader.instance().getModList())
            if(mod.getModId().equals(modid)) return mod.getName();
        return modid;
    }

    @Override
    public boolean isModLoaded(String modid) {
        return Loader.isModLoaded(modid);
    }
}
