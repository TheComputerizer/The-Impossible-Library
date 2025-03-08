package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.fabricmc.loader.api.ModContainer;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static net.fabricmc.loader.impl.FabricLoaderImpl.INSTANCE;

public abstract class ModHelperFabric1_20 extends ModHelperAPI {

    public ModHelperFabric1_20(boolean two, Side side) {
        super(two ? V19_2 : V19_4,FABRIC,side);
    }
    
    @Override public String getModName(String modid) {
        String name = super.getModName(modid);
        if(name.equals(modid) && isModLoaded(modid)) {
            ModContainer container = INSTANCE.getModContainer(modid).orElse(null);
            if(Objects.nonNull(container)) name = container.getMetadata().getName();
        }
        return name;
    }

    @Override public boolean isModLoaded(String modid) {
        return Objects.nonNull(INSTANCE) && INSTANCE.isModLoaded(modid);
    }
}
