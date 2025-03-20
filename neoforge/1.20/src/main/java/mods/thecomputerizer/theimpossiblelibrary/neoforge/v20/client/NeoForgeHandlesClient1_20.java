package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.NeoForgeHandlesClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NeoForgeHandlesClient1_20 extends NeoForgeHandlesClient {
    
    private final Set<KeyMapping> keys = new HashSet<>();
    private boolean registeredKeys;
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((BufferSource)source).endBatch((RenderType)type);
        else ((BufferSource)source).endBatch();
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        if(this.registeredKeys) ArrayUtils.add(Minecraft.getInstance().options.keyMappings,key.unwrap());
        else this.keys.add(key.unwrap());
    }
    
    @Override public void registerKeyBindingsEvent(Object eventObj) {
        RegisterKeyMappingsEvent event = (RegisterKeyMappingsEvent)eventObj;
        for(KeyMapping key : this.keys) event.register(key);
        this.keys.clear();
        this.registeredKeys = true;
    }
}