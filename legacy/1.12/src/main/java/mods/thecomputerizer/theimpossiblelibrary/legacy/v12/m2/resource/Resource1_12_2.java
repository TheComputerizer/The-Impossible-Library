package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

public class Resource1_12_2 implements ResourceAPI {

    @Override public ResourceLocationAPI<?> getLocation(String path) {
        return WrapperHelper.wrapResourceLocation(new ResourceLocation(path));
    }

    @Override public ResourceLocationAPI<?> getLocation(String modid, String path) {
        return WrapperHelper.wrapResourceLocation(new ResourceLocation(modid,path));
    }

    @Override public InputStream stream(ResourceLocationAPI<?> location) {
        ResourceLocation res = location.unwrap();
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream();
        } catch(Exception ex) {
            TILRef.logError("Failed to get resource stream for {}!",res,ex);
            return null;
        }
    }
}