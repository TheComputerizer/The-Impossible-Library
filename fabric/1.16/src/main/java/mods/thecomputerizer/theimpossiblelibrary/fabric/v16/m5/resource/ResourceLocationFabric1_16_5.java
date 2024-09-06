package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationFabric1_16_5 extends ResourceLocation1_16_5<ResourceLocation> {
    
    public ResourceLocationFabric1_16_5(Object instance) {
        super((ResourceLocation)instance);
    }
    
    @Override public void bind(MinecraftAPI mc) {
        Minecraft.getInstance().getTextureManager().bind(this.wrapped);
    }
    
    @Override public String getNamespace() {
        return this.wrapped.getNamespace();
    }
    
    @Override public String getPath() {
        return this.wrapped.getPath();
    }
}