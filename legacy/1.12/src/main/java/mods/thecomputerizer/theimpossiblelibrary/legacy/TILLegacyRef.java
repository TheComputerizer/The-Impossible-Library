package mods.thecomputerizer.theimpossiblelibrary.legacy;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.apache.logging.log4j.LogManager;

public class TILLegacyRef extends ReferenceAPI<ResourceLocation> {

    public TILLegacyRef() {
        super(() -> FMLLaunchHandler.side().isClient());
        load();
    }

    @Override
    public ResourceLocation getResource(String path) {
        return new ResourceLocation(MODID,path);
    }

    @Override
    protected void initLogger() {
        LOGGER = LogManager.getLogger(NAME);
    }
}
