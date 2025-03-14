package mods.thecomputerizer.theimpossiblelibrary.shared.v19.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;

public class Resource1_19 implements ResourceAPI {

    @Override public ResourceLocationAPI<?> getLocation(String path) {
        return WrapperHelper.wrapResourceLocation(new ResourceLocation(path));
    }

    @Override public ResourceLocationAPI<?> getLocation(String modid, String path) {
        return WrapperHelper.wrapResourceLocation(new ResourceLocation(modid,path));
    }

    @Override public InputStream stream(ResourceLocationAPI<?> location) {
        ResourceLocation res = location.unwrap();
        try {
            return Minecraft.getInstance().getResourceManager().getResourceOrThrow(res).open();
        } catch(Exception ex) {
            TILRef.logError("Failed to get resource stream for {}!",res,ex);
            return null;
        }
    }
}