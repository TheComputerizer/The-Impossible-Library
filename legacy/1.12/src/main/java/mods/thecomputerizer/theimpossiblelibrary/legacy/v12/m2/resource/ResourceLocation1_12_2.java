package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class ResourceLocation1_12_2 extends ResourceLocationAPI<ResourceLocation> {

    public ResourceLocation1_12_2(Object instance) {
        super(instance);
    }

    @Override public void bind(MinecraftAPI<?> mc) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.wrapped);
    }

    @Override public String getNamespace() {
        return Objects.nonNull(this.wrapped) ? this.wrapped.getNamespace() : null;
    }

    @Override public String getPath() {
        return Objects.nonNull(this.wrapped) ? this.wrapped.getPath() : null;
    }
    
    @Override public int getSpriteFrames() {
        return 0;
    }
}