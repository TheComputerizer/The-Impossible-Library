package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocation1_18_2 extends ResourceLocationAPI<ResourceLocation> {
    
    public ResourceLocation1_18_2(Object instance) {
        super((ResourceLocation)instance);
    }
    
    @Override public void bind(MinecraftAPI<?> mc) {
        Minecraft.getInstance().getTextureManager().bindForSetup(this.wrapped);
    }
    
    @Override public String getNamespace() {
        return this.wrapped.getNamespace();
    }
    
    @Override public String getPath() {
        return this.wrapped.getPath();
    }

    @Override public int getSpriteFrames() {
        return 0;
    }
}