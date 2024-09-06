package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ResourceLocation1_12_2 extends ResourceLocationAPI<ResourceLocation> {

    public ResourceLocation1_12_2(Object instance) {
        super((ResourceLocation)instance);
    }

    @Override public void bind(MinecraftAPI mc) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.wrapped);
    }

    @Override public int getSpriteFrames() {
        return 0;
    }

    @Override public String getNamespace() {
        return this.wrapped.getNamespace();
    }

    @Override public String getPath() {
        return this.wrapped.getPath();
    }
}