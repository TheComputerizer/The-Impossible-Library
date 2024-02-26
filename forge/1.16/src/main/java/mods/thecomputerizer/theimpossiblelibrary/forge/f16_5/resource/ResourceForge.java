package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

public class ResourceForge implements ResourceAPI {

    @Override
    public ResourceLocationForge getLocation(String path) {
        return new ResourceLocationForge(new ResourceLocation(path));
    }

    @Override
    public ResourceLocationForge getLocation(String modid, String path) {
        return new ResourceLocationForge(new ResourceLocation(modid,path));
    }

    @Override
    public InputStream stream(ResourceLocationAPI<?> location) {
        ResourceLocation res = (ResourceLocation)location.get();
        try {
            return Minecraft.getInstance().getResourceManager().getResource(res).getInputStream();
        } catch(Exception ex) {
            TILRef.logError("Failed to get resource stream for {}!",res,ex);
            return null;
        }
    }
}
