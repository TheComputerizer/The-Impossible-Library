package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.NeoForgeHandlesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Objects;

public class NeoForgeHandlesClient1_21 extends NeoForgeHandlesClient {
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((BufferSource)source).endBatch((RenderType)type);
        else ((BufferSource)source).endBatch();
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        ArrayUtils.add(Minecraft.getInstance().options.keyMappings,key.unwrap());
    }
}