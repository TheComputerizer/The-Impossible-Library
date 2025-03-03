package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.ForgeHandlesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.ClientRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

public class ForgeHandlesClient1_18_2 extends ForgeHandlesClient {
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((BufferSource)source).endBatch((RenderType)type);
        else ((BufferSource)source).endBatch();
    }
    
    @Override public boolean isLoading(@Nullable Object minecraft) {
        Minecraft mc = (Minecraft)minecraft;
        return super.isLoading(minecraft) && (Objects.isNull(mc) || (Objects.isNull(mc.level) && Objects.isNull(mc.screen)));
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        ClientRegistry.registerKeyBinding(key.unwrap());
    }
}