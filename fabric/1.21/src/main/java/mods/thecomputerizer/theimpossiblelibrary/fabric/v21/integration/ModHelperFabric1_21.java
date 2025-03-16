package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.fabricmc.loader.api.ModContainer;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static net.fabricmc.loader.impl.FabricLoaderImpl.INSTANCE;

public abstract class ModHelperFabric1_21 extends ModHelperAPI {

    public ModHelperFabric1_21(GameVersion version, Side side) {
        super(version,FABRIC,side);
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
