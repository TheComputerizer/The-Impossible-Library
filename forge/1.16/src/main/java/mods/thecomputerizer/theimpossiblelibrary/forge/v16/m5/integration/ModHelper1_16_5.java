package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

import java.util.Collections;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class ModHelper1_16_5 extends ModHelperAPI {

    public ModHelper1_16_5(Side side) {
        super(V16,FORGE,side);
    }

    @Override
    protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        addMod(map,new BetterWeather1_16_5());
        addMod(map,new Champions1_16_5());
        addMod(map,new DynamicSurroundings1_16_5());
        addMod(map,new EnhancedCelestials1_16_5());
        addMod(map,new GameStages1_16_5());
        addMod(map,new SereneSeasons1_16_5());
        return Collections.unmodifiableMap(map);
    }
    
    @Override
    public String getModName(String modid) {
        String name = super.getModName(modid);
        ModList list = ModList.get();
        if(name.equals(modid) && list.isLoaded(modid)) {
            for(ModInfo info : list.getMods()) {
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
        return ModList.get().isLoaded(modid);
    }
}
