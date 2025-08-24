package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.NeoForgeHandlesClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class NeoForgeHandlesClient1_20 extends NeoForgeHandlesClient {
    
    protected final Set<KeyMapping> keys = new HashSet<>();
    protected boolean registeredKeys;
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((BufferSource)source).endBatch((RenderType)type);
        else ((BufferSource)source).endBatch();
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        if(this.registeredKeys) ArrayHelper.append(Minecraft.getInstance().options.keyMappings,key.unwrap(),false);
        else this.keys.add(key.unwrap());
    }
}