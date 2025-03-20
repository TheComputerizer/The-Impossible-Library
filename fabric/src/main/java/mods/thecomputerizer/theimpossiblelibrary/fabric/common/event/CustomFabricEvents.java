package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;

import java.util.List;

public interface CustomFabricEvents {
    
    Event<CustomTickFabric> CUSTOM_TICK = EventFactory.createArrayBacked(
            CustomTickFabric.class,listeners -> ticker -> {
                for(CustomTickFabric listener : listeners) listener.onTick(ticker);
            });
    
    Event<KeyPressed> KEY_PRESSED = EventFactory.createArrayBacked(
            KeyPressed.class,listeners -> (key,scanCode,action,modifiers) -> {
                for(KeyPressed listener : listeners) listener.onKeyPressed(key,scanCode,action,modifiers);
            }
    );
    
    Event<RenderDebugInfo> RENDER_DEBUG_INFO = EventFactory.createArrayBacked(
            RenderDebugInfo.class,listeners -> (matrix,left,right) -> {
                for(RenderDebugInfo listener : listeners) listener.onRenderDebug(matrix,left,right);
            }
    );
    
    Event<RegistryEvent> REGISTER_BLOCK_ENTITIES = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    
    Event<RegistryEvent> REGISTER_BLOCKS = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    
    Event<RegistryEvent> REGISTER_ENTITIES = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    
    Event<RegistryEvent> REGISTER_ITEMS = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    
    Event<RegistryEvent> REGISTER_SOUND_EVENTS = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    
    interface CustomTickFabric { void onTick(CustomTick ticker); }
    
    interface KeyPressed { void onKeyPressed(int key, int scanCode, int action, int modifiers); }
    
    //Fabric doesn't use registry events, but this is still needed to keep the event wrappers happy
    interface RegistryEvent { void register(Registry<?> registry); }
    
    interface RenderDebugInfo { void onRenderDebug(PoseStack matrix, List<String> left, List<String> right); }
}