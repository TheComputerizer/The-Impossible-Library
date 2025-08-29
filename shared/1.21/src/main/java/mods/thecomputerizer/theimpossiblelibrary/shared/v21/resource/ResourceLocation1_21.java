package mods.thecomputerizer.theimpossiblelibrary.shared.v21.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ResourceLocation1_21 extends ResourceLocationAPI<ResourceLocation> {
    
    public ResourceLocation1_21(Object instance) {
        super(instance);
    }
    
    @Override public void bind(MinecraftAPI<?> mc) {
        Minecraft.getInstance().getTextureManager().bindForSetup(this.wrapped);
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