package mods.thecomputerizer.theimpossiblelibrary.legacy.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.MinecraftLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

public class ResourceLegacy implements ResourceAPI {

    @Override
    public ResourceLocationLegacy getLocation(String path) {
        return new ResourceLocationLegacy(new ResourceLocation(path));
    }

    @Override
    public ResourceLocationLegacy getLocation(String modid, String path) {
        return new ResourceLocationLegacy(new ResourceLocation(modid,path));
    }

    @Override
    public InputStream stream(ResourceLocationAPI<?> location) {
        ResourceLocation res = (ResourceLocation)location.get();
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream();
        } catch(Exception ex) {
            TILRef.logError("Failed to get resource stream for {}!",res,ex);
            return null;
        }
    }
}
