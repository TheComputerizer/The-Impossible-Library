package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.InfernalMobsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.integration.ModHelperNeoForge1_20;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ModHelperNeoForge1_20_6 extends ModHelperNeoForge1_20 {

    public ModHelperNeoForge1_20_6(Side side) {
        super(true,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(InfernalMobsAPI.MODID)) addMod(map,new InfernalMobs1_20_6());
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map,new SereneSeasonsForge1_20_6());
        return Collections.unmodifiableMap(map);
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