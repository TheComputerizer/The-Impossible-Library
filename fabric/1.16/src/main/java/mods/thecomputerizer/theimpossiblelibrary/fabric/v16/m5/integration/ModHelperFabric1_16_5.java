package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.fabricmc.loader.api.ModContainer;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16_5;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static net.fabricmc.loader.impl.FabricLoaderImpl.INSTANCE;

public class ModHelperFabric1_16_5 extends ModHelperAPI {

    public ModHelperFabric1_16_5(Side side) {
        super(V16_5,FABRIC,side);
    }

    @Override
    protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        return Collections.unmodifiableMap(map);
    }
    
    @Override
    public String getModName(String modid) {
        String name = super.getModName(modid);
        if(name.equals(modid) && isModLoaded(modid)) {
            ModContainer container = INSTANCE.getModContainer(modid).orElse(null);
            if(Objects.nonNull(container)) name = container.getMetadata().getName();
        }
        return name;
    }

    @Override
    public boolean isModLoaded(String modid) {
        return Objects.nonNull(INSTANCE) && INSTANCE.isModLoaded(modid);
    }
}
