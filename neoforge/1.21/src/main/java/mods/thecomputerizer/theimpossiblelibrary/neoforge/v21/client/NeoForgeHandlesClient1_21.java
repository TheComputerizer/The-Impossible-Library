package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.NeoForgeHandlesClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.loading.ClientModLoader;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NeoForgeHandlesClient1_21 extends NeoForgeHandlesClient {
    
    private final Set<KeyMapping> keys = new HashSet<>();
    private boolean registeredKeys;
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((BufferSource)source).endBatch((RenderType)type);
        else ((BufferSource)source).endBatch();
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override protected Event getDebugTextEvent(Minecraft mc, GuiGraphics graphics, List<String> left, List<String> right) {
        return new DebugText(mc.getWindow(),graphics,mc.getTimer(),left,right);
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override public boolean isLoading(@Nullable Object minecraft) {
        return ClientModLoader.isLoading(); //Maybe?
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        if(this.registeredKeys) ArrayUtils.add(Minecraft.getInstance().options.keyMappings,key.unwrap());
        else this.keys.add(key.unwrap());
    }
    
    @Override public void registerKeyBindingsEvent(Object eventObj) {
        RegisterKeyMappingsEvent event = (RegisterKeyMappingsEvent)eventObj;
        TILRef.logInfo("Registering {} keybinds for 1.21",this.keys.size());
        for(KeyMapping key : this.keys) event.register(key);
        this.keys.clear();
        this.registeredKeys = true;
    }
}