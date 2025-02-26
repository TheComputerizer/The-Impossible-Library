package mods.thecomputerizer.theimpossiblelibrary.forge.v19.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public abstract class ModHelperForge1_19 extends ModHelperAPI {

    protected ModHelperForge1_19(boolean two, Side side) {
        super(two ? V19_2 : V19_4,FORGE,side);
    }
    
    @Override public String getModName(String modid) {
        String name = super.getModName(modid);
        ModList mods = ModList.get();
        if(Objects.nonNull(mods) && name.equals(modid) && mods.isLoaded(modid)) {
            for(IModInfo info : mods.getMods()) {
                if(modid.equals(info.getModId())) {
                    name = info.getDisplayName();
                    break;
                }
            }
        }
        return name;
    }

    @Override public boolean isModLoaded(String modid) {
        ModList mods = ModList.get();
        return Objects.nonNull(mods) && mods.isLoaded(modid);
    }
}