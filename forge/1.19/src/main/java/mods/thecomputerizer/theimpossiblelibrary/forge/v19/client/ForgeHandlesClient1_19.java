package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.ForgeHandlesClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ForgeHandlesClient1_19 extends ForgeHandlesClient {
    
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
        TILRef.logInfo("Registering {} keybinds for 1.19",this.keys.size());
        for(KeyMapping key : this.keys) event.register(key);
        this.keys.clear();
        this.registeredKeys = true;
    }
}