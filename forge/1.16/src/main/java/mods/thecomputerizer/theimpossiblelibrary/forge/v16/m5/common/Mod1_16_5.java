package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

import java.util.Objects;

public class Mod1_16_5 implements ModAPI {
    @Override
    public String getModName(String modid) {
        ModFileInfo mod = ModList.get().getModFileById(modid);
        return Objects.isNull(mod) ? modid : mod.getFile().getModInfos().get(0).getDisplayName();
    }

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
}
