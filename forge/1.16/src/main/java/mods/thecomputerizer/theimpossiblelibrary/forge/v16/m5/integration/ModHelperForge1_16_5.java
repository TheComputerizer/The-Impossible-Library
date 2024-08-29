package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16_5;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class ModHelperForge1_16_5 extends ModHelperAPI {

    public ModHelperForge1_16_5(Side side) {
        super(V16_5,FORGE,side);
    }

    @Override
    protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        addMod(map,new BetterWeatherForge1_16_5());
        addMod(map,new ChampionsForge1_16_5());
        addMod(map,new DynamicSurroundingsForge1_16_5());
        addMod(map,new EnhancedCelestialsForge1_16_5());
        addMod(map,new GameStagesForge1_16_5());
        addMod(map,new SereneSeasonsForge1_16_5());
        return Collections.unmodifiableMap(map);
    }
    
    @Override
    public String getModName(String modid) {
        String name = super.getModName(modid);
        ModList mods = ModList.get();
        if(Objects.nonNull(mods) && name.equals(modid) && mods.isLoaded(modid)) {
            for(ModInfo info : mods.getMods()) {
                if(modid.equals(info.getModId())) {
                    name = info.getDisplayName();
                    break;
                }
            }
        }
        return name;
    }

    @Override
    public boolean isModLoaded(String modid) {
        ModList mods = ModList.get();
        return Objects.nonNull(mods) && mods.isLoaded(modid);
    }
}
