package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ResourceLocation1_16_5 extends ResourceLocationAPI<ResourceLocation> {
    
    public ResourceLocation1_16_5(Object instance) {
        super((ResourceLocation)instance);
    }
    
    @Override public void bind(MinecraftAPI<?> mc) {
        Minecraft.getInstance().getTextureManager().bind(this.wrapped);
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