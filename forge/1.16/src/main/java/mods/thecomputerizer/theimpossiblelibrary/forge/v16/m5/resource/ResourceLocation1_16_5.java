package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ResourceLocation1_16_5 extends ResourceLocationAPI<ResourceLocation> {
    
    public ResourceLocation1_16_5(ResourceLocation instance) {
        super(instance);
    }

    @Override
    public void bind(MinecraftAPI mc) {
        Minecraft.getInstance().getTextureManager().bind(this.instance);
    }

    @Override
    public int getSpriteFrames() {
        return 0;
    }

    @Override
    public String getNamespace() {
        return this.instance.getNamespace();
    }

    @Override
    public String getPath() {
        return this.instance.getPath();
    }
}