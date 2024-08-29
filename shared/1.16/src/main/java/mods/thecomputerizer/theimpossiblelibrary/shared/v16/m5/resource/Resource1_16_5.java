package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

public class Resource1_16_5 implements ResourceAPI {

    @Override
    public ResourceLocation1_16_5 getLocation(String path) {
        return new ResourceLocation1_16_5(new ResourceLocation(path));
    }

    @Override
    public ResourceLocation1_16_5 getLocation(String modid, String path) {
        return new ResourceLocation1_16_5(new ResourceLocation(modid,path));
    }

    @Override
    public InputStream stream(ResourceLocationAPI<?> location) {
        ResourceLocation res = (ResourceLocation)location.getInstance();
        try {
            return Minecraft.getInstance().getResourceManager().getResource(res).getInputStream();
        } catch(Exception ex) {
            TILRef.logError("Failed to get resource stream for {}!",res,ex);
            return null;
        }
    }
}
