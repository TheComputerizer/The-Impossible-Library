package mods.thecomputerizer.theimpossiblelibrary.legacy.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationLegacy implements ResourceLocationAPI<ResourceLocation> {

    private final ResourceLocation resource;

    public ResourceLocationLegacy(ResourceLocation resource) {
        this.resource = resource;
    }

    @Override
    public void bind(MinecraftAPI mc) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resource);
    }

    @Override
    public int getSpriteFrames() {
        return 0;
    }

    @Override
    public ResourceLocation get() {
        return this.resource;
    }

    @Override
    public String getNamespace() {
        return this.resource.getNamespace();
    }

    @Override
    public String getPath() {
        return this.resource.getPath();
    }
}